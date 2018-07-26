package com.xphwv.sort;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO
 * 
 * @author XuPan
 * @date 2016年8月30日 下午4:24:43
 * @version 1.0
 */
public class BubbleSort {

	static int[] array = { 89, 97, 92, 94, 2, 1 };

	public static void bubbleSort(int[] array) {
		Arrays.sort(array);
		AtomicInteger count = new AtomicInteger();
		System.out.println(array.length);
		for (int i = 0; i < array.length - 1; i++) {
			for (int j = 0; j < array.length-i - 1; j++) {
				count.incrementAndGet();
				if (array[j] > array[j + 1]) {
					int tem = array[j];
					array[j] = array[j + 1];
					array[j + 1] = tem;
				}
			}
		}
		System.out.println(Arrays.toString(array));
		System.out.println(count.get());
	}

	public static void main(String[] args) {
		bubbleSort(array);
	}
}
