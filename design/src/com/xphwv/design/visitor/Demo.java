/**  
 * @Copyright 北京网库互通信息技术有限公司 
 */
package com.xphwv.design.visitor;

/**
 * @Title:
 * @author xupan
 * @Modified xupan
 * @date 2013-12-17 下午4:02:21
 * @version V1.0
 */
public class Demo {
	public static void main(String[] args) {
		Father father = new Father();
		Father s1 = new Son1();
		Father s2 = new Son2();

		Execute exe = new Execute();
		father.accept(exe);
		s1.accept(exe);
		s2.accept(exe);
	}
}

class Father implements Accept {
	@Override
	public void accept(Execute execute) {
		execute.method(this);
	}
}

class Son1 extends Father {

	@Override
	public void accept(Execute execute) {
		execute.method(this);
	}

}

class Son2 extends Father {
	@Override
	public void accept(Execute execute) {
		execute.method(this);
	}
}

interface Accept {
	void accept(Execute execute);
}

class Execute {
	public void method(Father father) {
		System.out.println("father");
	}

	public void method(Son1 son1) {
		System.out.println("son1");
	}

	public void method(Son2 son2) {
		System.out.println("son2");
	}
}