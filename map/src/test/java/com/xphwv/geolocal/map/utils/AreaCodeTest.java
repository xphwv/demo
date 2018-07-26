package com.xphwv.geolocal.map.utils;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class AreaCodeTest {
	private static final Logger logger = Logger.getLogger("");

	static String path = System.getProperty("user.dir") + "/src/test/reousces";
	private static IGeoLocalService geoLocalService = DSFProxyFactory.create(IGeoLocalService.class, "tcp://geolocal/GeoLocalService");

	static {
		System.setProperty("serviceframe.config.path", path);
		DSFInit.init(Path.getCurrentPath() + "/config/dsf.config");
	}

	private static List<GeoLocal> getLocal() throws Exception {
		String sql = "select id,localid,localname,location,level,path from geolocal where level=3 and localid>0 and type=1 and location in('121.582614,38.975148','117.315358,31.86961 ','112.195354,30.350674','131.164856,44.396864','112.884656,36.119484','118.305139,40.146238','90.985271,29.289078 ','107.067616,22.131353','127.403602,46.081889')  order by localid asc";
		return (List<GeoLocal>) DaoHelper.getDAO().getListBySQL(GeoLocal.class, sql);
	}

	public static void main(String[] args) throws Exception {
		List<GeoLocal> ll = getLocal();
		logger.error("总条数：" + ll.size());
		for (GeoLocal g : ll) {
			String[] location = g.getLocation().split(",");
			areaInfo(new Point(Double.valueOf(location[0]), Double.valueOf(location[1])), g);
		}
		System.exit(0);
	}

	@SuppressWarnings("deprecation")
	private static void areaInfo(Point point, GeoLocal g) throws Exception {
		Map<String, String> res = AreaCodeHandle.get(point);
		List<GeoLocal> locate = geoLocalService.locate(MapSource.GAODE, point, LocalDepth.AREA);
		if (res != null && locate != null && !locate.isEmpty()) {
			GeoLocal l = locate.get(0);
			StringBuffer info = new StringBuffer();
			info.append(res.get("province")).append("|").append(res.get("district")).append("|").append(res.get("adcode")).append("||");
			info.append(l.getLocalId()).append("|").append(l.getLocalName()).append("||");
			info.append(g.getLocalId()).append("|").append(g.getLocalName()).append("|").append(g.getId()).append("|").append(g.getPath());
			logger.error(info.toString());
		}else{
			logger.error(g.getId()+"|"+g.getLocalId()+"|"+g.getLocalName()+"|"+g.getPath());
		}
	}

}
