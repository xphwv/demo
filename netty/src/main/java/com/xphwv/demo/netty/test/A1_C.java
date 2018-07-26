package com.xphwv.demo.netty.test;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineCoverage;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

public class A1_C {
	public static void main(String[] args) {
		ChannelFactory channelFactory = new NioClientSocketChannelFactory(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()),
				Executors.newFixedThreadPool(6));
		ClientBootstrap boot = new ClientBootstrap(channelFactory);
		ChannelPipeline pipeLine = boot.getPipeline();
		pipeLine.addFirst("handler", new Timehandler());
		boot.setOption("tcpNoDelay", true);
		boot.setOption("keepAlive", true);
		boot.connect(new InetSocketAddress("127.0.0.1", 9000));
	}

	@ChannelPipelineCoverage("all")
	static class Timehandler extends SimpleChannelHandler {

		@Override
		public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
			ChannelBuffer buffer = (ChannelBuffer) e.getMessage();
			Date d = new Date(buffer.readInt() * 1000);
			System.out.println(d);
			e.getChannel().close();
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
			e.getCause().printStackTrace();
			e.getChannel().close();
		}

	}
}
