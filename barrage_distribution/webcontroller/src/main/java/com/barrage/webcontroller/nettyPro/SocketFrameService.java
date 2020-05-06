package com.barrage.webcontroller.nettyPro;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

/**
 * 处理各种webSocket数据报
 */
public interface SocketFrameService {
    void handleFrame(ChannelHandlerContext context, WebSocketFrame webSocketFrame);
}
