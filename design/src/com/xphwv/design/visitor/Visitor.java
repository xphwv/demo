/**  
 * @Copyright 北京网库互通信息技术有限公司 
*/
package com.xphwv.design.visitor;

/**
 * @Title:  
 * @author xupan  
 * @Modified xupan      
 * @date 2013-12-17 下午3:44:02   
 * @version V1.0     
 */
public class Visitor implements IVisitor {

	@Override
	public void visit(ConcreatedElement1 element1) {
		element1.doSomething();
	}

	@Override
	public void visit(ConcreatedElement2 element2) {
		element2.doSomething();
	}

}
