/**  
 * @Copyright 北京网库互通信息技术有限公司 
*/
package com.xphwv.design.visitor;

/**
 * @Title:  
 * @author xupan  
 * @Modified xupan      
 * @date 2013-12-17 下午3:38:33   
 * @version V1.0     
 */
public interface IVisitor {
	public void visit(ConcreatedElement1 element1);
	public void visit(ConcreatedElement2 element2);
}
