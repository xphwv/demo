package com.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.IDataDao;
import com.service.IDataService;

/**
 * TODO
 * 
 * @author XuPan
 * @date 2015年1月7日 下午5:23:04
 * @version 1.0
 */
@Service
public class DataServiceImpl implements IDataService {
	@Autowired
	private IDataDao dataDao;
	public List<Map<String, Object>> getHourData() {
		return dataDao.getHourData();
	}
}
