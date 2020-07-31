package com.xphwv.netty.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by xupan on 2020/7/29
 */
public class EchoClient {
    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup workGroup = new NioEventLoopGroup(1);
        Bootstrap boot = new Bootstrap();
        boot.group(workGroup)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .channel(NioSocketChannel.class)
                .handler(new EchoClientInitializer());
        ChannelFuture future = boot.connect("127.0.0.1",8080)
                .sync();
    }
}
