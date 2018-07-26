/**  
 * @Copyright 北京网库互通信息技术有限公司 
*/
package com.xphwv.design.prototype;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: Prototype.java 
 * @Description: TODO  
 * @author xupan  
 * @Modified xupan      
 * @date 2013-9-23 下午4:31:18   
 * @version V1.0     
 */
public class Prototype implements Cloneable{
	protected List list=new ArrayList<String>();
	
	public List getList() {
		return list;
	}

	public void setList(List list2) {
		this.list = list2;
	}

	@Override
	protected Prototype clone(){
		 Prototype prototype = null;  
	        try{  
	            prototype = (Prototype)super.clone();  
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();  
	        }  
	        return prototype;   
	    }  

}
