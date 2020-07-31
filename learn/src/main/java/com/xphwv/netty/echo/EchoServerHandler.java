package com.xphwv.netty.echo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by xupan on 2020/7/29
 */
@Slf4j
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channelRegistered,channelId:{}", ctx.channel().id());
        ctx.write("\r\n");
        ctx.write("channelRegistered-------------------------------\r\n");
        ctx.write("registered success!\r\n");
        ctx.flush();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channelUnregistered,channelId:{}", ctx.channel().id());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelActive,channelId:{}", ctx.channel().id());
        ctx.write("\r\n");
        ctx.write("channelActive-------------------------------\r\n");
        ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n");
        ctx.write("It is " + new Date() + " now.\r\n");
        ctx.write("\r\n");
        ctx.flush();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelInactive,channelId:{}", ctx.channel().id());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("channelRead,channelId:{},msg:{}", ctx.channel().id(), msg);
        log.info("channelRead,channelId:{},sleep start....", ctx.channel().id(), msg);
        TimeUnit.SECONDS.sleep(3);
        log.info("channelRead,channelId:{},sleep finish", ctx.channel().id(), msg);
        ctx.write("channelRead-------------------------------\r\n");
        log.info("channelRead,System Input");
        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
        ctx.write(buf.readLine() + "\r\n");
        ctx.write("\r\n");
        ctx.flush();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("channelReadComplete,channelId:{}", ctx.channel().id());
        ctx.write("\r\n");
        ctx.write("channelReadComplete-------------------------------\r\n");
        ctx.write("server read success\r\n");
        ctx.write("\r\n");
        ctx.flush();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("userEventTriggered,channelId:{}", ctx.channel().id());
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        log.info("channelWritabilityChanged,channelId:{}", ctx.channel().id());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("exceptionCaught,channelId:{}", ctx.channel().id());
    }
}
