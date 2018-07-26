/**  
 * @Copyright 五八信息技术有限公司 
 */
package com.xphwv.test.th.play;

import java.util.Random;

/**
 * 具有ThreadLocal字段和猜测动作静态方法的类，ThreadLocal用于保存猜过的数字。
 * 
 * @author XuPan
 * @date 2014-5-9 下午4:33:27
 * @version 1.0
 */
public class Attempt {
	private static ThreadLocal<Record> history = new ThreadLocal<Record>();

	public static int guess(int maxValue) {
		Record record = getRecord();
		Random random = new Random();
		int value = 0;
		do {
			value = random.nextInt(maxValue) + 1;
		} while (record.contains(value));
		record.save(value);
		return value;
	}
	public static void review(String info){
		System.out.println(info+getRecord());
	}

	private static Record getRecord() {
		Record record = history.get();
		if (record == null) {
			record = new Record();
			history.set(record);
		}
		return record;
	}
}
