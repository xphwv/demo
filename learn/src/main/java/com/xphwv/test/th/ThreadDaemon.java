/**  
 * @Copyright 五八信息技术有限公司 
 */
package com.xphwv.test.th;

import java.util.concurrent.TimeUnit;

/**
 * TODO
 * 
 * @author XuPan
 * @date 2014-6-4 下午5:26:37
 * @version 1.0
 */
public class ThreadDaemon {
	public static void main(String[] args) {
		MyThread m=new MyThread();
//		m.setDaemon(true);
		m.start();
		System.out.println("main over......");
		System.out.println("方法返回");
	}
}

class MyThread extends Thread {

	@Override
	public void run() {
		System.out.println("连接接口开始执行");
		for (int i=0;i<100;i++) {
			try {
				System.out.println("尝试连接次数"+i);
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("连接接口执行结束");
	}

}