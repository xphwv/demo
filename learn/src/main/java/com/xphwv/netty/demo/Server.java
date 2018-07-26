package com.xphwv.netty.demo;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

/**
 * TODO
 * 
 * @author XuPan
 * @date 2015年12月2日 下午7:42:15
 * @version 1.0
 */
public class Server {
	public static void main(String[] args) {
		Executor bossExecutor=Executors.newCachedThreadPool();
		Executor workerExecutor=Executors.newCachedThreadPool();
		ChannelFactory channelFactory=new NioServerSocketChannelFactory(bossExecutor, workerExecutor);
		ServerBootstrap bootstrap=new ServerBootstrap(channelFactory);
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {  
            public ChannelPipeline getPipeline() {  
                 ChannelPipeline pipeline = Channels.pipeline();  
                pipeline.addLast("encode",new StringEncoder());  
                pipeline.addLast("decode",new StringDecoder());  
                pipeline.addLast("handler",new ServerHandler());  
                return pipeline;  
            }  
        });  
        bootstrap.setOption("child.tcpNoDelay", true);  
        bootstrap.setOption("child.keepAlive", true);  
        bootstrap.bind(new InetSocketAddress(8080)); 
	}
}
