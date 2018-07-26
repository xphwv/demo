/**  
 * @Copyright 北京网库互通信息技术有限公司 
*/
package com.xphwv.design.mediator;

/**
 * @Title: AbstractColleague.java 
 * @Description: TODO  
 * @author xupan  
 * @Modified xupan      
 * @date 2013-9-23 下午5:49:10   
 * @version V1.0     
 */
public abstract class AbstractColleague {
	protected int num;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	abstract void setNum(int num,AbstractMediator mediator);
}
