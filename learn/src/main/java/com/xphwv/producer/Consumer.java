package com.xphwv.producer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 * 
 * @author XuPan
 * @date 2016年8月19日 下午5:49:59
 * @version 1.0
 */
public class Consumer<T> implements Runnable {
	private BlockingQueue<T> queue;

	public Consumer(BlockingQueue<T> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			while (true) {
				T t = queue.take();
				System.out.println("Consumer--->:" + t);
				TimeUnit.MILLISECONDS.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
