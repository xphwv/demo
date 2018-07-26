package com.xphwv.geolocal.map.geolocal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import com.xphwv.geolocal.map.entity.GeoLocal;
import com.xphwv.geolocal.map.utils.DaoHelper;
import org.apache.log4j.Logger;

import com.xphwv.geolocal.map.utils.Init;

public class MultiUpdatGeolocalData extends Init {
	static Logger logger = Logger.getLogger("");

	static AtomicInteger count = new AtomicInteger(0);

	static {
		String dirPath = System.getProperty("user.dir");
		DSFInit.init(dirPath + "/src/main/resources/dsf.config");
		SearchServiceProvider.init("searchcloud");
	}

	static class Storage {
		BlockingQueue<GeoLocal> queues = new LinkedBlockingQueue<GeoLocal>(24);

		public void push(GeoLocal p) {
			try {
				queues.put(p);
			} catch (InterruptedException e) {
				return;
			}
		}

		public GeoLocal pop() {
			try {
				return queues.take();
			} catch (InterruptedException e) {
				return null;
			}
		}
	}

	static class Producer implements Runnable {
		private Storage s = null;

		public Producer(Storage s) {
			this.s = s;
		}

		final int pageSize = 1;

		@SuppressWarnings("unchecked")
		@Override
		public void run() {
			try {
				String condition = " localid >0 and lonlat != ''";
				int count = DaoHelper.getDAO().getCount(GeoLocal.class, condition);
				logger.info("数据总量：" + count);
				List<GeoLocal> list = null;
				int pageIndex = 1;
				do {
					list = (List<GeoLocal>) DaoHelper.getDAO().getListByPage(GeoLocal.class, condition, "*", pageIndex, pageSize, "localId asc");
					if (list != null) {
						for (GeoLocal geo : list) {
							s.push(geo);
						}
					}
					pageIndex++;
				} while (list != null && list.size() == pageSize);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	static class Consumer implements Runnable {

		private Storage s;

		public Consumer(Storage s) {
			this.s = s;
		}

		@Override
		public void run() {
			while (true) {
				GeoLocal geolocal = s.pop();
				if (geolocal != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String addtime = sdf.format(new Date());
					String lonlat_geo = geolocal.getLonlat();
					List<String> lonlat = new ArrayList<String>();
					if (lonlat_geo == null || lonlat_geo.length() == 0) {
						logger.error("坐标为空：" + geolocal.getId());
					} else {
						if (lonlat_geo.indexOf("|") > 0) {
							String[] lonlat_add = lonlat_geo.split("\\|");
							for (int x = 0; x < lonlat_add.length; x++) {
								lonlat.add(x, "[" + lonlat_add[x].replaceAll(";", "],[") + "]");
							}
						} else {
							lonlat.add(0, "[" + lonlat_geo.replaceAll(";", "],[") + "]");
						}
						StringBuffer source = new StringBuffer();
						source.append("{");
						source.append("\"id\":").append(geolocal.getId());
						source.append(",\"localid\":").append(geolocal.getLocalId());
						source.append(",\"localName\":").append("\"" + geolocal.getLocalName() + "\"");
						source.append(",\"location\":").append("\"" + geolocal.getLocation() + "\"");
						source.append(",\"path\":").append("\"" + geolocal.getPath().replace("\\", "/") + "\"");
						source.append(",\"level\":").append(geolocal.getLevel());
						source.append(",\"type\":").append(geolocal.getType());
						source.append(",\"addtime\":").append("\"" + addtime + "\"");
						source.append(",\"lonlat\":").append("{\"type\":\"multipolygon\",\"coordinates\":[");
						for (int y = 0; y < lonlat.size(); y++) {
							source.append("[[" + lonlat.get(y) + "]]");
							if (y != lonlat.size() - 1)
								source.append(",");
						}
						source.append("]}");
						source.append("}");
						try {

							long pre_time = System.currentTimeMillis();
							SearchServiceProvider.upsert("alia_houtai_geolocal", "ty_houtai_geolocal", geolocal.getId() + "", geolocal.getLevel() + "", source.toString());
							long last_time = System.currentTimeMillis();
							count.incrementAndGet();
							long timer = last_time - pre_time;
							logger.info("执行数：" + count + ", " + geolocal.toString() + "执行时间：" + timer + "ms");
						} catch (Throwable e) {
							logger.error("错误数据：" + geolocal.getId() + e);
						}
					}
				}
			}
		}

	}

	public static void main(String[] args) throws Throwable {
		final int POOLSIZE = 3;
		Storage storage = new Storage();
		ExecutorService executor = Executors.newFixedThreadPool(POOLSIZE);
		Runnable producer = new Producer(storage);
		new Thread(producer).start();
		Runnable consumer = new Consumer(storage);
		for (int i = 0; i < POOLSIZE; i++) {
			executor.execute(consumer);
		}
	}

}
