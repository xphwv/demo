/**  
 * @Copyright 北京网库互通信息技术有限公司 
 */
package com.xphwv.design.builder;

/**
 * @Title: Director.java
 *         导演类：负责调用适当的建造者来组建产品，导演类一般不与产品类发生依赖关系，与导演类直接交互的是建造者类。一般来说
 *         ，导演类被用来封装程序中易变的部分。
 * @author xupan
 * @Modified xupan
 * @date 2013-9-23 下午3:35:27
 * @version V1.0
 */
public class Director {
	private AbstractBuilder builder=new BuilderImpl();
	public Product getProductA(){
		builder.setProduct("宝马", "AAA");
		return builder.getProduct();
	}
}
