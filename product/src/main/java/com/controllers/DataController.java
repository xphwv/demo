package com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.service.IDataService;

/**
 * TODO
 * 
 * @author XuPan
 * @date 2014年10月29日 下午6:25:57
 * @version 1.0
 */

@Controller
@RequestMapping(value = "data")
public class DataController {

	@Autowired
	private IDataService dataService;

	@RequestMapping(value = "hour", method = RequestMethod.GET)
	public String test(ModelMap map) {
		dataService.getHourData();
		map.put("aa", "hello word");
		return "data";
	}
}
