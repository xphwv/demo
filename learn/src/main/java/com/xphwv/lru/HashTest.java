package com.xphwv.lru;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 * 
 * @author XuPan
 * @date 2016年8月30日 下午7:31:12
 * @version 1.0
 */

public class HashTest {
	static int hash(int h) {
		// This function ensures that hashCodes that differ only by
		// constant multiples at each bit position have a bounded
		// number of collisions (approximately 8 at default load factor).
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}

	static void hash2(){
		String key="8";
		int hashCode = key.hashCode();
		System.out.println(hashCode);
		System.out.println("9".hashCode());
		System.out.println(Integer.toBinaryString(hashCode));
		System.out.println(Integer.toBinaryString("9".hashCode()));
		System.out.println(Integer.toBinaryString(hash(hashCode)));
		System.out.println(Integer.toBinaryString(hash("9".hashCode())));
	}
	public static void main(String[] args) {
		Map<String,Object>map=new HashMap<String, Object>(3);
		for (int i = 0; i < 5; i++) {
			map.put(String.valueOf(i), i);
		}
		
	}
}
