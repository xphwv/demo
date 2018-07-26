package com.xphwv.geolocal.map.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {
	
	protected final static String DEFAULT_ENCODING = "utf-8";


	protected final static int DEFAULT_maxClientCount = 1000;
	protected final static int DEFAULT_warnClientCount = 300;
	
	protected final static String DEFAULT_REQUEST_METHOD = "GET";

	private int maxClientCount = DEFAULT_maxClientCount;
	private int warnClientCount = DEFAULT_warnClientCount;

	private Map<String, String> headers = new HashMap<String, String>();
	private String encoding = DEFAULT_ENCODING;
	private String requestMethod = DEFAULT_REQUEST_METHOD;
	private int connectTimeout = 500;
	private int readTimeout =500;

	public HttpClient() {
	}

	public HttpClient(String encoding) throws Exception {
		this.encoding = encoding;
	}

	public String getContentByGet(String url) throws Exception {
		return getContent(new URL(url));
	}
	
	public String getContentByPost(String url, Map<String, Object> params) throws Exception {
		return getContent4Post(new URL(url), params);
	}

	public String getContent(URL url) throws Exception {
		if (url == null) {
			return "";
		}
		HttpURLConnection urlConn = null;
		String responseContent = null;
		BufferedReader rd = null;
		InputStream in = null;
		try {
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setRequestMethod("GET");
			if (headers != null && headers.size() > 0) {
				for (String key : headers.keySet()) {
					urlConn.setRequestProperty(key, headers.get(key));
				}
			}
 
			int n = 1;
			if (n < warnClientCount) {
				urlConn.setConnectTimeout(connectTimeout);
				urlConn.setReadTimeout(readTimeout);
			} else if (n >= maxClientCount) {
				throw new Exception("too many httpClient,url=" + url.toString());
			} else {
				urlConn.setConnectTimeout(connectTimeout * (maxClientCount - n) / n);
				urlConn.setReadTimeout(readTimeout * (maxClientCount - n) / n);
			}

			in = urlConn.getInputStream();
			rd = new BufferedReader(new InputStreamReader(in, encoding));
			String tempLine = rd.readLine();
			StringBuffer tempStr = new StringBuffer();
			String crlf = System.getProperty("line.separator");
			while (tempLine != null) {
				tempStr.append(tempLine);
				tempStr.append(crlf);
				tempLine = rd.readLine();
			}
			responseContent = tempStr.toString();
			return responseContent;

		} catch (IOException e) {
			throw new Exception("取得Url内容失败:url=" + url.toString(), e);
		} finally {
			try {
				if (rd != null) {
					rd.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (urlConn != null) {
				urlConn.disconnect();
			}
		}
	}
	
	private String getContent4Post(URL url, Map<String, Object> params) throws Exception {
    	
		if (url == null) {
			return "";
		}
		HttpURLConnection urlConn = null;
		BufferedReader rd = null;
		InputStream in = null;
		try {
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setRequestMethod("POST");
			if (headers != null && headers.size() > 0) {
				for (String key : headers.keySet()) {
					urlConn.setRequestProperty(key, headers.get(key));
				}
			}
			
			int n = 1;
			if (n < warnClientCount) {
				urlConn.setConnectTimeout(connectTimeout);
				urlConn.setReadTimeout(readTimeout);
			} else if (n >= maxClientCount) {
				throw new Exception("too many httpClient,url=" + url.toString());
			} else {
				urlConn.setConnectTimeout(connectTimeout * (maxClientCount - n) / n);
				urlConn.setReadTimeout(readTimeout * (maxClientCount - n) / n);
			}
			
			// 组织请求参数
            StringBuilder postBody = new StringBuilder();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() == null) continue;
                postBody.append(entry.getKey())
                	.append("=")
                	.append(URLEncoder.encode(entry.getValue().toString(), "utf-8"))
                	.append("&");
            }

            if (!params.isEmpty()) {
                postBody.deleteCharAt(postBody.length() - 1);
            }
            
            // 设置长链接
            urlConn.setRequestProperty("Connection", "Keep-Alive");
            urlConn.setDoOutput(true);
            urlConn.getOutputStream().write(postBody.toString().getBytes());
            urlConn.getOutputStream().flush();
            int responseCode = urlConn.getResponseCode();
            if (responseCode != 200) {
                System.out.println("[FraudApiInvoker] invoke failed, response status:" + responseCode);
                return null;
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "utf-8"));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line).append("\n");
            }
            return result.toString().trim();
            
		} catch (IOException e) {
			throw new Exception("取得Url内容失败:url=" + url.toString(), e);
		} finally {
			try {
				if (rd != null) {
					rd.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (urlConn != null) {
				urlConn.disconnect();
			}
		}
    }
	

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public int getMaxClientCount() {
		return maxClientCount;
	}

	public int getWarnClientCount() {
		return warnClientCount;
	}

	public void setMaxClientCount(int maxClientCount) {
		this.maxClientCount = maxClientCount;
	}

	public void setWarnClientCount(int warnClientCount) {
		this.warnClientCount = warnClientCount;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
}