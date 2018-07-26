/**  
 * @Copyright 北京网库互通信息技术有限公司 
*/
package com.xphwv.design.visitor;

/**
 * @Title:  
 * @author xupan  
 * @Modified xupan      
 * @date 2013-12-17 下午3:37:27   
 * @version V1.0     
 */
public abstract class Element {
	public abstract void accept(IVisitor visitor);
	public abstract void doSomething();
	
}
