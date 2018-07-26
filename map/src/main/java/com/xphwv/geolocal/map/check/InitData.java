package com.xphwv.geolocal.map.check;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.xphwv.geolocal.map.entity.GeoLocal;
import com.xphwv.geolocal.map.utils.DaoHelper;
import org.apache.log4j.Logger;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class InitData {
	static Logger logger = Logger.getLogger("");
	private final static String rootDirPath = "E:\\china\\百度";

	public static String getString(String path) {
		StringBuffer content = new StringBuffer();
		try {
			List<String> lines = Files.readLines(new File(path), Charsets.UTF_8);
			for (String string : lines) {
				content.append(string);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content.toString();
	}

	@SuppressWarnings("unchecked")
	public static void traverseFolder(String path, int level) {
		level++;
		File file = new File(path);
		if (file.exists()) {
			File[] files = file.listFiles();
			if (files.length == 0) {
				return;
			} else {
				for (File file2 : files) {
					if (file2.isDirectory()) {
						traverseFolder(file2.getAbsolutePath(), level);
					} else {
						String filePath = file2.getAbsolutePath();

						String content = getString(filePath);
						String name = file2.getName().replaceAll(".data", "");
						try {
							GeoLocal geo = new GeoLocal();
							geo.setLevel(level);
							geo.setLocalName(name);
							geo.setLonlat(content);
							geo.setType(2);
							geo.setPath(filePath);

							name = replaceAll(name);
							List<LocalVO> localList = (List<LocalVO>) DaoHelper.getDAO().getListByCustom(LocalVO.class, "*",
									"name='" + name + "' and depth=" + level, "");
							if (localList != null && !localList.isEmpty()) {
								LocalVO local = localList.get(0);
								geo.setLocalId(local.getId());
							}
							logger.error(name + ":" + filePath);
							DaoHelper.getDAO().insert(geo);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	static StringBuffer reg = new StringBuffer();
	static List<String> list = new ArrayList<String>();
	static {
		reg.append("新区|郊区|郊县");
		reg.append("|拉祜族佤族布朗族傣族|佤族|傣族佤族");
		reg.append("|自治区|特别行政区|壮族|维吾尔|回族|自治州|自治县|盟|市市辖区|朝鲜族|彝族|藏族");
		reg.append("|省|市|区|县");

		list.add("云县");
		list.add("华坪县");
	}

	public static String replaceAll(String name) {
		for (String str : list) {
			if (name.equals(str)) {
				return name;
			}
		}
		// 省
		// return name.replaceAll("省|市|区|县|自治区|特别行政区|壮族|维吾尔|回族", "");
		// 市
		return name.replaceAll(reg.toString(), "");
	}

	public static void main(String[] args) {
		traverseFolder(rootDirPath, 0);
		// System.exit(0);
	}
}
