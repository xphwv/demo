/**  
 * @Copyright 五八信息技术有限公司 
 */
package com.xphwv.test.th;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;


/**
 * TODO
 * 
 * @author XuPan
 * @date 2014-6-4 下午5:16:13
 * @version 1.0
 */
public class ExecutorsTest {

	public static void main(String[] args) {
		  /* 
         * 每个创建线程池的方法都有ThreadFactory的重载 
         */  
        Executors threadPool = (Executors) Executors.newFixedThreadPool(10, new ThreadFactory() {  
  
            @Override  
            public Thread newThread(Runnable r) {  
                /* 
                 * 使用ThreadFactory能控制线程产生时的细节操作 
                 */  
                Thread thread = new Thread(r);  
                // 设置为守护线程  
                thread.setDaemon(true);  
                // 线程优先级为最高  
                thread.setPriority(Thread.MAX_PRIORITY);  
                return thread;  
            }  
        });  
	}
}
