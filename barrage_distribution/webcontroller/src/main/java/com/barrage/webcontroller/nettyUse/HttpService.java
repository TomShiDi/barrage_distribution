package com.barrage.webcontroller.nettyUse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public interface HttpService {
    void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req);
}
