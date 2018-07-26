package com.xphwv.utils;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;

import com.xphwv.entity.HttpResponseInfo;

/**
 * https 请求处理工具类，默认信任来自任何网站的数字证书
 * 
 * @author 58
 * 
 */
public class HttpsClient {
	private static final Logger logger = Logger.getLogger(HttpsClient.class);

	protected final static int DEFAULT_CONNECT_TIMEOUT = 3000;
	protected final static int DEFAULT_READ_TIMEOUT = 3000;

	protected final static int DEFAULT_maxClientCount = 1000;
	protected final static int DEFAULT_warnClientCount = 300;

	protected static ConcurrentCounter counter = new ConcurrentCounter();

	private int maxClientCount = DEFAULT_maxClientCount;
	private int warnClientCount = DEFAULT_warnClientCount;

	private Map<String, Object> headers = new HashMap<String, Object>();
	private int connectTimeout = DEFAULT_CONNECT_TIMEOUT;
	private int readTimeout = DEFAULT_READ_TIMEOUT;
	public HttpResponseInfo httpRequest(String requestUrl,Method method,String param, FileWriter fileWriter) {
		long t1 = System.currentTimeMillis();
		try {
				return doHttpRequest(requestUrl, method, param, fileWriter);
		} catch (Exception e) {
			try {
				return doHttpRequest(requestUrl, method, param, fileWriter);
			} catch (Exception e1) {
				System.out.println("HttpsUtil:requestUrl=" + requestUrl
						+ ",method=" + method + "execute Time: "
						+ (System.currentTimeMillis() - t1));
				e1.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 提交时的请求类型
	 * 
	 * @author 58
	 * 
	 */
	public enum Method {

		GET("GET"), POST("POST");

		private String method;

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		Method(String method) {
			this.method = method;
		}
	}

	private HttpResponseInfo doHttpRequest(String requestUrl,Method method,String params, FileWriter fileWriter)throws Exception {
		if (requestUrl == null) {
			HttpResponseInfo httpResponseInfo = new HttpResponseInfo();
			httpResponseInfo.setResponseContent("");
			httpResponseInfo.setStatus(404);
			return httpResponseInfo;
		}
		HttpsURLConnection httpUrlConn = null;
		String responseContent = "";
		InputStreamReader inputStreamReader = null;
		BufferedReader rd = null;
		InputStream in = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化,默认信任所有的网站
			TrustManager[] tm = { new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			} };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			counter.inc();
			httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setHostnameVerifier(new HostnameVerifier() {

				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
			httpUrlConn.setRequestProperty("User-agent","Mozilla/4.0");
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			httpUrlConn.setRequestMethod(method.getMethod());
			httpUrlConn.setRequestProperty("Connection", "Keep-Alive");
			httpUrlConn.setRequestProperty("Content-Type", "application/json");
			httpUrlConn.setRequestProperty("Accept", "text/html");
			if (headers != null && headers.size() > 0) {
				for (String key : headers.keySet()) {
					httpUrlConn.setRequestProperty(key, headers.get(key).toString());
				}
			}
			if(method.getMethod().equals("post")){
				String postBody = params;
				httpUrlConn.getOutputStream().write(postBody.toString().getBytes());
				httpUrlConn.getOutputStream().flush();
			}
			
			
			int n = counter.getCount();
			if (n < warnClientCount) {
				httpUrlConn.setConnectTimeout(connectTimeout);
				fileWriter.write("set request connecttimeout:"+connectTimeout+"\n");
				httpUrlConn.setReadTimeout(readTimeout);
				fileWriter.write("set request readTimeout:"+readTimeout+"\n");
			} else if (n >= maxClientCount) {
				return null;
			} else {
				httpUrlConn.setConnectTimeout(connectTimeout * (maxClientCount - n) / n);
				fileWriter.write("set request connecttimeout:"+connectTimeout * (maxClientCount - n) / n+"\n");
				httpUrlConn.setReadTimeout(readTimeout * (maxClientCount - n) / n);
				fileWriter.write("set request connecttimeout:"+readTimeout * (maxClientCount - n) / n+"\n");
			}
			HttpResponseInfo httpResponseInfo = new HttpResponseInfo();
			httpResponseInfo.setStatus(httpUrlConn.getResponseCode());
			try {
				httpUrlConn.connect();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpUrlConn.getInputStream(),"utf-8"));
				String tempLine = bufferedReader.readLine();
				StringBuffer tempStr = new StringBuffer();
				String crlf = System.getProperty("line.separator");
				while (tempLine != null) {
					tempStr.append(tempLine);
					tempStr.append(crlf);
					tempLine = bufferedReader.readLine();
				}
				responseContent = tempStr.toString();
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("get content failed",e);
			}

			httpResponseInfo.setResponseContent(responseContent);
			return httpResponseInfo;
		} catch (Exception e) {
			throw new Exception("取得Url内容失败:url=" + requestUrl, e);
		} finally {
			counter.dec();
			try {
				if (rd != null) {
					rd.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				logger.info("https client rd close failed", e);
			}
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				logger.info("https client in close failed", e);
			}

			if (inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("https client bufferedReader close failed", e);
				}
			}
			// 释放资源
			if (httpUrlConn != null) {
				try {
					httpUrlConn.disconnect();
				} catch (Exception e) {
					System.err.println("https client httpUrlConn close failed");
					e.printStackTrace();
				}
			}
		}
	}

	public int getMaxClientCount() {
		return maxClientCount;
	}

	public void setMaxClientCount(int maxClientCount) {
		this.maxClientCount = maxClientCount;
	}

	public int getWarnClientCount() {
		return warnClientCount;
	}

	public void setWarnClientCount(int warnClientCount) {
		this.warnClientCount = warnClientCount;
	}

	public Map<String, Object> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, Object> headers) {
		this.headers = headers;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
	

}