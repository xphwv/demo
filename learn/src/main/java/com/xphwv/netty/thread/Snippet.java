package com.xphwv.netty.thread;

import java.util.concurrent.locks.LockSupport;

public class Snippet {
	public static void main(String[] args) {
		Thread thread = Thread.currentThread();
		
		LockSupport.unpark(thread);
		
		System.out.println("a");
		LockSupport.park();
		System.out.println("b");
		LockSupport.unpark(thread);
		LockSupport.park();
		System.out.println("c");
		LockSupport.park();
		System.out.println("d");
	}
}
