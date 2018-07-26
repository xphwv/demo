/**  
 * @Copyright 北京网库互通信息技术有限公司 
 */
package com.xphwv.design.command;

/**
 * @Title:
 * @author xupan
 * @Modified xupan
 * @date 2013-12-17 下午4:42:45
 * @version V1.0
 */
public class Client {
	public static void main(String[] args) {
		Receiver receiver = new Receiver();
		Command command = new ConcreteCommand(receiver);
		command.execute();
		Invoker invoker=new Invoker();
		invoker.setCommand(command);
		
		invoker.action();
	}
}
