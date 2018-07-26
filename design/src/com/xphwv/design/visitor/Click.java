/**  
 * @Copyright 北京网库互通信息技术有限公司 
 */
package com.xphwv.design.visitor;

import java.util.List;

/**
 * @Title:
 * @author xupan
 * @Modified xupan
 * @date 2013-12-17 下午3:46:47
 * @version V1.0
 */
public class Click {
	public static void main(String[] args) {
		List<Element> list = ObjectStruture.getList();
		for (Element e : list) {
			e.accept(new Visitor());
		}
	}
}
