package com.xphwv.test.threads;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {

	public static volatile int count = 0;
	public static AtomicInteger aCount = new AtomicInteger(0);

	public static void inc() {

		// 这里延迟1毫秒，使得结果明显
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
		}
		aCount.getAndIncrement();
		count++;

		// 这里每次运行的值都有可能不同,可能为1000
		System.out.println("运行结果:Counter.count=" + Counter.count);
		System.out.println("运行结果:Counter.aCount=" + Counter.aCount);
	}

	public static void main(String[] args) throws InterruptedException {

		// 同时启动1000个线程，去进行i++计算，看看实际结果

		for (int i = 0; i < 1000; i++) {
			Thread t=new Thread(new Runnable() {
				@Override
				public void run() {
					Counter.inc();
				}
			});
			t.start();
//			t.join();
		}

	}
}