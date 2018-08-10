package com.sfc.netty.basic;


import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Store ChannelHandlerContext for further use
 */
public class StoreWriteHandler extends ChannelHandlerAdapter {

    /**
     * store context field
     */
    private ChannelHandlerContext ctx;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        // Store this context for further use
        this.ctx = ctx;
    }

    public void send(String msg) {
        // send message via stored context
        ctx.writeAndFlush(msg);
    }
}
