/**  
 * @Copyright 北京网库互通信息技术有限公司 
 */
package com.xphwv.design.command;

/**
 * @Title:
 * @author xupan
 * @Modified xupan
 * @date 2013-12-17 下午4:41:43
 * @version V1.0
 */
public class ConcreteCommand extends Command {
	private Receiver receiver;

	public ConcreteCommand(Receiver receiver) {
		this.receiver = receiver;
	}

	@Override
	public void execute() {
		this.receiver.doSomething();
	}

}
