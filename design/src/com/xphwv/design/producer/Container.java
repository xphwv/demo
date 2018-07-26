package com.xphwv.design.producer;

import java.util.ArrayList;
import java.util.List;

/**
 * 容器
 * 
 * @author XuPan
 * @date 2016年8月12日 下午3:52:49
 * @version 1.0
 * @param <E>
 */
public class Container<T> {
	private List<T> list = null;
	private int capacity;

	public Container(int capacity) {
		this.capacity = capacity;
		list = new ArrayList<T>(capacity);
	}

	public synchronized boolean add(T e) {
		if (list.size() < capacity) {
			list.add(e);
			return true;
		}
		return false;
	}

	public synchronized boolean isFull() {
		return list.size() > capacity;
	}

	public synchronized T get() {
		if (list.size() > 0) {
			return list.remove(0);
		}
		return null;
	}
}
