package com.xphwv.thread;

/**
 * TODO
 * 
 * @author XuPan
 * @date 2015年12月28日 下午5:08:10
 * @version 1.0
 */

public class NotifyAllTest {
	private static Object obj=new Object();
	public static void main(String[] args) {
		for (int i = 0; i < 3; i++) {
			Thread t1 = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						obj.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("开始执行了");
				}
			});
		}
	}
}
