package com.xphwv.lru;

import java.util.LinkedHashMap;

/**
 * TODO
 * 
 * @author XuPan
 * @date 2016年8月30日 下午6:02:54
 * @version 1.0
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
	private final int capacity;
	
	public LRUCache(int capacity) {
		this.capacity=capacity;
	}

	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
		return this.size()>capacity;
	}

}
