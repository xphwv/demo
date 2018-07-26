package com.xphwv.permission.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class CookieValueUtil {
	private static final String ITEM_INTERVAL = "&";
	private static final String KV_INTERVAL = "=";

	
	
	/**
	 * 将类似
	 * "CurrProductID=3&CurrCates=8,10,12,13&CurrCityID=188&PortalIds=129062705&ProductIds=3"
	 * 的字符串， 解析为一个Map对象。如果字符串为空，则返回null。
	 */
	public static Map<String, String> splitCookieKeyValue(String source) {
		if (source != null && !"".equals(source)) {
			String items[] = source.split(ITEM_INTERVAL);
			if (items != null && items.length > 0) {
				HashMap<String, String> result = new HashMap<String, String>(items.length);
				for (String item : items) {
					if (item.indexOf(KV_INTERVAL) > 0) {
						String[] keyValueArray = item.split(KV_INTERVAL);
						if (keyValueArray.length == 1) {
							result.put(keyValueArray[0].toLowerCase(), "");
						} else if (keyValueArray.length == 2) {
							result.put(keyValueArray[0].toLowerCase(), CookieUtil.decode(keyValueArray[1]));
						}
					}
				}
				return result;
			}
		}
		return null;
	}

	public static String joinCookieKeyValue(Map<String, String> kvMap) {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String> item : kvMap.entrySet()) {
			if (sb.length() > 0) {
				sb.append(ITEM_INTERVAL);
			}
			sb.append(item.getKey());
			sb.append(KV_INTERVAL);
			sb.append(CookieUtil.encode(item.getValue()));

		}
		return sb.toString();
	}
}
