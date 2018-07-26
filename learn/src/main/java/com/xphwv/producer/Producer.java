package com.xphwv.producer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 * 
 * @author XuPan
 * @date 2016年8月19日 下午5:49:23
 * @version 1.0
 */
public class Producer<T> implements Runnable {
	private BlockingQueue<T> queue;

	public Producer(BlockingQueue<T> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			for (int i = 0; i < 10; i++) {
				queue.put((T) ("Produced: " + i));
				System.out.println("Produced: " + i+"-->"+System.currentTimeMillis());
				TimeUnit.MILLISECONDS.sleep(100);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
