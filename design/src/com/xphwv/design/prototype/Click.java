/**  
 * @Copyright 北京网库互通信息技术有限公司 
 */
package com.xphwv.design.prototype;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: Click.java
 * @Description: TODO
 * @author xupan
 * @Modified xupan
 * @date 2013-9-23 下午4:33:24
 * @version V1.0
 */
public class Click {
	public static void main(String[] args) {
		ConcretePrototype cp = new ConcretePrototype();
		List list=new ArrayList(); 
		list.add("aaaa");
		list.add("aaaa");
		cp.setList(list);
		ConcretePrototype p = (ConcretePrototype) cp.clone();
		System.err.println(cp.clone().equals(cp));
		p.show();
		System.out.println(p.list);
	}
}
