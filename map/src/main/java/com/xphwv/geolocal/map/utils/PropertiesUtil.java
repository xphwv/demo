package com.xphwv.geolocal.map.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PropertiesUtil {
	private Properties pro = null;

	public PropertiesUtil(String path) throws Exception {
		this.pro = loadProperty(path);
	}

	public PropertiesUtil(InputStream inputStream) {
		this.pro = new Properties();
		try {
			this.pro.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getString(String key,String defaultValue) {
		try {
			return this.pro.getProperty(key).trim();
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public int getInt(String key, int defaultValue) {
		try {
			return Integer.parseInt(this.pro.getProperty(key).trim());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public double getDouble(String key,double defaultValue) {
		try {
			return Double.parseDouble(this.pro.getProperty(key).trim());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public long getLong(String key,long defaultValue) {
		try {
			return Long.parseLong(this.pro.getProperty(key).trim());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public float getFloat(String key,float defaultValue) {
		try {
			return Float.parseFloat(this.pro.getProperty(key).trim());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public boolean getBoolean(String key,boolean defaultValue) {
		try {
			return Boolean.parseBoolean(this.pro.getProperty(key).trim());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public Set<Object> getAllKey() {
		return this.pro.keySet();
	}

	public Collection<Object> getAllValue() {
		return this.pro.values();
	}

	public Map<String, Object> getAllKeyValue() {
		Map<String, Object> mapAll = new HashMap<String, Object>();
		Set<Object> keys = getAllKey();

		Iterator<Object> it = keys.iterator();
		while (it.hasNext()) {
			String key = it.next().toString();
			mapAll.put(key, this.pro.get(key));
		}
		return mapAll;
	}

	private Properties loadProperty(String filePath) throws Exception {
		FileInputStream fin = null;
		Properties pro = new Properties();
		try {
			fin = new FileInputStream(filePath);
			pro.load(fin);
		} catch (IOException e) {
			throw e;
		} finally {
			if (fin != null) {
				fin.close();
			}
		}
		return pro;
	}
}
