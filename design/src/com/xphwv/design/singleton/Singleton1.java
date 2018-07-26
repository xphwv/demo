package com.xphwv.design.singleton;

/**
 * TODO
 * 
 * @author XuPan
 * @date 2015年12月18日 上午11:31:21
 * @version 1.0
 */
public class Singleton1 {
	private static class SingletonInner {
		public static Singleton1 instanll = new Singleton1();
		static {
			System.out.println("a");
		}
	}

	private Singleton1() {
		System.out.println("b");
	}

	public static Singleton1 getInstall() {
		return SingletonInner.instanll;
	}

	public static void main(String[] args) {
		System.out.println("aa");
		Singleton1.getInstall();
	}
}
