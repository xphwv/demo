package com.xphwv.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO
 * 
 * @author XuPan
 * @date 2016年8月29日 下午2:44:11
 * @version 1.0
 */

public class AtomicIntegerTest {
	public static void main(String[] args) {
		AtomicInteger count = new AtomicInteger(10);
		boolean bol = count.compareAndSet(10, 9);
		System.out.println(bol);
	}
}
