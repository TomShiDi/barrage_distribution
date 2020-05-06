package com.barrage.webcontroller.nettyUse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;


public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

    private HttpService httpService;
    private IWebSocketService webSocketService;

    public WebSocketServerHandler(HttpService httpService, IWebSocketService webSocketService) {
        this.httpService = httpService;
        this.webSocketService = webSocketService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            httpService.handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            webSocketService.handleFrame(ctx, (WebSocketFrame) msg);
        }
    }


}

