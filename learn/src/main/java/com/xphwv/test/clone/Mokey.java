/**  
 */
package com.xphwv.test.clone;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * @Title: Mokey.java 
 * @Description: TODO  
 * @author xupan  
 * @Modified xupan      
 * @date 2013-10-25 上午11:29:36   
 * @version V1.0     
*/
public class Mokey implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;
	private int height;
	private int weight;
	private Date birthDate;
	private GoldRingdeStaff goldRingdeStaff;

	public Mokey() {
		birthDate = new Date();
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 *
	 */
	public Object clone() {
		Mokey mokey = null;
		try {
			mokey = (Mokey) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return mokey;
	}

	public Object deepClone() {
		ObjectInputStream oi;
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(this);

			ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
			oi = new ObjectInputStream(bi);
			return (oi.readObject());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public GoldRingdeStaff getGoldRingdeStaff() {
		return goldRingdeStaff;
	}

	public void setGoldRingdeStaff(GoldRingdeStaff goldRingdeStaff) {
		this.goldRingdeStaff = goldRingdeStaff;
	}
}

class GoldRingdeStaff implements Serializable {
	private static final long serialVersionUID = 1L;
	private float height = 100.0f;
	private float weight = 10.0f;

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}
}