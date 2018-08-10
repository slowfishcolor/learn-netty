package com.sfc.netty.basic;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

/**
 * Basic channel write operations
 */
public class WriteHandlers {

    private static final ChannelHandlerContext CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE = null;

    /**
     * Write via Channel
     * Write callback will trigger through hole pipeline
     */
    public static void writeViaChannel() {
        ChannelHandlerContext ctx = CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE;
        // 获取到与 ChannelHandlerContext 相关联的 Channel 的引用
        Channel channel = ctx.channel();
        // 通过 Channel 写入缓冲区
        channel.write(Unpooled.copiedBuffer("Netty in Action",
                CharsetUtil.UTF_8));

    }

    /**
     * Write via ChannelPipeline
     * Write callback will trigger through hole pipeline
     */
    public static void writeViaChannelPipeline() {
        ChannelHandlerContext ctx = CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE;
        // 获取到与 ChannelHandlerContext 相关联的 ChannelPipeline 的引用
        ChannelPipeline pipeline = ctx.pipeline(); //get reference form somewhere
        // 通过 ChannelPipeline 写入缓冲区
        pipeline.write(Unpooled.copiedBuffer("Netty in Action",
                CharsetUtil.UTF_8));

    }

    /**
     * Write via ChannelHandlerContext
     * Write callback will only trigger begin at next handler, previous handler won't trigger this callback
     */
    public static void writeViaChannelHandlerContext() {
        // 获取到 ChannelHandlerContext 的引用
        ChannelHandlerContext ctx = CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE;
        // write() 方法将把缓冲区数据发送到下一个 ChannelHandler
        ctx.write(Unpooled.copiedBuffer("Netty in Action", CharsetUtil.UTF_8));
    }

}
