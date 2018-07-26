/**  
 * @Copyright 五八信息技术有限公司 
 */
package com.xphwv.test.th;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 获取vip订单流水
 * 
 * @author XuPan
 * @date 2014-5-15 下午6:35:48
 * @version 1.0
 */
public class Provider1 {
	private boolean bol = false;
	private List<String> list = new ArrayList<String>();

	public boolean isExecuteBol() {
		ExecutorService es = Executors.newFixedThreadPool(4);
		List<Future<Boolean>> futures = new ArrayList<Future<Boolean>>();
		try {
			ProviderCallable pc = null;
			Future<Boolean> future = null;
			for (int i = 1; i < 5; i++) {
				pc = this.new ProviderCallable(i);
				future = es.submit(pc);
				futures.add(future);
			}
//			for (Future<Boolean> futureTask : futures) {
//				bol = futureTask.get();
//				System.out.println(futureTask.isCancelled());
//				Thread.sleep(10000);
//			}
			es.shutdown();
		} catch (Exception e) {
		}
		return bol;
	}

	public boolean setList(int index) {
		for (int i = 0; i < 10; i++) {
			System.out.println("aaaaaa");
			list.add("index" + index + "---" + i);
		}
		return true;
	}

	class ProviderCallable implements Callable<Boolean> {
		private int index;

		public ProviderCallable(int index) {
			this.index = index;

		}

		@Override
		public Boolean call() throws Exception {
			System.out.println("aaaaaaaaaaaaaa");
			return setList(index);
		}

	}

	public static void main(String[] args) {
		Provider1 p = new Provider1();
		if (p.isExecuteBol())
			System.out.println(p.list.size());
	}
}
