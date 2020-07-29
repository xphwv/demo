package com.xphwv.google.guice.eventbus;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Snippet {
	public static void main(String[] args) {
		ReentrantLock lock=null;
		BlockingQueue blockingQueue = new ArrayBlockingQueue(10);
		ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 5, 1, TimeUnit.MINUTES, blockingQueue);
		for (int i = 0; i < 15; i++) {

			executor.submit(new Callable<Object>() {
				@Override
				public Object call() throws Exception {
					System.out.println(Thread.currentThread().getName());
					TimeUnit.HOURS.sleep(1);
					return null;
				}

			});
			System.out.println(executor.getActiveCount()+":"+executor.getTaskCount());
		}
		executor.shutdown();
	}
}
