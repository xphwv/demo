package com.xphwv.geolocal.map.utils;


public class DaoHelper{

	private static DAOOperator dao = null;

	private DaoHelper() {
	}

	static {
		String dbConfig = System.getProperty("user.dir") + "/src/main/resources/swap.config";
		try {
			dao = new DAOOperator(dbConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DAOOperator getDAO() {
		return dao;
	}

}
