/**  
 * @Copyright 北京网库互通信息技术有限公司 
*/
package com.xphwv.design.chain;

/**
 * @Title:  
 * @author xupan  
 * @Modified xupan      
 * @date 2013-12-19 上午10:36:51   
 * @version V1.0     
 */
public class Level {
	private int level;

	public Level(int level) {
		this.level = level;
	}
	public boolean above(Level level){
		if(this.level>=level.level){
			return true;
		}
		return false;
	}
}
