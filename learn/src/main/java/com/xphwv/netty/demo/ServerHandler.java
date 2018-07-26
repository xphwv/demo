package com.xphwv.netty.demo;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

public class ServerHandler extends SimpleChannelUpstreamHandler  {  
    @Override  
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {  
       System.out.println("服务器接收："+e.getMessage());  
    }  
      
    @Override  
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {  
        e.getCause().printStackTrace();  
        Channel ch = e.getChannel();  
        ch.close();  
    }  
} 