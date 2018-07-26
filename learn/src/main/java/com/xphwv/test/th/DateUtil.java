/**  
 * @Copyright 五八信息技术有限公司 
 */
package com.xphwv.test.th;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO
 * 
 * @author XuPan
 * @date 2014-5-4 下午5:54:16
 * @version 1.0
 */
public class DateUtil {
	private final static String FORMAT = "yyyy-MM-dd hh:mm:ms";
	public static ThreadLocal<Format> tl=new ThreadLocal<Format>(){
		@Override
		protected synchronized Format initialValue() {
			System.out.println("init");
			return new SimpleDateFormat(FORMAT);
		}
	};
	public static DateFormat  get(){
		return (DateFormat) tl.get();
	}
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			TestThread tt=new TestThread();
			tt.start();
		}
	}
}

class TestThread extends Thread{

	DateFormat df=DateUtil.get();
	@Override
	public void run() {
		String str=df.format(new Date());
	
		System.out.println(this.getId()+"--"+str+"--"+df.hashCode());
	}
	
}
