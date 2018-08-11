package com.sfc.netty.basic;

import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * EventLoop Schedule examples
 */
public class ScheduleExamples {
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    /**
     * Schedule via ScheduledExecutorService
     */
    public static void schedule() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);

        ScheduledFuture<?> future = executor.schedule(
            new Runnable() {
            @Override
            public void run() {
                System.out.println("Now it is 60 seconds later");
            }
        // 调度任务在从现在开始的 60 秒之后执行
        }, 60, TimeUnit.SECONDS);
        // 一旦调度任务执行完成，就关闭 ScheduledExecutorService 以释放资源
        executor.shutdown();
    }

    /**
     * Schedule via EventLoop
     */
    public static void scheduleViaEventLoop() {
        Channel ch = CHANNEL_FROM_SOMEWHERE; // get reference from somewhere
        ScheduledFuture<?> future = ch.eventLoop().schedule(
            new Runnable() {
            @Override
            public void run() {
                System.out.println("60 seconds later");
            }
            // 调度任务在从现在开始的 60 秒之后执行
        }, 60, TimeUnit.SECONDS);
    }

    /**
     * Schedule via EventLoop with fix interval
     */
    public static void scheduleFixedViaEventLoop() {
        Channel ch = CHANNEL_FROM_SOMEWHERE; // get reference from somewhere
        ScheduledFuture<?> future = ch.eventLoop().scheduleAtFixedRate(
           new Runnable() {
           @Override
           public void run() {
                System.out.println("Run every 60 seconds");
           }
        //调度在 60 秒之后，并且以后每间隔 60 秒运行
        }, 60, 60, TimeUnit.SECONDS);
    }

    /**
     * Cancel a scheduled task
     */
    public static void cancelingTaskUsingScheduledFuture(){
        Channel ch = CHANNEL_FROM_SOMEWHERE; // get reference from somewhere
        ScheduledFuture<?> future = ch.eventLoop().scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Run every 60 seconds");
                    }
                }, 60, 60, TimeUnit.SECONDS);
        // Some other code that runs...
        boolean mayInterruptIfRunning = false;
        // 取消该任务
        future.cancel(mayInterruptIfRunning);
    }
}
