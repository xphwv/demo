package com.xphwv.geolocal.map.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONArray;
import com.xphwv.geolocal.map.entity.BmapPoint;


/**
 * 用于点与多边形位置关系的判断
 * 
 */
public class GraphUtils {
	private static Log log = LogFactory.getLog(GraphUtils.class);

	/**
	 * 将单个行政区的范围转换为对象数组
	 * 
	 * @param singleDistrictMap
	 * @return
	 */
	public static BmapPoint[] convertSingleDistrictToBmapPoint(String singleDistrictMap) {
		if (StringUtils.isEmpty(singleDistrictMap)) {
			throw new IllegalArgumentException();
		}
		String[] points = singleDistrictMap.split(";");
		BmapPoint[] pointArr = new BmapPoint[points.length];
		for (int i = 0; i < points.length; i++) {
			String pointStr = points[i];
			String[] lngAndLatStrings = pointStr.split(",");
			if (StringUtils.isNotEmpty(lngAndLatStrings[0]) && StringUtils.isNotEmpty(lngAndLatStrings[1])) {
				BmapPoint point = new BmapPoint(Double.valueOf(lngAndLatStrings[0]), Double.valueOf(lngAndLatStrings[1]));
				pointArr[i] = point;
			}
		}
		return pointArr;
	}

	/**
	 * 判断点是否在多边形内
	 * 
	 * @param lng
	 * @param lat
	 * @param path
	 * @return
	 */
	public static <T> boolean isPointInPolygon(Double lng, Double lat, String path) {
		if (lng == null || lng == 0L || lat == null || lat == 0L || StringUtils.isEmpty(path)) {
			throw new IllegalArgumentException();
		}
		try {
			List<BmapPoint> list = JSONArray.parseArray(path, BmapPoint.class);
			BmapPoint[] boundaryPoints = (BmapPoint[])list.toArray(new BmapPoint[list.size()]);
			return isPointInPolygon(new BmapPoint(lng, lat), boundaryPoints);
		} catch (Exception e) {
			log.error("wrong format of bizarea path,path=" + path, e);
			return false;
		}
	}

	/**
	 * 判断点是否在多边形内，时间复杂度为O(2n)
	 * 
	 * @param point
	 * @param boundaryPoints
	 *            数组中的点不能重复
	 * @return
	 */
	public static boolean isPointInPolygon(BmapPoint point, BmapPoint[] boundaryPoints) {
		// 防止第一个点与最后一个点相同
		int pointCount = boundaryPoints.length;

		// 如果点与多边形的其中一个顶点重合，那么直接返回true
		double minLng = boundaryPoints[0].getLng(), minLat = boundaryPoints[0].getLat(), maxLng = 0.0d, maxLat = 0.0d;
		for (int i = 0; i < pointCount; i++) {
			BmapPoint bmapPoint = boundaryPoints[i];
			if (point.equals(bmapPoint)) {
				return true;
			}

			double lng = bmapPoint.getLng(), lat = bmapPoint.getLat();
			if (lng < minLng) {
				minLng = lng;
			}
			if (lat < minLat) {
				minLat = lat;
			}
			if (lng > maxLng) {
				maxLng = lng;
			}
			if (lat > maxLat) {
				maxLat = lat;
			}
		}
		// 判断点是否在多边形的外包矩形内，如果在，则进一步判断，否则返回falser
		if (!((point.getLng() >= minLng && point.getLng() <= maxLng && point.getLat() >= minLat && point.getLat() <= maxLat))) {
			return false;
		}

		/**
		 * 基本思想是利用X轴射线法，计算射线与多边形各边的交点，如果是偶数，则点在多边形外，否则在多边形内。还会考虑一些特殊情况，如点在多边形顶点上
		 * ， 点在多边形边上等特殊情况。
		 */
		// X轴射线与多边形的交点数
		int intersectPointCount = 0;
		// X轴射线与多边形的交点权值
		float intersectPointWeights = 0;
		// 浮点类型计算时候与0比较时候的容差
		double precision = 2e-10;
		// 边P1P2的两个端点
		BmapPoint point1 = boundaryPoints[0], point2;
		// 循环判断所有的边
		for (int i = 1; i <= pointCount; i++) {
			point2 = boundaryPoints[i % pointCount];

			/**
			 * 如果点的y坐标在边P1P2的y坐标开区间范围之外，那么不相交。
			 */
			if (point.getLat() < Math.min(point1.getLat(), point2.getLat()) || point.getLat() > Math.max(point1.getLat(), point2.getLat())) {
				point1 = point2;
				continue;
			}

			/**
			 * 此处判断射线与边相交
			 */
			if (point.getLat() > Math.min(point1.getLat(), point2.getLat()) && point.getLat() < Math.max(point1.getLat(), point2.getLat())) {// 如果点的y坐标在边P1P2的y坐标开区间内
				if (point1.getLng() == point2.getLng()) {// 若边P1P2是垂直的
					if (point.getLng() == point1.getLng()) {
						// 若点在垂直的边P1P2上，则点在多边形内
						return true;
					} else if (point.getLng() < point1.getLng()) {
						// 若点在在垂直的边P1P2左边，则点与该边必然有交点
						++intersectPointCount;
					}
				} else {// 若边P1P2是斜线
					if (point.getLng() <= Math.min(point1.getLng(), point2.getLng())) {// 点point的x坐标在点P1和P2的左侧
						++intersectPointCount;
					} else if (point.getLng() > Math.min(point1.getLng(), point2.getLng())
							&& point.getLng() < Math.max(point1.getLng(), point2.getLng())) {// 点point的x坐标在点P1和P2的x坐标中间
						double slopeDiff = 0.0d;
						if (point1.getLat() > point2.getLat()) {
							slopeDiff = (point.getLat() - point2.getLat()) / (point.getLng() - point2.getLng()) - (point1.getLat() - point2.getLat())
									/ (point1.getLng() - point2.getLng());
						} else {
							slopeDiff = (point.getLat() - point1.getLat()) / (point.getLng() - point1.getLng()) - (point2.getLat() - point1.getLat())
									/ (point2.getLng() - point1.getLng());
						}
						if (slopeDiff > 0) {
							if (slopeDiff < precision) {// 由于double精度在计算时会有损失，故匹配一定的容差。经试验，坐标经度可以达到0.0001
								// 点在斜线P1P2上
								return true;
							} else {
								// 点与斜线P1P2有交点
								intersectPointCount++;
							}
						}
					}
				}
			} else {
				// 边P1P2水平
				if (point1.getLat() == point2.getLat()) {
					if (point.getLng() <= Math.max(point1.getLng(), point2.getLng()) && point.getLng() >= Math.min(point1.getLng(), point2.getLng())) {
						// 若点在水平的边P1P2上，则点在多边形内
						return true;
					}
				}
				/**
				 * 判断点通过多边形顶点
				 */
				if (((point.getLat() == point1.getLat() && point.getLng() < point1.getLng()))
						|| (point.getLat() == point2.getLat() && point.getLng() < point2.getLng())) {
					if (point2.getLat() < point1.getLat()) {
						intersectPointWeights += -0.5;
					} else if (point2.getLat() > point1.getLat()) {
						intersectPointWeights += 0.5;
					}
				}
			}
			point1 = point2;
		}

		if ((intersectPointCount + Math.abs(intersectPointWeights)) % 2 == 0) {// 偶数在多边形外
			return false;
		} else { // 奇数在多边形内
			return true;
		}
	}

	/**
	 * 判断点是否在矩形内在矩形边界上，也算在矩形内(根据这些点，构造一个外包矩形)
	 * 
	 * @param point
	 *            点对象
	 * @param boundaryPoints
	 *            矩形边界点
	 * @return
	 */
	public static boolean isPointInRectangle(BmapPoint point, BmapPoint[] boundaryPoints) {
		BmapPoint southWestPoint = getSouthWestPoint(boundaryPoints); // 西南角点
		BmapPoint northEastPoint = getNorthEastPoint(boundaryPoints); // 东北角点
		return (point.getLng() >= southWestPoint.getLng() && point.getLng() <= northEastPoint.getLng() && point.getLat() >= southWestPoint.getLat() && point
				.getLat() <= northEastPoint.getLat());

	}

	/**
	 * 获取商圈的最大最小的经纬度
	 * 
	 * @param path
	 * @return
	 */
	public static Map<String, Double> getRectangleVertex(String path) {
		if (StringUtils.isEmpty(path)) {
			throw new IllegalArgumentException();
		}
		List<BmapPoint> list = JSONArray.parseArray(path, BmapPoint.class);
		BmapPoint[] boundaryPoints = (BmapPoint[])list.toArray(new BmapPoint[list.size()]);
		double minLng = boundaryPoints[0].getLng(), minLat = boundaryPoints[0].getLat(), maxLng = 0.0d, maxLat = 0.0d;
		for (int i = 0; i < boundaryPoints.length; i++) {
			BmapPoint bmapPoint = boundaryPoints[i];

			double lng = bmapPoint.getLng(), lat = bmapPoint.getLat();
			if (lng < minLng) {
				minLng = lng;
			}
			if (lat < minLat) {
				minLat = lat;
			}
			if (lng > maxLng) {
				maxLng = lng;
			}
			if (lat > maxLat) {
				maxLat = lat;
			}
		}
		Map<String, Double> resultMap = new HashMap<String, Double>();
		resultMap.put("maxLng", maxLng);
		resultMap.put("minLng", minLng);
		resultMap.put("maxLat", maxLat);
		resultMap.put("minLat", minLat);
		return resultMap;
	}

	/**
	 * 根据这组坐标，画一个矩形，然后得到这个矩形西南角的顶点坐标
	 * 
	 * @param vertexs
	 * @return
	 */
	private static BmapPoint getSouthWestPoint(BmapPoint[] vertexs) {
		double minLng = vertexs[0].getLng(), minLat = vertexs[0].getLat();
		for (BmapPoint bmapPoint : vertexs) {
			double lng = bmapPoint.getLng();
			double lat = bmapPoint.getLat();
			if (lng < minLng) {
				minLng = lng;
			}
			if (lat < minLat) {
				minLat = lat;
			}
		}
		return new BmapPoint(minLng, minLat);
	}

	/**
	 * 根据这组坐标，画一个矩形，然后得到这个矩形东北角的顶点坐标
	 * 
	 * @param vertexs
	 * @return
	 */
	private static BmapPoint getNorthEastPoint(BmapPoint[] vertexs) {
		double maxLng = 0.0d, maxLat = 0.0d;
		for (BmapPoint bmapPoint : vertexs) {
			double lng = bmapPoint.getLng();
			double lat = bmapPoint.getLat();
			if (lng > maxLng) {
				maxLng = lng;
			}
			if (lat > maxLat) {
				maxLat = lat;
			}
		}
		return new BmapPoint(maxLng, maxLat);
	}
	
	public static void main(String args[]) {
//		JSONArray arr = new JSONArray();
//		JSONObject object = new JSONObject();
//		object.put("lng", "116.31602766799");
//		object.put("lat", "40.077354857376");
//		arr.add(object);
//		
//		JSONObject object1 = new JSONObject();
//		object1.put("lng", "116.33181663123");
//		object1.put("lat", "40.025332669793");
//		arr.add(object1);
//		
//		JSONObject object2 = new JSONObject();
//		object2.put("lng", "116.29457927304");
//		object2.put("lat", "40.018737394147");
//		arr.add(object2);
//		
//		JSONObject object3 = new JSONObject();
//		object3.put("lng", "116.27518033816");
//		object3.put("lat", "40.062671618403");
//		arr.add(object3);
//		System.out.println(JSONObject.toJSONString(arr));
		
		String bizString = "[{\"lng\":\"116.405\",\"lat\":\"39.7037\"},{\"lng\":\"116.406\",\"lat\":\"39.6863\"},{\"lng\":\"116.424\",\"lat\":\"39.6856\"},{\"lng\":\"116.426\",\"lat\":\"39.7015\"}]";
		// 116.393222,39.862263  
		System.out.println(isPointInPolygon(116.425704,39.694541,bizString));
	}

}
