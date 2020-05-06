package com.barrage.webcontroller.nettyPro;


import com.barrage.webcontroller.utils.DateFormatUtil;
import com.barrage.api.dao.CommunicationMessageDao;
import com.barrage.api.enums.SocketResponseEnum;
import com.google.gson.Gson;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelMatchers;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//import barrage.demo.utils.SSLUtil;

public class HBSocketServer implements HttpService, SocketFrameService {

    private String host;
    private int port;

    private static int MAX_CONTENT_LENGTH = 65536;
    private static final String SSL_HANDLER = "SSL_HANDLER";

    /**
     * //http请求数据解析handler的key字段
     */
    private static final String HTTP_SERVER_CODEC = "HTTP_SERVER_CODEC";
    /**
     * //解析请求中的数据内容并生成FullHttpRequest的Handler的key字段
     */

    private static final String HTTP_OBJECT_AGGREGATOR = "HTTP_OBJECT_AGGREGATOR";

    /**
     * //大数据发送接收Handler的key字段
     */
    private static final String CHUNKED_WRITE_HANDLER = "CHUNKED_WRITE_HANDLER";

    /**
     * //用户自定义的Handler的key字段
     */
    private static final String MY_SERVICE = "MY_SERVICE";

    private static final String ONLINE_COUNT_MAP_KEY = "online_count";

    /**
     * http响应头中的upgrade字段值，表示服务器端已经成功将此次连接切换成websocket协议
     */
    private static final String WEBSOCKET_UPGRADE = "websocket";
    /**
     * http请求头中的upgrade字段值，表示当前的http请求不是普通的请求，是一个切换websocket的请求
     */
    private static final String WEBSOCKET_CONNECTION = "Upgrade";

    /**
     * 开启websocket服务的模板字符串，下面会格式化成具体的字符串
     */
    private static final String WEB_SOCKET_URL_PATTERN = "ws://%s:%d";

    /**
     * //客户端最终用来建立连接的字符串
     */
    private static String WEB_SOCKET_URL;

    private static final AttributeKey<WebSocketServerHandshaker> ATTRIBUTE_KEY = AttributeKey.newInstance("ATTR_KEY_CHANNELID");
    //线程安全的channelMap，用于保存客户端来的请求对象
    private Map<ChannelId, Channel> channelMap = new ConcurrentHashMap<>();

    /**
     * //用于保存客户端来的请求对象，可以方便批量向客户端发送信息
     */
    private ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static Map<String, Integer> onlineCountMap = new ConcurrentHashMap<>(2);

    public HBSocketServer(int port) {
        this("0.0.0.0", port);
    }

    public HBSocketServer(String host, int port) {
        this.host = host;
        this.port = port;
        //构造客户端连接字符串
        WEB_SOCKET_URL = String.format(WEB_SOCKET_URL_PATTERN, host, port);
//        onlineCountMap.put(ONLINE_COUNT_MAP_KEY, 0);
    }

    public void start() throws Exception {
        //EventLoopGroup是用来处理请求到来的各种event和IO操作的
        // 父工作组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //子工作组
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        //一个实现主要功能的核心组件，是项目的中枢
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap
                //设置事件分发组对象和事件执行组对象
                .group(bossGroup, workerGroup)
                //设置每个channel对应的创建形式，本项目使用NIO模式处理socket连接
                .channel(NioServerSocketChannel.class)
                //添加请求到来时的初始化函数
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        //channel管道，各种读写操作都是在管道中执行的，可以理解为缓存区操作，NIO就是基于缓存操作的
                        ChannelPipeline pipeline = ch.pipeline();
                        channelGroup.add(ch);//将当前的请求对象缓存下来以便以后使用
                        channelMap.put(ch.id(), ch);//建立请求的id映射
                        ch.closeFuture().addListener((ChannelFutureListener) future -> channelMap.remove(future.channel().id()));//添加请求关闭事件的处理函数


                        pipeline.addLast(HTTP_SERVER_CODEC, new HttpServerCodec());//为管道添加http解码和编码handler
                        pipeline.addLast(HTTP_OBJECT_AGGREGATOR, new HttpObjectAggregator(MAX_CONTENT_LENGTH));//为管道添加请求内容解析和构造handler
                        pipeline.addLast(CHUNKED_WRITE_HANDLER, new ChunkedWriteHandler());//数据发送和接收处理handler
                        pipeline.addLast(MY_SERVICE, new HBSocketHandler(HBSocketServer.this, HBSocketServer.this));//这是自己定义的handler，用来自定义处理逻辑
                    }
                });
        try {
            //为服务端的socket建立事件添加处理回调函数
            ChannelFuture channelFuture = bootstrap.bind(host, port).addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    System.out.println("socket success");
                }
            }).sync();
            //为服务端的socket关闭事件添加回调函数
            channelFuture.channel().closeFuture().addListener((ChannelFutureListener) future ->
                    System.out.println("socket channel closed {} " + future.channel())).sync();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * websocket之所以普及这么快的一个原因就是它兼容http协议，表现为建立websocket连接的第一次请求是http请求，
     * 但不是普通的http请求，是一个协议切换请求。
     * 因此，这个函数就是用来处理来自客户端的建立连接请求的
     *
     * @param context 当前请求channel的上下文对象，可以获取当前请求channel
     * @param request netty封装的httprequest对象，主要功能和servlet中的差不多
     */
    @Override
    public void handleHttpRequest(ChannelHandlerContext context, FullHttpRequest request) {
        Gson gson = new Gson();
        if (isWebSocketUpgrade(request)) {
            String subProtocols = request.headers().get(HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL);
            WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(WEB_SOCKET_URL, subProtocols, false);
            WebSocketServerHandshaker handshaker = factory.newHandshaker(request);
            if (handshaker == null) {
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(context.channel());
            } else {
                handshaker.handshake(context.channel(), request);
                context.channel().attr(ATTRIBUTE_KEY).set(handshaker);
//                increaseOnlineCount();
                channelGroup.writeAndFlush(new TextWebSocketFrame(gson.toJson(createOnlineResponse(channelMap.size()))));
            }
            return;
        }
        System.out.println("ignore normal http request");
    }

    /**
     * 处理websocket的各种形式的数据帧
     *
     * @param context        当前请求的上下文
     * @param webSocketFrame websocket的数据帧对象
     */
    @Override
    public void handleFrame(ChannelHandlerContext context, WebSocketFrame webSocketFrame) {
        //gson数据解析器
        Gson gson = new Gson();
        //textFrame 到来时的处理操作
        if (webSocketFrame instanceof TextWebSocketFrame) {
            String text = ((TextWebSocketFrame) webSocketFrame).text();
            System.out.println("receive TextWebSocket from channel {} " + context.channel() + " is " + text);
            TextWebSocketFrame responseFrame = new TextWebSocketFrame(gson.toJson(createMessageResponse(text, DateFormatUtil.toChinaNormal(new Date()))));
            //向除了当前请求之外的其他请求发送消息
            channelGroup.writeAndFlush(responseFrame, ChannelMatchers.isNot(context.channel()));
            context.channel().writeAndFlush(new TextWebSocketFrame(gson.toJson(createSuccessResponse())));
            return;
        }
        //pingFrame到来时的处理操作
        if (webSocketFrame instanceof PingWebSocketFrame) {
            System.out.println("receive PingWebSocketFrame from channel {} " + context.channel());
            context.channel().writeAndFlush(new PongWebSocketFrame(webSocketFrame.content().retain()));
            return;
        }
        //pongFrame到来时的处理操作
        if (webSocketFrame instanceof PongWebSocketFrame) {
            System.out.println("receive PongWebSocketFrame from channel {} " + context.channel());
            return;
        }
        //closeFrame 到来时的处理操作
        if (webSocketFrame instanceof CloseWebSocketFrame) {
            System.out.println("receive CloseWebSocketFrame from channel {} " + context.channel());
            WebSocketServerHandshaker handshaker = context.channel().attr(ATTRIBUTE_KEY).get();
            if (handshaker == null) {
                System.out.println(context.channel() + " does not has handShaker");
                return;
            }
            channelMap.remove(context.channel().id());
            channelGroup.writeAndFlush(new TextWebSocketFrame(gson.toJson(createOnlineResponse(channelMap.size()))));
        }
        System.out.println("unHandle binary frame from channel {}" + context.channel());
    }

    /**
     * 判断是否是切换webSocket的请求
     *
     * @param request 客户端的请求
     * @return 返回判断结果
     */
    private boolean isWebSocketUpgrade(FullHttpRequest request) {
        HttpHeaders httpHeaders = request.headers();
        System.out.println("httpHeader: " + httpHeaders.get(HttpHeaderNames.UPGRADE) + "  " + httpHeaders.get(HttpHeaderNames.CONNECTION));
        return request.method().equals(HttpMethod.GET)
                && httpHeaders.get(HttpHeaderNames.UPGRADE).contains(WEBSOCKET_UPGRADE)
                && httpHeaders.get(HttpHeaderNames.CONNECTION).contains(WEBSOCKET_CONNECTION);
    }

    /**
     * 构造返回在线客户的返回数据对象
     *
     * @param onlineCount 在线数
     * @return 构造的消息对象
     */
    private CommunicationMessageDao createOnlineResponse(Integer onlineCount) {
        CommunicationMessageDao communicationMessageDao = new CommunicationMessageDao();
        communicationMessageDao.setMethodCode(SocketResponseEnum.GET_ONLINE_COUNT.getCode());
        communicationMessageDao.setOnlineCount(onlineCount);
        return communicationMessageDao;
    }

    /**
     * 构造消息数据返回数据对象
     *
     * @param message     响应消息
     * @param currentDate 当前时间
     * @return 构造的消息对象
     */
    private CommunicationMessageDao createMessageResponse(String message, String currentDate) {
        CommunicationMessageDao communicationMessageDao = new CommunicationMessageDao();
        communicationMessageDao.setMethodCode(SocketResponseEnum.MESSAGE_RESPONSE.getCode());
        communicationMessageDao.setSendingMessage(message);
        communicationMessageDao.setCurrentDate(currentDate);
        return communicationMessageDao;
    }

    /**
     * 构造服务器接受数据成功的返回数据对象
     *
     * @return 构造的消息对象
     */
    private CommunicationMessageDao createSuccessResponse() {
        CommunicationMessageDao communicationMessageDao = new CommunicationMessageDao();
        communicationMessageDao.setMethodCode(SocketResponseEnum.SUCCESS_RECEIVE_RESPONSE.getCode());
        return communicationMessageDao;
    }
}
