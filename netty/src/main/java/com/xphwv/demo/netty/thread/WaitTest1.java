package com.xphwv.demo.netty.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class WaitTest1 {

	public static void main(String[] args) {

		ThreadA ta = new ThreadA("ta");

		synchronized (ta) { // 通过synchronized(ta)获取“对象ta的同步锁”
			try {
				System.out.println(Thread.currentThread().getName() + " start ta");
				ta.start();

				System.out.println(Thread.currentThread().getName() + " block");
				// 主线程等待
				ta.wait();

				System.out.println(Thread.currentThread().getName() + " continue");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

    static class ThreadA extends Thread{

        public ThreadA(String name) {
            super(name);
        }

        public void run() {
            synchronized (this) { // 通过synchronized(this)获取“当前对象的同步锁”
            	System.out.println(LockSupport.getBlocker(this));
                System.out.println(Thread.currentThread().getName()+" wakup others");
                try {
					TimeUnit.SECONDS.sleep(10);
					notify();    // 唤醒“当前对象上的等待线程”
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
        }
    }
}