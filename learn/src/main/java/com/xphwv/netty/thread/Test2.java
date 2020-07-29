package com.xphwv.netty.thread;

public class Test2 {
	private String flag = "false";

	public static void main(String[] args) {
		Test2 t = new Test2();
		t.new Thread1().start();
		t.new Thread2().start();
	}

	class Thread1 extends Thread {
		private Thread1() {
			super("thread-1");
		}

		@Override
		public void run() {
			synchronized (flag) {
				System.out.println(Thread.currentThread().getName() + "拿到锁");
				try {
					System.out.println(Thread.currentThread().getName() + "释放锁，等待信号");
					flag.wait();
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
			System.out.println(Thread.currentThread().getName() + "释放锁");
		}

	}

	class Thread2 extends Thread {
		private Thread2() {
			super("thread-2");
		}

		@Override
		public void run() {
			synchronized (flag) {
				System.out.println(Thread.currentThread().getName() + "拿到锁");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				flag.notify();
				System.out.println(Thread.currentThread().getName() + "发出信号");
			}
			System.out.println(Thread.currentThread().getName() + "释放锁");
		}
	}
}
