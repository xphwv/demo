/**  
 * @Copyright 北京网库互通信息技术有限公司 
 */
package com.xphwv.design.builder;

/**
 * @Title: Builder.java 
 * 建造者：实现抽象类的所有未实现的方法，具体来说一般是两项任务：组建产品；返回组建好的产品。
 * @author xupan
 * @Modified xupan
 * @date 2013-9-23 下午3:33:10
 * @version V1.0
 */
public class BuilderImpl extends AbstractBuilder{
	private Product product = new Product();  
	@Override
	void setProduct(String name, String type) {
		product.setName(name);
		product.setType(type);
	}

	@Override
	Product getProduct() {
		return product;
	}

}
