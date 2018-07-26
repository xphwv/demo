package com.xphwv.geolocal.map.geolocal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;


public class CheckData {
	static String path = System.getProperty("user.dir") + "/src/main/resources/";
	static {
		DSFInit.init(path + "dsf.config");
	}

	public final static Logger logger = Logger.getLogger("");

	public static void main(String[] args) throws Exception {
		IGeoDispLocalService service = DSFProxyFactory.create(IGeoDispLocalService.class, "tcp://geolocal/GeoDispLocalService");
		IGeoLocalService geolocalService = DSFProxyFactory.create(IGeoLocalService.class, "tcp://geolocal/" + IGeoLocalService.LOOKUP);
		FileReader reader = new FileReader(path + "geolocal.txt");
		BufferedReader br = new BufferedReader(reader);
		String line = null;
		double lat = 0, lon = 0;
		int count = 0;
		int all = 0;
		int re_count = 0;
		int more_results = 0;
		long all_time = 0;
		long average_time = 0;
		while ((line = br.readLine()) != null && all < 200000) {
			try {
				String[] str = line.split(",");
				lat = Double.parseDouble(str[0]);
				lon = Double.parseDouble(str[1]);
				List<GeoDispLocal>  result= service.locate(2, new Point(116.30621, 39.976121));
				List<GeoDispLocal> list = new ArrayList<GeoDispLocal>();
				long pre_time = System.currentTimeMillis();
				List<GeoLocal> list2 = geolocalService.locate(MapSource.GAODE, new Point(116.30621, 39.976121), LocalDepth.CITY);
				long last_time = System.currentTimeMillis();
				String id_str = ",\t";
				for (GeoDispLocal geolocal : result) {
					if (geolocal.getDepth() == 0) {
						list.add(geolocal);
						id_str += geolocal.getLocalId() + " ";
					}
				}
				id_str += ",\t";
				for (GeoLocal geolocal : list2) {
					id_str += geolocal.getLocalId() + " ";
				}

				// if (compare(list, list2)) {
				all_time += last_time - pre_time;
				average_time = all_time / (all + 1);
				logger.info(list.get(0).getLocalId() + "\t执行时间：" + (last_time - pre_time) + "ms" + ",平均时间：" + average_time + "ms");
				TimeUnit.MILLISECONDS.sleep(10);
				all++;
			} catch (Exception e) {
			}
		}
		System.out.println(all);
		System.out.println(count);
		logger.info("all:" + all + ",count:" + count + ",recount:" + re_count + ",more_results:" + more_results);
		System.out.println("END!!!");
	}

	private static boolean compare(List<GeoDispLocal> list, List<GeoLocal> list2) {
		int i = 0;
		List<Integer> localId2 = new ArrayList<Integer>();
		for (GeoLocal geo : list2) {
			if (geo.getPath().contains("百强县"))
				i++;
			else {
				localId2.add(geo.getLocalId());
			}
		}
		if (i == 1) {
			return true;
		}
		List<Integer> localId = new ArrayList<Integer>();
		for (GeoDispLocal geo : list) {
			localId.add(geo.getLocalId());
		}
		if (localId.equals(localId2)) {
			return true;
		}
		return false;
	}
}
