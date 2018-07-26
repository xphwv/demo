package com.xphwv.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * TODO 
 * @author XuPan
 * @date 2016年8月11日 下午4:37:35
 * @version 1.0
 */
public  class ThreadLocalTest {
	public static void main(String[] args) {
		ThreadPool  pool1=new TestCachedThreadPool();
		pool1.execute();
	}
}
interface ThreadPool{
	void execute();
}
class TestCachedThreadPool implements ThreadPool{

	@Override
	public void execute() {
		ExecutorService service=Executors.newCachedThreadPool();
		for (int i = 0; i < 10; i++) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			service.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println(Thread.currentThread().getId());
				}
			});
		}
	}
	
}