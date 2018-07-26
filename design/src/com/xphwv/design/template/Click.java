/**  
 * @Copyright 北京网库互通信息技术有限公司 
 */
package com.xphwv.design.template;

/**
 * @Title: Click.java
 * @Description: TODO
 * @author xupan
 * @Modified xupan
 * @date 2013-9-23 下午5:16:59
 * @version V1.0
 */
public class Click {
	public static void main(String[] args) {
		AbstractTemplate template1 = new TemplateImpl1();
		template1.run();
		AbstractTemplate template2 = new TemplateImpl2();
		template2.run();
	}
}
