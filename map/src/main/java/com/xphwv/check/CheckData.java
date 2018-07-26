package com.xphwv.check;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

@SuppressWarnings("unchecked")
public class CheckData {
	static Logger logger = Logger.getLogger("");
	static String path = System.getProperty("user.dir") + "/src/main/webapp/";
	static Map<String, Object> wuba = null;
	static Map<String, Object> gaode = null;
	static Map<String, Object> baidu = null;

	public static String getString(String path) {
		StringBuffer content = new StringBuffer();
		try {
			List<String> lines = Files.readLines(new File(path), Charsets.UTF_8);
			for (String string : lines) {
				content.append(string);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content.toString().replace("cityData=", "");
	}

	public static void main(String[] args) {
		logger.info("path:" + path);
		String gaodeStr = getString(path + "gaode.min.json");
		logger.info(gaodeStr);
		gaode = JSON.parseObject(gaodeStr, Map.class);
		logger.info("获取高德数据：" + wuba.size());
		String baiduStr = getString(path + "baidu.min.json");
		logger.info(baiduStr);
		baidu = JSON.parseObject(baiduStr, Map.class);
		logger.info("获取百度数据：" + wuba.size());
		String wubaStr = getString(path + "wuba.min.json");
		logger.info(wubaStr);
		wuba = JSON.parseObject(wubaStr, Map.class);
		logger.info("获取五八数据：" + wuba.size());

	}
}
