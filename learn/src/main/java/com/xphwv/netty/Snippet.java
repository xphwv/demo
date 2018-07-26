package com.xphwv.netty;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * TODO
 * 
 * @author XuPan
 * @date 2015年12月4日 下午5:04:13
 * @version 1.0
 */

public class Snippet {
	static String httpUrl = "http://apis.baidu.com/apistore/mobilephoneservice/mobilephone";
	static String httpArg = "tel=15846530170";

	public static void main(String[] args) {
		System.out.println(request(httpUrl, httpArg));
	}

	/**
	 * @param urlAll :请求接口
	 * @param httpArg :参数
	 * @return 返回结果
	 */
	public static String request(String httpUrl, String httpArg) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" + httpArg;

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey", "ea46070a7d18249a3c4a39ddad54752c");
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "GBK"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			System.out.println(sbf.toString());
			result = convert(sbf.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public static String convert(String utfString){  
	    StringBuilder sb = new StringBuilder();  
	    int i = -1;  
	    int pos = 0;  
	    while((i=utfString.indexOf("\\u", pos)) != -1){  
	        sb.append(utfString.substring(pos, i));  
	        if(i+5 < utfString.length()){  
	            pos = i+6;  
	            sb.append((char)Integer.parseInt(utfString.substring(i+2, i+6), 16));  
	        }  
	    }  
	    sb.append(utfString.substring(pos, utfString.length()));
	    return sb.toString();  
	}  
}
