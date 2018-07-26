package com.xphwv.thread;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * 
 * @author XuPan
 * @date 2015年12月8日 下午2:26:22
 * @version 1.0
 */
public class Demo1 {
	private static Logger loger = Logger.getLogger(Demo1.class);

	public static void main(String[] args) {
		int length = 2;
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
		}

		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}
		checkStats(threads, status);
	}

	public static void checkStats(Thread[] threads, Thread.State[] status) {
		boolean finish = false;
		while (!finish) {
			for (int i = 0; i < threads.length; i++) {
				if (threads[i].getState() != status[i]) {
					loger.info(threads[i].getName() + "---" + status[i]);
					status[i] = threads[i].getState();
				}
			}
			finish = true;
			for (int i = 0; i < status.length; i++) {
				finish = finish && (status[i] == Thread.State.TERMINATED);
			}
		}
	}
	static class ThreadDemo implements Runnable {
		@Override
		public void run() {
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < 10; i++) {
				loger.debug(i);
			}
			
		}

	}
}
