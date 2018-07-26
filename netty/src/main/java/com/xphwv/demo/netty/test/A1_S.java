package com.xphwv.demo.netty.test;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineCoverage;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class A1_S {
	public static void main(String[] args) {
		ChannelFactory channelFactory = new NioServerSocketChannelFactory(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()),
				Executors.newFixedThreadPool(6));
		ServerBootstrap boot = new ServerBootstrap();
		boot.setFactory(channelFactory);
		ChannelPipeline pipeLine = boot.getPipeline();
		// pipeLine.addLast("handler", new TimeHandler());
		// pipeLine.addFirst("last_handler", new DiscardHandler());
		pipeLine.addLast("first_handler", new TimeHandler());
		boot.setOption("child.tcpNoDelay", true);
		boot.setOption("child.keepAlive", true);
		boot.bind(new InetSocketAddress("127.0.0.1", 9000));
		System.out.println("启动成功");
	}

	@ChannelPipelineCoverage("all")
	static class DiscardHandler extends SimpleChannelHandler {

		@Override
		public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
			Channel channel = ctx.getChannel();
			ChannelBuffer buffer = (ChannelBuffer) e.getMessage();
			System.out.println(this.getClass().getName() + "-----" + (char) buffer.readByte());
			channel.write(e.getMessage());
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
			e.getCause().printStackTrace();
			Channel chennel = e.getChannel();
			chennel.close();
		}

	}

	@ChannelPipelineCoverage("all")
	static class TimeHandler extends SimpleChannelHandler {

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
			e.getCause().printStackTrace();
			Channel chennel = e.getChannel();
			chennel.close();
		}

		@Override
		public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
			Channel channel = e.getChannel();

			ChannelBuffer time = ChannelBuffers.buffer(4);
			int value = (int) (System.currentTimeMillis() / 1000);
			time.writeInt(value);
			System.out.println(this.getClass().getName() + "-----" + value);
			ChannelFuture f = channel.write(time);
			f.addListener(ChannelFutureListener.CLOSE);
			// f.addListener(new ChannelFutureListener() {
			//
			// @Override
			// public void operationComplete(ChannelFuture future) throws Exception {
			// future.getChannel().close();
			// }
			// });

		}

	}
}
