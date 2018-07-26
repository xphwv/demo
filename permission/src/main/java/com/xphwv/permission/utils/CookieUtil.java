package com.xphwv.permission.utils;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	private static final String CHARSET = "utf-8";
	private static final int REMOVE_MAXAGE = 0;
	/**
	 * 浏览器生命周期
	 */
	public static final int EXPLORE_EXPRIY = -1;

	/**
	 * 缓存30天
	 */
	public static final int DAY30_EXPRIY = 30 * 24 * 3600;

	/**
	 * 取得COOKIE
	 * 
	 * @param 
	 * @param cookieName
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, String cookieName) throws Exception {
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals(cookieName)) {
					try {
						return java.net.URLDecoder.decode(cookie.getValue(), CHARSET);
					} catch (Exception e) {
						return null;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 写COOKIE
	 * 
	 * @param 
	 * @param cookieName
	 * @param cookieValue
	 * @param expriy
	 *            过期时间 单位为秒 ，如为0则指浏览器生命周期
	 */
	public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue, int expriy) throws Exception {
		setCookie(response, cookieName, cookieValue, "", expriy);
	}

	/**
	 * @param 
	 * @param cookieName
	 * @param cookieValue
	 * @param domain
	 * @param expriy
	 *            过期时间 单位为秒 ，如为0则指浏览器生命周期
	 */
	public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue, String domain, int expriy) throws Exception {
		Cookie cookie = null;

		cookie = new Cookie(cookieName, java.net.URLEncoder.encode(cookieValue, CHARSET));
		if (domain != null && !"".equals(domain)) {
			cookie.setDomain(domain);
		}
		cookie.setPath("/");
		if (expriy != EXPLORE_EXPRIY)
			cookie.setMaxAge(expriy);
		response.addCookie(cookie);

	}

	/**
	 * 删除COOKIE
	 * 
	 * @param 
	 * @param cookieName
	 */
	public static void removeCookie(HttpServletResponse response, String cookieName) throws Exception {
		removeCookie(response, cookieName, "");

	}

	/**
	 * 删除COOKIE
	 * 
	 * @param 
	 * @param cookieName
	 */
	public static void removeCookie(HttpServletResponse response, String cookieName, String domain) throws Exception {
		Cookie cookie = new Cookie(cookieName, null);
		if (domain != null && !"".equals(domain)) {
			cookie.setDomain(domain);
		}
		cookie.setPath("/");
		cookie.setMaxAge(REMOVE_MAXAGE);
		response.addCookie(cookie);

	}

	public static String encode(String val) {
		try {
			return java.net.URLEncoder.encode(val, CHARSET);
		} catch (UnsupportedEncodingException e) {
			return val;
		}
	}

	/**
	 * URL反编码
	 * 
	 * @param url
	 * @return
	 */
	public static String decode(String val) {
		try {
			return java.net.URLDecoder.decode(val, CHARSET);
		} catch (UnsupportedEncodingException e) {
			return val;
		}
	}
}
