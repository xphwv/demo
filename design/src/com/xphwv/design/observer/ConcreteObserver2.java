/**  
 * @Copyright 北京网库互通信息技术有限公司 
*/
package com.xphwv.design.observer;

/**
 * @Title:  
 * @author xupan  
 * @Modified xupan      
 * @date 2013-12-18 下午7:22:31   
 * @version V1.0     
 */
public class ConcreteObserver2 implements Observer{

	@Override
	public void update() {
		 System.out.println("观察者2收到信息，并进行处理。");  
	}

}
