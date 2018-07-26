package com.xphwv.maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.xphwv.utils.DaoHelper;

public class WBJsonMap{
	static Logger logger = Logger.getLogger("");

	@SuppressWarnings({ "unchecked" })
	public static void main(String[] args) throws Exception {
		List<LocalVO> provinceList = (List<LocalVO>) DaoHelper.getDAO().getListByCustom(LocalVO.class, "*", "depth=1", "id desc");
		String chinaJson = JSON.toJSONString(handleProvince(provinceList));
		System.out.println(chinaJson);
		logger.info(chinaJson);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Map<String, Object>> handleProvince(List<LocalVO> provinceList) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (LocalVO province : provinceList) {
			// Files.write(province.get(""), provinceFile);
			Map provinceMap = new HashMap<String, Object>();
			provinceMap.put("id", province.getId());
			provinceMap.put("name", province.getName());
			provinceMap.put("adCode", province.getAreaCode());
			List<LocalVO> cityList = (List<LocalVO>) DaoHelper.getDAO().getListByCustom(LocalVO.class, "*", "pid=" + province.getId(), "id desc");
			provinceMap.put("city", handleCity(province.getName(), cityList));
			list.add(provinceMap);
		}
		return list;
	}

	@SuppressWarnings({ "unchecked" })
	public static List<Map<String, Object>> handleCity(String provinceName, List<LocalVO> cityList) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (LocalVO city : cityList) {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", city.getId());
			map.put("name", city.getName());
			map.put("adCode", city.getAreaCode());
			List<LocalVO> areaList = (List<LocalVO>) DaoHelper.getDAO().getListByCustom(LocalVO.class, "*", "pid=" + city.getId(), "id desc");
			map.put("area", handleArea(provinceName, city.getName(), areaList));
			list.add(map);
		}
		return list;
	}

	public static List<Map<String, Object>> handleArea(String provinceName, String cityName, List<LocalVO> areaList) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (LocalVO area : areaList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", area.getId());
			map.put("name", area.getName());
			map.put("adCode", area.getAreaCode());
			list.add(map);
		}
		return list;
	}
}