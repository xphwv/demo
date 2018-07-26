/**  
 * @Copyright 五八信息技术有限公司 
 */
package com.xphwv.test.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * 多线访问情况
 * @author XuPan
 * @date 2014-5-29 下午1:28:18
 * @version 1.0
 */
public class AtomicIntegerTest {
	public static void main(String[] args) throws InterruptedException {
		AtomicIntegerTest tm = new AtomicIntegerTest();
		final int size = 10;
		Thread[] ts = new Thread[size];
		for (int i = 0; i < size; i++) {
			Test t = tm.new Test("thread-" + i);
			ts[i] = t;
		}
		for (int i = 0; i < size; i++) {
			ts[i].start();
		}
		for (int i = 0; i < size; i++) {
			ts[i].join();
		}

		System.out.println("ai.get():" + tm.ai.get());
		System.out.println("i:" + i);
	}

	private static  int i = 0;
	private AtomicInteger ai = new AtomicInteger(0);

	private void action() {
		while (ai.get() < 10000) {
			i++;
			ai.incrementAndGet();
			System.out.println(Thread.currentThread().getName() + "---" + i + ";ai=" + ai.get());
			try {
				TimeUnit.MILLISECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	class Test extends Thread {
		public Test(String name) {
			super(name);
		}

		@Override
		public void run() {
			action();
		}

	}
}