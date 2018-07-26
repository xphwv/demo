package com.xphwv.geolocal.map.maps;

import com.alibaba.fastjson.JSON;
import com.google.common.io.Files;
import com.xphwv.geolocal.map.entity.GeoLocal;
import com.xphwv.geolocal.map.utils.DaoHelper;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GaoDeMap {
	private final static String rootDirPath = "E:\\china\\高德";
	static Logger logger = Logger.getLogger("");

	private final static String url = "http://restapi.amap.com/v3/config/district?subdistrict=1&level=%s&showbiz=false&extensions=%s&key=caaa086bdf5666322fba3baf5a6a2c03&s=rsv3&output=json&keywords=%s";
	private static HttpClient client = new HttpClient();
	static {
		final File rootDir = new File(rootDirPath);
		if (rootDir.mkdirs()) {
			logger.info("根目录创建成功");
		}
	}
	
	public static void update(String center,String path) throws Exception{
		path = path.replaceAll("\\\\", "\\\\\\\\");
		String condition = "path = '"+path+"'";
		List<GeoLocal> result = (List<GeoLocal>)DaoHelper.getDAO().getListByCustom(GeoLocal.class, null, condition, null);
		if(result == null || result.isEmpty()){
		}
		else{
		GeoLocal geolocal = result.get(0);
		geolocal.setLocation(center);
		DaoHelper.getDAO().upateEntity(geolocal);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) throws Exception {
		// toJson("中国","city","base");
		// toJson("410000","province");
		Map chainMap = getData("city", "base", "%E4%B8%AD%E5%9B%BD");
		List<Map> provinceList = (List<Map>) chainMap.get("districts");
		handleProvince(provinceList);

	}

	private static void writeFile(String filePath, String content) {
		logger.info(filePath);
		File newFile = new File(filePath);
		if (newFile.exists()) {
			if (newFile.length() == 0) {
			}
		} else {
			try {
				newFile.createNewFile();
				Files.write(content.toString().getBytes(), newFile);
			} catch (IOException e) {
				logger.error("操作失败" + filePath);
			}
		}
	}

	private static void writeDir(String dirPath) {
		File provinceDir = new File(dirPath);
		provinceDir.mkdirs();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void handleProvince(List<Map> provinceList) throws Exception {
		for (Map province : provinceList) {
			// Files.write(province.get(""), provinceFile);
			Map provinceMap = getData((String) province.get("level"), null, (String) province.get("adcode"));
			String provinceName = provinceMap.get("name").toString();
			String provinceFile = rootDirPath + "\\" + provinceName + ".data";
			writeFile(provinceFile, provinceMap.get("polyline").toString());
			update(provinceMap.get("center").toString(),provinceFile);

			List<Map> cityList = (List<Map>) provinceMap.get("districts");
			handleCity(provinceName, cityList);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void handleCity(String provinceName, List<Map> cityList) throws Exception {
		String provinceDir = rootDirPath + "\\" + provinceName;
		writeDir(provinceDir);
		for (Map city : cityList) {
			Map cityMap = getData((String) city.get("level"), null, (String) city.get("adcode"));
			String cityName = cityMap.get("name").toString();
			String provinceFile = rootDirPath + "\\" + provinceName + "\\" + cityName + ".data";

			writeFile(provinceFile, cityMap.get("polyline").toString());
			
			update(cityMap.get("center").toString(),provinceFile);

			List<Map> areaList = (List<Map>) cityMap.get("districts");
			handleArea(provinceName, cityName, areaList);
		}
	}

	@SuppressWarnings("rawtypes")
	public static void handleArea(String provinceName, String cityName, List<Map> areaList) throws Exception {
		String cityDir = rootDirPath + "\\" + provinceName + "\\" + cityName;
		writeDir(cityDir);
		for (Map areaMap : areaList) {
			Map areaObj = getData((String) areaMap.get("level"), null, (String) areaMap.get("adcode"));
			String areaName = areaObj.get("name").toString();
			String cityFile = rootDirPath + "\\" + provinceName + "\\" + cityName +"\\"+areaName+ ".data";
			writeFile(cityFile, areaObj.get("polyline").toString());
			update(areaObj.get("center").toString(),cityFile);
		}
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