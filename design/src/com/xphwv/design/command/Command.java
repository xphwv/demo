/**  
 * @Copyright 北京网库互通信息技术有限公司 
*/
package com.xphwv.design.command;

/**
 * @Title:  是一个抽象类，类中对需要执行的命令进行声明，一般来说要对外公布一个execute方法用来执行命令。
 * @author xupan  
 * @Modified xupan      
 * @date 2013-12-17 下午4:37:38   
 * @version V1.0     
 */
public abstract class Command {
	public abstract void execute();
}
