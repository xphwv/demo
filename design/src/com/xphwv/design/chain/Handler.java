/**  
 * @Copyright 北京网库互通信息技术有限公司 
*/
package com.xphwv.design.chain;

/**
 * @Title:  
 * @author xupan  
 * @Modified xupan      
 * @date 2013-12-19 上午11:25:44   
 * @version V1.0     
 */
public abstract class Handler {
	private Handler nextHander;
	public final Response handlerRequest(Request request){
		Response response=null;
		if(this.getHanderLevel().above(request.getLevel())){
			response=this.response(request);
		}else{
			if(this.nextHander!=null){
				this.nextHander.handlerRequest(request);
			}else{
				System.out.println("没有合适处理器");
			}
		}
		return response;
	}
	
	public void setNextHander(Handler nextHander) {
		this.nextHander = nextHander;
	}

	protected abstract Level getHanderLevel();
	public abstract Response response(Request request);
}
