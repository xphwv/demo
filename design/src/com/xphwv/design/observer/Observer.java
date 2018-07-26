/**  
 * @Copyright 北京网库互通信息技术有限公司 
 */
package com.xphwv.design.observer;

/**
 * @Title: Observer.java
 * 观察者：观察者角色一般是一个接口，它只有一个update方法，在被观察者状态发生变化时，这个方法就会被触发调用。
 * @author xupan
 * @Modified xupan
 * @date 2013-9-29 上午9:55:20
 * @version V1.0
 */
public interface Observer {
	void update();
}
