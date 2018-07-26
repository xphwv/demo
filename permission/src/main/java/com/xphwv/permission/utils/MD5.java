package com.xphwv.permission.utils;

import java.security.MessageDigest;

/**
 * MD5加密
 * 
 * @author wangzt
 * @since 2012-2-27
 * @version 1.1.0
 */
public class MD5 {
	private final static String KEY_MD5 = "MD5";

	/**
	 * MD5加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encode(byte[] data) throws Exception {

		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);
		return md5.digest();

	}

}
