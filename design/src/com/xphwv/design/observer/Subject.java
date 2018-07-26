/**  
 * @Copyright 北京网库互通信息技术有限公司 
*/
package com.xphwv.design.observer;

import java.util.Vector;

/**
 * @Title: Subject.java 
 * @Description: TODO  
 * @author xupan  
 * @Modified xupan      
 * @date 2013-9-29 上午9:56:08   
 * @version V1.0     
 */
public abstract class Subject {
	private Vector<Observer> vector=new Vector<Observer>();
	public void  attach(Observer o){
		vector.add(o);
	}
	public void detach(Observer o){
		vector.remove(o);
	}
	protected void notifyObserver() {
		for (int i = 0; i < vector.size(); i++) {
			vector.get(i).update();
		}
	}
	public abstract void dosomething();
}
