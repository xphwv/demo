/**  
 * @Copyright 北京网库互通信息技术有限公司 
 */
package com.xphwv.design.builder;

/**
 * @Title: Click.java
 * @Description: TODO
 * @author xupan
 * @Modified xupan
 * @date 2013-9-23 下午4:05:06
 * @version V1.0
 */
public class Click {
	public static void main(String[] args) {
		Director director = new Director();  
		director.getProductA().showProduct();
	}
}
