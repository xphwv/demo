package com.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * TODO
 * 
 * @author XuPan
 * @date 2015年1月6日 上午10:04:09
 * @version 1.0
 */
@Controller
@RequestMapping("echarts")
public class EChartsController{
	@RequestMapping("test")
	public String index() {
		return "test";
	}

	@RequestMapping("data")
	public @ResponseBody String data() {
		// 服务器台数
		int serverSize = 5;
		List<Object> list=new ArrayList<Object>();
		
		List<Integer> timeList=new ArrayList<Integer>();
		for (int i = 0; i < 24; i++) {
			timeList.add(i);
		}
		
		
		for (int i = 1; i <= serverSize; i++) {
			Map<String, Object> server = new HashMap<String, Object>(2);
			server.put("name", "服务器" + i);
			server.put("time", timeList);

			List<Integer> serverData = new ArrayList<Integer>(24);
			for (int j = 0; j < 24; j++) {
				serverData.add(new Random().nextInt(1000));
			}
			server.put("data", serverData);
			list.add(server);
		}
		return  JSON.toJSONString(list);
	}
}
