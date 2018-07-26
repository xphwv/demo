/**  
 * @Copyright 北京网库互通信息技术有限公司 
*/
package com.xphwv.design.mediator;

/**
 * @Title: AbstractMediator.java 
 * @Description: TODO  
 * @author xupan  
 * @Modified xupan      
 * @date 2013-9-23 下午5:50:50   
 * @version V1.0     
 */
public abstract class AbstractMediator {
	AbstractColleague a1;
	AbstractColleague a2;
	
	protected  AbstractMediator(AbstractColleague a1,AbstractColleague a2){
		this.a1=a1;
		this.a2=a2;
	}
	public abstract void setA1();
	public abstract void setA2();
}
