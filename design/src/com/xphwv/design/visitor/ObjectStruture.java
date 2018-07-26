/**  
 * @Copyright 北京网库互通信息技术有限公司 
*/
package com.xphwv.design.visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Title:  结构对象：一个元素的容器，一般包含一个容纳多个不同类、不同接口的容器，如List、Set、Map等，在项目中一般很少抽象出这个角色。
 * @author xupan  
 * @Modified xupan      
 * @date 2013-12-17 下午3:45:25   
 * @version V1.0     
 */
public class ObjectStruture {
	public static List<Element> getList(){  
        List<Element> list = new ArrayList<Element>();  
        Random ran = new Random();  
        for(int i=0; i<10; i++){  
            int a = ran.nextInt(100);  
            if(a>50){  
                list.add(new ConcreatedElement1());  
            }else{  
                list.add(new ConcreatedElement2());  
            }  
        }  
        return list;  
    }  
}
