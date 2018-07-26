package com.xphwv.geolocal.map.check;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class ReadWhile {
	public final static Logger logger = Logger.getLogger("");

	public static void main(String[] args) throws InterruptedException, IOException {
		String path = System.getProperty("user.dir") + "/src/main/resources/";
		DSFInit.init(path + "dsf.config");
		List<String> lines = Files.readLines(new File("E:\\geolocal.txt"), Charsets.UTF_8);
		SearchServiceProvider.init("searchcloud");
//		for (int i = 0; i < 6; i++) {
			new ReadWhile.GeoThread(lines).start();
//		}
		Thread.currentThread().join();
	}

	static class GeoThread extends Thread {
		GeoShapeQuery query = null;
		List<String> lines;

		// public GeoThread(double lat, double lon, int result) {
		public GeoThread(List<String> lines) {
			query = new GeoShapeQuery();
			query.setToken("alia_houtai_geolocal");
			query.setTypes(new String[] { "ty_houtai_geolocal" });
			query.setName("lonlat");
			query.setShapeType(GeoShapeEnum.POINT);
			query.setFields(Arrays.asList(new String[] { "id", "localid", "localName", "path", "level", "type" }));
			query.setQuery("+level:2 +type:1");
			this.lines = lines;
		}

		@Override
		public void run() {
			double lat = 0, lon = 0;
			int resultId = 0;
			for (int i = 0; i < 10000000; i++) {
				try {

					Random ran = new Random();

					String[] str = lines.get(ran.nextInt(lines.size())).split(",");
					System.out.println(Arrays.toString(str));
					lat = Double.parseDouble(str[0]);
					lon = Double.parseDouble(str[1]);
					resultId = Integer.parseInt(str[2]);

					List<GeoPoint> pointList = new ArrayList<GeoPoint>();
					pointList.add(new GeoPoint(lat, lon));
					query.setPoints(pointList);

					SearchResult<Map> result = SearchServiceProvider.search(query);
					Integer localId = Integer.parseInt(((Map) result.items(Map.class).get(0)).get("localid").toString());
					if (localId != resultId) {
						logger.error(localId);
					} else {
						System.out.println(localId);
					}
				} catch (Exception e) {
					if (e instanceof IndexOutOfBoundsException) {

					} else {
						logger.error(lon + "," + lat + "," + resultId, e);
					}
				}
			}
		}
	}

}
