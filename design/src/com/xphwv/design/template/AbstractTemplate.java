/**  
 * @Copyright 北京网库互通信息技术有限公司 
 */
package com.xphwv.design.template;

/**
 * @Title: Template.java
 * @Description: TODO
 * @author xupan
 * @Modified xupan
 * @date 2013-9-23 下午5:11:03
 * @version V1.0
 */
public abstract class AbstractTemplate {
	public void run() {
		System.out.println(this.getMsg());;
	}

	abstract protected String getMsg() ;
}
