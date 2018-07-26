/**  
 * @Copyright 五八信息技术有限公司 
 */
package com.xphwv.test.th;

/**
 * TODO
 * 
 * @author XuPan
 * @date 2014-5-12 上午11:03:19
 * @version 1.0
 */
public class ThreadPool {
	public static void main(String[] args) throws Exception {
		String name = Thread.currentThread().getName()
				+ Thread.currentThread().getId();
		System.out.println(name);
		Thread t1 = new Thread();
		Thread t2=new Thread(t1);
		t1.start();
		Thread.sleep(2000);
		t2.start();
		t2.join();// 在代碼2里，將此處注釋掉
		System.out.println(name + " end!");
	}
}
