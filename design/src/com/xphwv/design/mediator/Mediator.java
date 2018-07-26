/**  
 * @Copyright 北京网库互通信息技术有限公司 
 */
package com.xphwv.design.mediator;

/**
 * @Title: Mediator.java
 * @Description: TODO
 * @author xupan
 * @Modified xupan
 * @date 2013-9-26 下午4:40:31
 * @version V1.0
 */
public class Mediator extends AbstractMediator {

	/**
	 * 创建一个新的实例 Mediator.
	 * 
	 * @param a1
	 * @param a2
	 */
	protected Mediator(AbstractColleague a1, AbstractColleague a2) {
		super(a1, a2);
	}

	@Override
	public void setA1() {
		a2.setNum(a1.getNum() * 100);
	}

	@Override
	public void setA2() {
		a1.setNum(a2.getNum() / 100);
	}

}
