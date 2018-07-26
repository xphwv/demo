/**  
 * @Copyright 北京网库互通信息技术有限公司 
 */
package com.xphwv.design.observer;

/**
 * @Title:
 * @author xupan
 * @Modified xupan
 * @date 2013-12-18 下午7:50:31
 * @version V1.0
 */
public class Client {
	public static void main(String[] args) {
		Observer observe1 = new ConcreteObserver1();
		Observer observe2= new ConcreteObserver2();
		Subject subject=new ConcreteSubject();
		subject.attach(observe1);
		subject.attach(observe2);
		subject.dosomething();
	}
}
