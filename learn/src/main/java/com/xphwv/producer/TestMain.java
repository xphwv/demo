package com.xphwv.producer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO
 * 
 * @author XuPan
 * @date 2016年8月19日 下午5:49:14
 * @version 1.0
 */
public class TestMain {

	private final static Lock lock = new ReentrantLock();
	private final static Condition con = lock.newCondition();

	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<String> queue = new ArrayBlockingQueue<String>(100);
		Producer<String> p = new Producer<String>(queue);
		Consumer<String> c = new Consumer<String>(queue);
		new Thread(c).start();
		new Thread(p).start();
		TimeUnit.SECONDS.sleep(1);
		p.run();

	}
}
