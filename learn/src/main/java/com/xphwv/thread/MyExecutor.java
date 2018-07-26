package com.xphwv.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * TODO 
 * @author XuPan
 * @date 2015年12月19日 下午2:37:58
 * @version 1.0
 */
public class MyExecutor extends ThreadPoolExecutor{
//	Condition
	
	public MyExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

}
