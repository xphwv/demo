/**  
 * @Copyright 北京网库互通信息技术有限公司 
 */
package com.xphwv.test.java;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;

/**
 * @Title: Test1.java
 * @Description: TODO
 * @author xupan
 * @Modified xupan
 * @date 2013-10-24 上午11:36:43
 * @version V1.0
 */
public class Click {
	public static void main(String[] args) {
		Test t = new Test();
		t.write();
		Test tt=(Test) t.read();
		System.out.println(tt.getA());
		System.out.println(tt.getB());
		System.out.println(tt.getHt().get("test"));
	}

}

class Test implements Serializable {
	private static final long serialVersionUID = 1L;
	private transient String a = "我是字符串，不被序列化";
	private String b = "我是字符串，可以序列化";
	private static Click t = new Click();
	private Hashtable ht=new Hashtable();

	public Hashtable getHt() {
		return ht;
	}
	public void setHt(Hashtable ht) {
		this.ht = ht;
	}
	/**
	 * write(这里用一句话描述这个方法的作用)
	 * 
	 * @throws IOException
	 *             void
	 * @exception
	 * @since 1.0
	 */
	public void write() {
		try {
			ht.put("test", "您好");
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("f:/a.txt"));
			oos.writeObject(this);
			oos.flush();
			oos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Object read(){
		try {
			ObjectInputStream  ois=new ObjectInputStream(new FileInputStream("f:/a.txt"));
			return ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getA() {
		return a;
	}
	public void setA(String a) {
		this.a = a;
	}
	public String getB() {
		return b;
	}
	public void setB(String b) {
		this.b = b;
	}
	
}