/**  
 * @Copyright 北京网库互通信息技术有限公司 
 */
package com.xphwv.design.builder;

/**
 * @Title: Product.java
 * @Description: 
 * 产品类：一般是一个较为复杂的对象，也就是说创建对象的过程比较复杂，一般会有比较多的代码量。
 *  在本类图中，产品类是一个具体的类，而非抽象类。实际编程中，产品类可以是由一个抽象类与它
 *  的不同实现组成，也可以是由多个抽象类与他们的实现组成
 * @author xupan
 * @Modified xupan
 * @date 2013-9-23 下午2:40:10
 * @version V1.0
 */
public class Product {
	private String name;
	private String type;
	public void setName(String name) {
		this.name = name;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void showProduct(){
		System.out.println("产品名称："+this.name+";规格："+this.type);
	}
}
