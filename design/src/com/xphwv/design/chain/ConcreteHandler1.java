/**  
 * @Copyright 北京网库互通信息技术有限公司 
*/
package com.xphwv.design.chain;

/**
 * @Title:  
 * @author xupan  
 * @Modified xupan      
 * @date 2013-12-19 上午11:35:25   
 * @version V1.0     
 */
public class ConcreteHandler1 extends Handler {

	@Override
	 protected Level getHanderLevel() {  
        return new Level(1);  
    }  
	 @Override
    public Response response(Request request) {  
        System.out.println("-----请求由处理器1进行处理-----");  
        return null;  
    }

}
