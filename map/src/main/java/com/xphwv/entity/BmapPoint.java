package com.xphwv.entity;

/**
 * 用于构造百度地图中的经纬度点
 * 
 */
public class BmapPoint {
	private double lng;// 经度
	private double lat;// 纬度

	public BmapPoint() {

	}

	public BmapPoint(double lng, double lat) {
		this.lng = lng;
		this.lat = lat;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BmapPoint) {
			BmapPoint bmapPoint = (BmapPoint) obj;
			return (bmapPoint.getLng() == lng && bmapPoint.getLat() == lat) ? true : false;
		} else {
			return false;
		}
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}
}
