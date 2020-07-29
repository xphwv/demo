package com.xphwv.netty.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Test1 {
	private ReentrantLock lock = new ReentrantLock();
	private Condition con = lock.newCondition();

	public static void main(String[] args) {
		Test1 t = new Test1();
		t.new Thread1().start();
		t.new Thread2().start();
	}

	class Thread1 extends Thread {
		private Thread1() {
			super("thread-1");
		}

		@Override
		public void run() {
			lock.lock();
			System.out.println(Thread.currentThread().getName() + "拿到锁");
			try {
				System.out.println(Thread.currentThread().getName() + "释放锁，等待信号");
				con.await();
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			lock.unlock();
			System.out.println(Thread.currentThread().getName() + "释放锁");
		}

	}

	class Thread2 extends Thread {
		private Thread2() {
			super("thread-2");
		}

		@Override
		public void run() {
			lock.lock();
			System.out.println(Thread.currentThread().getName() + "拿到锁");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			con.signal();
			System.out.println(Thread.currentThread().getName() + "发出信号");
			lock.unlock();
			System.out.println(Thread.currentThread().getName() + "释放锁");
		}
	}
}
