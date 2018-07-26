/**  
 * @Copyright 五八信息技术有限公司 
 */
package com.xphwv.test.th.play;

import java.util.Random;

/**
 * 用来设定目标数字以及判断猜测的结果
 * @author XuPan
 * @date 2014-5-9 下午4:11:20
 * @version 1.0
 */
public class Judge {
	public final static int  MAX_INT=10;
	private static int targetValue;
	/**
	 *预设一个随机数
	 */
	public static void prepare(){
		Random r=new Random();
		targetValue=r.nextInt(MAX_INT)+1;
	}
	/**
	 *判断是否和预设数字相同
	 */
	public static boolean judge(int value){
		return value==targetValue;
	}
}
