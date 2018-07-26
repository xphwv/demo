/**  
 * @Copyright 北京网库互通信息技术有限公司 
 */
package com.xphwv.design.command;

/**
 * @Title: 接收者，负责接收命令并且执行命令。
 * @author xupan
 * @Modified xupan
 * @date 2013-12-17 下午4:39:43
 * @version V1.0
 */
public class Receiver {
	public void doSomething() {
		System.out.println("接受者-业务逻辑处理");
	}
}
