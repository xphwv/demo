package com.xphwv.zookeeper;

public class ZkConfig {
	private static String connection="10.37.18.174:2181";
	private static int timeOut=1000;
	public static String getConnection() {
		return connection;
	}
	public static int getTimeOut() {
		return timeOut;
	}
	
}
