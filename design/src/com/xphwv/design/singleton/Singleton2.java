package com.xphwv.design.singleton;

/**
 * TODO
 * 
 * @author XuPan
 * @date 2016年8月12日 下午2:23:06
 * @version 1.0
 */
public class Singleton2 {
	private volatile static Singleton2 install=null;
	private Singleton2() {
	}
	
	public static Singleton2 getInstall(){
		if(install==null){
			synchronized (Singleton2.class) {
				if(install==null){
					install=new Singleton2();
				}
			}
		}
		return install;
	}

	public static void main(String[] args) {
		Singleton2.getInstall();
	}

}
