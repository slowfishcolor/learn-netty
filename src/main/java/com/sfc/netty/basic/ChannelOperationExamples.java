package com.sfc.netty.basic;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Channel write related operations
 */
public class ChannelOperationExamples {

    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    /**
     * Write to a channel
     */
    public static void writingToChannel() {
        Channel channel = CHANNEL_FROM_SOMEWHERE;

        // 创建持有要写数据的 ByteBuf
        ByteBuf buf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8);
        ChannelFuture cf = channel.writeAndFlush(buf);
        // 添加 ChannelFutureListener 以便在写操作完成后接收通知
        cf.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                // 写操作完成，并且没有错误发生
                if (future.isSuccess()) {
                    System.out.println("Write successful");
                } else {
                    // 记录错误
                    System.err.println("Write error");
                    future.cause().printStackTrace();
                }
            }
        });
    }

    /**
     * Write to a single channel via many threads
     */
    public static void writingToChannelFromManyThreads() {
        final Channel channel = CHANNEL_FROM_SOMEWHERE;

        final ByteBuf buf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8);
        // 创建将数据写到 Channel 的 Runnable
        Runnable writer = new Runnable() {
            @Override
            public void run() {
                channel.write(buf.duplicate());
            }
        };

        Executor executor = Executors.newCachedThreadPool();

        // write in one thread
        executor.execute(writer);

        // write in another thread
        executor.execute(writer);
        //...
    }
}
