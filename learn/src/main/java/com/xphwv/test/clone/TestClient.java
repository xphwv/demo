/**  
 * @Copyright 北京网库互通信息技术有限公司 
 */
package com.xphwv.test.clone;


/**
 * @Title: TestClient.java
 * @Description: 此类用来测试原型模式
 * @author xupan
 * @Modified xupan
 * @date 2013-10-22 下午5:49:47
 * @version V1.0
 */
public class TestClient {
	private Mokey mokey = new Mokey();

	public void change(){
		Mokey copymokey2;
		GoldRingdeStaff goldRingdeStaff = new GoldRingdeStaff();
		mokey.setGoldRingdeStaff(goldRingdeStaff);
		
//		copymokey2 = (Mokey) mokey.deepClone();
		copymokey2 = (Mokey) mokey.clone();
		
		goldRingdeStaff.setHeight(1000f);
		mokey.setGoldRingdeStaff(goldRingdeStaff);
		
		System.out.println("monkey birth date :" + mokey.getBirthDate());
		System.out.println("copymokey2 birth date :"+ copymokey2.getBirthDate());
		System.out.println("copymokey2 ==monkey :" + (copymokey2 == mokey));
		System.out.println(copymokey2.getGoldRingdeStaff().getHeight());
		System.out.println(copymokey2.getGoldRingdeStaff());
		System.out.println(mokey.getGoldRingdeStaff());
		System.out.println("copymokey2 staff ==monkey staff:"
				+ (copymokey2.getGoldRingdeStaff() == mokey.getGoldRingdeStaff()));
	}

	public static void main(String[] args){
		TestClient testClient = new TestClient();
		testClient.change();
	}
}
