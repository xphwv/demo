package com.xphwv.netty.nio._2_3_3;

public class TimeClient {
	private static int port = 9000;

	public static void main(String[] args) {
		new Thread(new TimeHandleClient(port), "TimeClient-01").start();
	}
}
