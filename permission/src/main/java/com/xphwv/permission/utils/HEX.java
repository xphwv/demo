package com.xphwv.permission.utils;

/**
 * 16进制字符串
 * 
 * @author wangzt
 * @since 2012-2-27
 * @version 1.1.0
 */
public class HEX {
	/**
	 * @param str
	 * @return
	 * @Description: 16进制字串转为字节数组
	 */
	public static byte[] decode(String str) {
		if (str == null)
			return null;
		str = str.trim();
		int len = str.length();
		if (len == 0 || len % 2 == 1)
			return null;
		byte[] b = new byte[len / 2];
		try {
			for (int i = 0; i < str.length(); i += 2) {
				b[i / 2] = (byte) Integer.decode("0x" + str.substring(i, i + 2)).intValue();
			}
			return b;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @param b
	 * @return
	 * @Description: 字节数组转为 16进制字串
	 */
	public static String encode(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs;
	}

}
