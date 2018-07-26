package com.xphwv.demo.netty.nio._2_3_3;

public class TimeServer {
	private static int port = 9000;

	public static void main(String[] args) {
		MultiplexerTimeServer multiplexer1=new MultiplexerTimeServer(port);
		new Thread(multiplexer1, "MultiplexerTimeServer-01").start();
	}

}
