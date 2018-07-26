/**  
 * @Copyright 北京网库互通信息技术有限公司 
 */
package com.xphwv.design.command;

/**
 * @Title: Invoker类：调用者，负责调用命令
 * @author xupan
 * @Modified xupan
 * @date 2013-12-17 下午4:38:21
 * @version V1.0
 */
public class Invoker {
	private Command command;
	public void setCommand(Command command){
		this.command=command;
	}
	public void action(){
		this.command.execute();
	}
}
