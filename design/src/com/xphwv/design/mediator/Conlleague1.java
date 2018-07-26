/**  
 * @Copyright 北京网库互通信息技术有限公司 
*/
package com.xphwv.design.mediator;

/**
 * @Title: Conlleague1.java 
 * @Description: TODO  
 * @author xupan  
 * @Modified xupan      
 * @date 2013-9-26 下午4:31:52   
 * @version V1.0     
 */
public class Conlleague1 extends AbstractColleague {

	@Override
	void setNum(int num, AbstractMediator mediator) {
		this.num=num;
		mediator.setA1();
	}

}
