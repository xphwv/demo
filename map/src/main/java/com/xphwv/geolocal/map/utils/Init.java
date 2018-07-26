package com.xphwv.geolocal.map.utils;

public class Init {
	static {
		String path = System.getProperty("user.dir");
		System.setProperty("serviceframe.config.path", path + "/src/main/resources");
	}
}
