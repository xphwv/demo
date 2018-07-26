package com.xphwv.design.singleton;
/**
 * TODO 
 * @author XuPan
 * @date 2016年8月12日 下午2:50:32
 * @version 1.0
 */
public class Singleton3 {
	private static class SingletonInstall{
		private static Singleton3 install=new Singleton3();
	}
	public static Singleton3 getInstall(){
		return SingletonInstall.install;
	}
	
	public static void main(String[] args) {
		Singleton3 install=Singleton3.getInstall();
		System.out.println(install.hashCode());
	}
}
