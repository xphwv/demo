package com.xphwv.geolocal.map.utils;

import org.apache.log4j.Logger;


public class ApiUtils {
	private static final Logger logger = Logger.getLogger("");

	private ApiUtils() {
	}

	private static PropertiesUtil properties = null;
	static {
		String path = Path.getCurrentPath() + "/config/map.properties";
		try {
			properties = new PropertiesUtil(path);
		} catch (Exception e) {
			logger.error("读取配置文件异常,path=" + path, e);
		}
	}

	public static String getKey(String key) {
		java.util.Random random = new java.util.Random();
		String[] keys = properties.getString(key, "").split(",");
		int randomPos = random.nextInt(keys.length);
		return keys[randomPos];
	}

	public static String getBaiDuUrl() {
		return properties.getString("baidu.url","");
	}

	public static String getBaiDuKey() {
		return getKey("baidu.key");
	}

	public static String getGaoDeUrl() {
		return properties.getString("gaode.url","");
	}

	public static String getGaoDeKey() {
		return getKey("gaode.key");
	}

	public static int getConnectTimeout() {
		return properties.getInt("map.api.timeout.connect", 500);
	}

	public static int getReadTimeout() {
		return properties.getInt("map.api.timeout.read", 500);
	}
}
