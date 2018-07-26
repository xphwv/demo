package com.xphwv.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO
 * 
 * @author XuPan
 * @date 2016年7月29日 下午2:41:38
 * @version 1.0
 */
public class TestLock {
	
 	public static void main(String[] args) {
		ReentrantLock lock = new ReentrantLock();
		lock.lock();
		try {

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (lock != null)
				lock.unlock();
		}

	}

	class MyThread implements Runnable {

		@Override
		public void run() {
			try {
				System.out.println("begin");
				Thread.sleep(1000);
				System.out.println("end");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
