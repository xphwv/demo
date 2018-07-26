package com.xphwv.zookeeper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.Test;

public class ClientTest {
	private static int threadCount = 1;
	private static CountDownLatch threadsSemaphore = new CountDownLatch(threadCount);
	private static Logger logger = Logger.getLogger(ClientTest.class);

	@Test
	public void test() {
		for (int i = 0; i < threadCount; i++) {
			final int threadId = i + 1;
			new Thread() {
				@Override
				public void run() {
					try {
						DistributedLock dc = new DistributedLock(threadId);
//						threadsSemaphore.countDown();
//						threadsSemaphore.await();
						dc.getLock();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}.start();
		}
		try {
			TimeUnit.MINUTES.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
