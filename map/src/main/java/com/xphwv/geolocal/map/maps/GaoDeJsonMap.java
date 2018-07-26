package com.xphwv.geolocal.map.maps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

public class GaoDeJsonMap {
	static Logger logger = Logger.getLogger("");

	private final static String url = "http://restapi.amap.com/v3/config/district?subdistrict=1&level=%s&showbiz=false&extensions=%s&key=caaa086bdf5666322fba3baf5a6a2c03&s=rsv3&output=json&keywords=%s";
	private static HttpClient client = new HttpClient();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		Map chinaMap = getData("city", "base", "%E4%B8%AD%E5%9B%BD");
		List<Map> provinceList = (List<Map>) chinaMap.get("districts");
		String chinaJson = JSON.toJSONString(handleProvince(provinceList));
		System.out.println(chinaMap);
		logger.info(chinaJson);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Map<String, Object>> handleProvince(List<Map> provinceList) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Map province : provinceList) {
			// Files.write(province.get(""), provinceFile);
			Map provinceMap = getData((String) province.get("level"), null, (String) province.get("adcode"));
			String name = provinceMap.get("name").toString();

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", name);
			map.put("adCode", (String) provinceMap.get("adcode"));

			List<Map> cityList = (List<Map>) provinceMap.get("districts");
			map.put("city", handleCity(name, cityList));
			list.add(map);
			System.out.println(JSON.toJSONString(list));
		}
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Map<String, Object>> handleCity(String provinceName, List<Map> cityList) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Map city : cityList) {
			Map cityMap = getData((String) city.get("level"), null, (String) city.get("adcode"));
			String name = cityMap.get("name").toString();

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", name);
			map.put("adCode", (String) cityMap.get("adcode"));
			List<Map> areaList = (List<Map>) cityMap.get("districts");
			map.put("area", handleArea(provinceName, name, areaList));
			list.add(map);
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	public static List<Map<String, Object>> handleArea(String provinceName, String cityName, List<Map> areaList) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Map areaMap : areaList) {
			Map areaObj = getData((String) areaMap.get("level"), null, (String) areaMap.get("adcode"));
			String name = areaObj.get("name").toString();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", name);
			map.put("adCode", (String) areaMap.get("adcode"));
			list.add(map);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes" })
	private static Map getData(String level, String type, String keyword) {
		String tempUrl = String.format(url, level, type == null ? "all" : type, keyword);
		HttpMethod method = new GetMethod(tempUrl);
		logger.debug(tempUrl);
		String baseJson = null;
		try {
			client.executeMethod(method);
			baseJson = method.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map base = JSON.parseObject(baseJson, Map.class);
		if (base != null && "OK".equals(base.get("info"))) {
			List list = (List) base.get("districts");
			Map baseMap = (Map) list.get(0);
			return baseMap;
		}
		method.releaseConnection();
		return null;
	}

}