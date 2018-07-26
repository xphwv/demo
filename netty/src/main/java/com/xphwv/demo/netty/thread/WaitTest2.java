package com.xphwv.demo.netty.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class WaitTest2 {
	static Thread manThread = null;

	public static void main(String[] args) {
		manThread = Thread.currentThread();
		ThreadA ta = new ThreadA("ta");

		System.out.println(Thread.currentThread().getName() + " start ta");
		ta.start();
		System.out.println(Thread.currentThread().getName() + " block");
		// 主线程等待
		LockSupport.park(manThread);

		System.out.println(Thread.currentThread().getName() + " continue");
	}

	static class ThreadA extends Thread {

		public ThreadA(String name) {
			super(name);
		}

		public void run() {
			System.out.println(Thread.currentThread().getName() + " blockding");
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			LockSupport.unpark(manThread);
		}
	}
}