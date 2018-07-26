/**  
 * @Copyright 北京网库互通信息技术有限公司 
*/
package com.xphwv.design.visitor;

/**
 * @Title:  
 * @author xupan  
 * @Modified xupan      
 * @date 2013-12-17 下午3:42:20   
 * @version V1.0     
 */
public class ConcreatedElement2  extends Element{

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public void doSomething() {
		System.out.println("这是元素2");
	}

}
