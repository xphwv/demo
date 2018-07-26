package com.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * TODO
 * 
 * @author XuPan
 * @date 2014年10月29日 下午6:25:57
 * @version 1.0
 */

@Controller
@RequestMapping(value = "test")
public class TestController {
	private static final Logger loger = Logger.getLogger(TestController.class);

	@RequestMapping(value = "{id}")
	public String test(ModelMap map,@PathVariable(value = "id") String id) {
		loger.debug(id);
		map.put("aa", "hello word");
		return "/test1";
	}
}
