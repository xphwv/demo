/**  
 * @Copyright 北京网库互通信息技术有限公司 
*/
package com.xphwv.design.observer;

/**
 * @Title: ConcreteSubject.java 
 * @Description: TODO  
 * @author xupan  
 * @Modified xupan      
 * @date 2013-10-17 下午5:01:27   
 * @version V1.0     
 */
public class ConcreteSubject extends Subject {

	@Override
	public void dosomething() {
		System.out.println("被观察者事件反生");  
		this.notifyObserver();
	}
}
