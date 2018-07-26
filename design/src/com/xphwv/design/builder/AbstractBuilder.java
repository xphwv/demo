/**  
 * @Copyright 北京网库互通信息技术有限公司 
 */
package com.xphwv.design.builder;

/**
 * @Title: Builder.java
 *         抽象建造者：引入抽象建造者的目的，是为了将建造的具体过程交与它的子类来实现。这样更容易扩展。一般至少会有两个抽象方法
 *         ，一个用来建造产品，一个是用来返回产品
 * @author xupan
 * @Modified xupan
 * 
 * @date 2013-9-23 下午3:23:34
 * @version V1.0
 */
abstract public class AbstractBuilder {
	abstract void setProduct(String name, String type);

	abstract Product getProduct();
}
