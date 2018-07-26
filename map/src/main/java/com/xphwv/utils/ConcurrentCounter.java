package com.xphwv.utils;


import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentCounter {

	private AtomicInteger n = new AtomicInteger();
	public ConcurrentCounter() {
	}

	public ConcurrentCounter(int count) {
		n.set(count);
	}

	public int inc() {
		return n.incrementAndGet();

	}

	public int dec() {
		return n.decrementAndGet();
	}

	public int getCount() {
		return n.get();
	}
}
