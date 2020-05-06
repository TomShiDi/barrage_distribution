package com.barrage.webcontroller.nettyPro;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * webSocket 连接建立请求依赖http协议，这个接口用来处理这个请求
 */
public interface HttpService {

    void handleHttpRequest(ChannelHandlerContext context, FullHttpRequest request);
}
