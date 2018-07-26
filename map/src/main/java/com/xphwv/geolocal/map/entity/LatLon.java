package com.xphwv.geolocal.map.entity;
public class LatLon {
	static double M_PI = Math.PI;

	// 经纬度转墨卡托
	// 经度(lon)，纬度(lat)
	public static double[] lonLat2Mercator(double lon, double lat) {
		double[] xy = new double[2];
		double x = lon * 20037508.342789 / 180;
		double y = Math.log(Math.tan((90 + lat) * M_PI / 360)) / (M_PI / 180);
		y = y * 20037508.34789 / 180;
		xy[0] = x;
		xy[1] = y;
		return xy;
	}

	// 墨卡托转经纬度
	public static double[] Mercator2lonLat(double mercatorX, double mercatorY) {
		double[] xy = new double[2];
		double x = mercatorX / 20037508.34 * 180;
		double y = mercatorY / 20037508.34 * 180;
		y = 180 / M_PI * (2 * Math.atan(Math.exp(y * M_PI / 180)) - M_PI / 2);
		xy[0] = x;
		xy[1] = y;
		return xy;
	}

	public static void main(String[] args) {
//		经纬度:左下角,右上角：115.430282,39.445587;117.513171,41.066884
//		墨卡托坐标:左下角,右上角：12849780.03,4758298.57;13081648.69,4993877.7
		double[] num, num1;
		num = lonLat2Mercator(115.430282,39.445587);
		for (int i = 0; i < num.length; i++) {
			System.out.println("摩卡坐标："+num[i]);
		}
		num1 = Mercator2lonLat(12849780.03,4758298.57);
		for (int i = 0; i < num1.length; i++) {
			System.out.println("经纬度："+num1[i]);
		}
	}

}
