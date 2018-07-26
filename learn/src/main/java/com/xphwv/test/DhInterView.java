package com.xphwv.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Title: DhInterView.java 
 * @Description: 敦煌网面试
 * @author xupan  
 * @Modified xupan      
 * @date 2013-10-25 上午11:39:58   
 * @version V1.0     
*/
public class DhInterView {
	public static void main(String[] args) {
		// print();
		Integer[] array = { -8, 1, 23, -9, -1, 2, 13, 4, 6, -2, -13, 88 };
		searchAbs1(array);
		searchAbs2(array);
	}
	private static void searchAbs2(Integer[] array) {
		Arrays.sort(array);
		int i = Math.abs(Arrays.binarySearch(array, 0)) - 1;
		Integer[] ay1, ay2;
		ay1 = Arrays.copyOfRange(array, 0, i);
		ay2 = Arrays.copyOfRange(array, i, array.length);
		count(Arrays.asList(ay1), Arrays.asList(ay2));
	}

	private static void searchAbs1(Integer[] ary) {
		List<Integer> list = Arrays.asList(ary);
		Collections.sort(list);
		int j = 0;
		int size = list.size();
		for (int i = 0; i < size; i++) {
			if (list.get(i) >= 0) {
				j = i;
				break;
			}
		}
		List<Integer> list1 = list.subList(0, j);
		List<Integer> list2 = list.subList(j, size);
		count(list1, list2);
	}

	private static void count(List<Integer> list1, List<Integer> list2) {
		int count = 0;
		int size = list1.size();
		for (int i = 0; i < size; i++) {
			if (list2.contains(Math.abs(list1.get(i)))) {
				count++;
			}
		}
		System.out.println(count);
	}

	/**
	 * 将1234打印成1234,123,234,12,23,34,1,2,3,4
	 * 
	 * @exception
	 * @since 1.0
	 */
	private static void print() {
		String s = "12345";
		for (int i = s.length(); i > 0; i--) {
			for (int j = 0; i + j <= s.length(); j++) {
				System.out.print(s.substring(j, i + j) + ", ");
			}
		}
	}
}
