package com.xphwv.geolocal.map.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class AreaCodeHandle {

	private static final Logger logger = Logger.getLogger("");
	private static HttpClient client = null;
	static {
		client = new HttpClient();
		client.setConnectTimeout(ApiUtils.getConnectTimeout());
		client.setReadTimeout(ApiUtils.getReadTimeout());
	}

	@SuppressWarnings("rawtypes")
	public static Map<String, String> get(Point point) {
		String url = String.format(ApiUtils.getGaoDeUrl(), ApiUtils.getGaoDeKey(), point.getLongitude(), point.getLatitude());
		logger.debug(url);
		String json = getJson(url);
		if (StringUtils.isBlank(json)) {
			return null;
		}
		Map map = JSONUtils.parseObject(json, Map.class);
		if (map == null) {
			return null;
		}
		if (map != null && map.containsKey("status") && "1".equals(map.get("status").toString())) {
			Map<String, String> result = new HashMap<String, String>();
			Map regeocode = (Map) map.get("regeocode");
			if(regeocode!=null&&regeocode.containsKey("addressComponent")){
				Map addressComponent=(Map) regeocode.get("addressComponent");
				result.put("province", addressComponent.get("province").toString());
				result.put("district", addressComponent.get("district").toString());
				result.put("adcode", addressComponent.get("adcode").toString());
				return result;
			}
		}
		logger.error("高德抓取数据异常,url=" + url + ",info=" + (map.containsKey("info") ? map.get("info") : url));
		return null;
	}

	public static String getJson(String url) {
		try {
			return client.getContentByGet(url);
		} catch (Exception e) {
			logger.error("抓取异常，url=" + url, e);
			return null;
		}
	}


}