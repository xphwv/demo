package com.xphwv.thread;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * TODO
 * 
 * @author XuPan
 * @date 2015年12月8日 下午2:26:22
 * @version 1.0
 */
public class Demo2 {
	private static Logger loger = Logger.getLogger(Demo2.class);

	public static void main(String[] args) {
		int length = 5;
		Thread[] threads = new Thread[length];
		Thread.State[] status = new Thread.State[length];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new ThreadDemo());
			threads[i].setName("thread-" + (i + 1));
			status[i] = threads[i].getState();
			if (i % 2 == 0) {
				threads[i].setPriority(Thread.MIN_PRIORITY);
			} else {
				threads[i].setPriority(Thread.MAX_PRIORITY);
			}
			threads[i].start();
		}
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int i = 2;
		loger.info(threads[i].getName() + "准备中断" + System.currentTimeMillis());
		threads[i].interrupt();
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		loger.info(threads[i].getName() + "是否中断：" + threads[i].isInterrupted());
	}

	static class ThreadDemo implements Runnable {
		@Override
		public void run() {
			String name = Thread.currentThread().getName();
			loger.info(name + "线程启动" + System.currentTimeMillis());
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				// 中断处理相关操作
				loger.error(name + "被中断处理" + System.currentTimeMillis());
				return ;
			}
			loger.info(name + "线程结束" + System.currentTimeMillis());
		}

	}
}
