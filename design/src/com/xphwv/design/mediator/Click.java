/**  
 * @Copyright 北京网库互通信息技术有限公司 
 */
package com.xphwv.design.mediator;

/**
 * @Title: Click.java
 * @Description: TODO
 * @author xupan
 * @Modified xupan
 * @date 2013-9-26 下午4:53:18
 * @version V1.0
 */
public class Click {
	public static void main(String[] args) {
		AbstractColleague ac1 = new Conlleague1();
		AbstractColleague ac2 = new Conlleague2();
		AbstractMediator am=new Mediator(ac1, ac2);
		ac1.setNum(1000,am);
		System.out.println(ac1.getNum()+"---"+ac2.getNum());
		
		ac2.setNum(1000,am);
		System.out.println(ac1.getNum()+"---"+ac2.getNum());
	}
}
