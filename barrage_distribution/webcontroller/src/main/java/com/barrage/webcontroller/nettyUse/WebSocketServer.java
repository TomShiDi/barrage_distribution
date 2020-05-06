package com.barrage.webcontroller.nettyUse;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketServer implements IWebSocketService, HttpService {

    private static final String HN_HTTP_CODEC = "HN_HTTP_CODEC";//http请求数据解析handler的key字段
    private static final String HN_HTTP_AGGREGATOR = "HN_HTTP_AGGREGATOR";//解析请求中的数据内容并生成FullHttpRequest的Handler的key字段
    private static final String HN_HTTP_CHUNK = "HN_HTTP_CHUNK";//大数据发送接收Handler的key字段
    private static final String HN_SERVER = "HN_LOGIC";//用户自定义的Handler的key字段
    private static final String WEBSOCKET_UPGRADE = "websocket";//http响应头中的upgrade字段值，表示服务器端已经成功将此次连接切换成websocket协议
    private static final String WEBSOCKET_CONNECTION = "Upgrade";//http请求头中的upgrade字段值，表示当前的http请求不是普通的请求，是一个切换websocket的请求
    private int MAX_CONTENT_LENGTH = 65536;//最大的请求可发送的数据量

    private static final AttributeKey<WebSocketServerHandshaker> ATTR_HANDSHAKER = AttributeKey.newInstance("ATTR_KEY_CHANNELID");
    private static final String WEBSOCKET_URI_ROOT_PATTERN = "ws://%s:%d";//开启websocket服务的模板字符串，下面会格式化成具体的字符串
    private final String WEBSOCKET_URI_ROOT;//客户端最终用来建立连接的字符串


    private String host;
    private int port;

    private Map<ChannelId, Channel> channelMap = new ConcurrentHashMap<>();//线程安全的channelMap，用于保存客户端来的请求对象
    private ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);//用于保存客户端来的请求对象，可以方便批量向客户端发送信息

    public WebSocketServer(int port) {
        this("localhost", port);
    }

    public WebSocketServer(String host, int port) {
        this.host = host;
        this.port = port;
        WEBSOCKET_URI_ROOT = String.format(WEBSOCKET_URI_ROOT_PATTERN, host, port);//构造客户端连接字符串
    }

    public void start() {
        //EventLoopGroup是用来处理请求到来的各种event和IO操作的
        EventLoopGroup bossGroup = new NioEventLoopGroup();//父工作组
        EventLoopGroup workerGroup = new NioEventLoopGroup();//子工作组
        ServerBootstrap serverBootstrap = new ServerBootstrap();//一个实现主要功能的核心组件，是项目的中枢
        serverBootstrap
                .group(bossGroup, workerGroup)//设置事件分发组对象和事件执行组对象
                .channel(NioServerSocketChannel.class)//设置每个channel对应的创建形式，本项目使用NIO模式处理socket连接
                .childHandler(new ChannelInitializer<Channel>() {//添加请求到来时的初始化函数
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline channelPipeline = ch.pipeline();//channel管道，各种读写操作都是在管道中执行的，可以理解为缓存区操作，NIO就是基于缓存操作的
                        channelGroup.add(ch);//将当前的请求对象缓存下来以便以后使用
                        channelMap.put(ch.id(), ch);//建立请求的id映射
                        ch.closeFuture().addListener((ChannelFutureListener) future -> {
                            channelMap.remove(future.channel().id());
                        });//添加请求关闭事件的处理函数

                        channelPipeline.addLast(HN_HTTP_CODEC, new HttpServerCodec());//为管道添加http解码和编码handler
                        channelPipeline.addLast(HN_HTTP_AGGREGATOR, new HttpObjectAggregator(MAX_CONTENT_LENGTH));//为管道添加请求内容解析和构造handler
                        channelPipeline.addLast(HN_HTTP_CHUNK, new ChunkedWriteHandler());//数据发送和接收处理handler
                        channelPipeline.addLast(HN_SERVER, new WebSocketServerHandler(WebSocketServer.this, WebSocketServer.this));//这是自己定义的handler，用来自定义处理逻辑
                    }
                });
        try {
            //为服务端的socket建立事件添加处理回调函数
            ChannelFuture channelFuture = serverBootstrap.bind(host, port).addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    System.out.println("socket success");
                }
            }).sync();
            //为服务端的socket关闭事件添加回调函数
            channelFuture.channel().closeFuture().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    System.out.println("socket channel closed {}" + future.channel());
                }
            }).sync();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * websocket之所以普及这么快的一个原因就是它兼容http协议，表现为建立websocket连接的第一次请求是http请求，
     * 但不是普通的http请求，是一个协议切换请求。
     * 因此，这个函数就是用来处理来自客户端的建立连接请求的
     *
     * @param ctx 当前请求channel的上下文对象，可以获取当前请求channel
     * @param req netty封装的httprequest对象，主要功能和servlet中的差不多
     */
    @Override
    public void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        if (isWebSocketUpgrade(req)) { // 该请求是不是websocket upgrade请求
            System.out.println("upgrade to websocket protocol");

            //获取request请求头中协议信息
            String subProtocols = req.headers().get(HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL);

            //构造握手信息
            WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(WEBSOCKET_URI_ROOT, subProtocols, false);
            WebSocketServerHandshaker handshaker = factory.newHandshaker(req);

            if (handshaker == null) {// 请求头不合法, 导致handshaker没创建成功
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else {
                // 响应该请求
                handshaker.handshake(ctx.channel(), req);
                // 把handshaker 绑定给Channel, 以便后面关闭连接用
                ctx.channel().attr(ATTR_HANDSHAKER).set(handshaker);// attach handshaker to this channel
            }
            return;
        }

        // TODO 忽略普通http请求
        System.out.println("ignoring normal http request");
    }

    /**
     * 处理websocket的各种形式的数据帧
     *
     * @param ctx   当前请求的上下文
     * @param frame websocket的数据帧对象
     */
    @Override
    public void handleFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // text frame
        if (frame instanceof TextWebSocketFrame) {
            String text = ((TextWebSocketFrame) frame).text();
//            TextWebSocketFrame rspFrame = new TextWebSocketFrame(text);
            System.out.println("recieve TextWebSocketFrame from channel {}" + ctx.channel());
            // 发给其他所有channel
//            for (Channel ch : channelMap.values()) {
//                if (ctx.channel().equals(ch)) {
//                    continue;
//                }
//                TextWebSocketFrame rspFrame = new TextWebSocketFrame(text);
//                ch.writeAndFlush(rspFrame);
//                System.out.println("write text[{}] to channel {}"+ text+ ch);
//            }
            TextWebSocketFrame rspFrame = new TextWebSocketFrame(text);
            channelGroup.writeAndFlush(rspFrame);
            return;
        }
        // ping frame, 回复pong frame即可
        if (frame instanceof PingWebSocketFrame) {
            System.out.println("recieve PingWebSocketFrame from channel {}" + ctx.channel());
            ctx.channel().writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        if (frame instanceof PongWebSocketFrame) {
            System.out.println("recieve PongWebSocketFrame from channel {}" + ctx.channel());
            return;
        }
        // close frame,
        if (frame instanceof CloseWebSocketFrame) {
            System.out.println("recieve CloseWebSocketFrame from channel {}" + ctx.channel());
            WebSocketServerHandshaker handshaker = ctx.channel().attr(ATTR_HANDSHAKER).get();
            if (handshaker == null) {
                System.out.println("channel {} have no HandShaker" + ctx.channel());
                return;
            }
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        // 剩下的是binary frame, 忽略
        System.out.println("unhandle binary frame from channel {}" + ctx.channel());
    }

    //三者与：1.GET? 2.Upgrade头 包含websocket字符串?  3.Connection头 包含 Upgrade字符串?
    private boolean isWebSocketUpgrade(FullHttpRequest req) {
        HttpHeaders headers = req.headers();
        return req.getMethod().equals(HttpMethod.GET)
                && headers.get(HttpHeaderNames.UPGRADE).contains(WEBSOCKET_UPGRADE)
                && headers.get(HttpHeaderNames.CONNECTION).contains(WEBSOCKET_CONNECTION);
    }
}
