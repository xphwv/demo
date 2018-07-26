package com.xphwv.utils;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.xphwv.entity.HttpResponseInfo;


public class HttpClient {

	private static final Logger logger = Logger.getLogger(HttpClient.class);	
	protected final static String DEFAULT_ENCODING = "utf-8";
	protected final static int DEFAULT_CONNECT_TIMEOUT = 3000;
	protected final static int DEFAULT_READ_TIMEOUT = 3000;

	protected final static int DEFAULT_maxClientCount = 1000;
	protected final static int DEFAULT_warnClientCount = 300;
	
	protected final static String DEFAULT_REQUEST_METHOD = "GET";

	protected static ConcurrentCounter counter = new ConcurrentCounter();

	private int maxClientCount = DEFAULT_maxClientCount;
	private int warnClientCount = DEFAULT_warnClientCount;

	private Map<String, Object> headers = new HashMap<String, Object>();
	private String encoding = DEFAULT_ENCODING;
	private String requestMethod = DEFAULT_REQUEST_METHOD;
	private int connectTimeout = DEFAULT_CONNECT_TIMEOUT;
	private int readTimeout = DEFAULT_READ_TIMEOUT;

	public HttpClient() {
		this.encoding = "UTF-8";
	}

	public HttpClient(String encoding) throws Exception {
		this.encoding = encoding;
	}

	public HttpResponseInfo testByGet(String url, FileWriter fileWriter) throws Exception {
		return getContent(new URL(url), fileWriter);
	}
	
	public HttpResponseInfo testByPost(String url, Map<String, Object> params, FileWriter fileWriter) throws Exception {
		return getContent4Post(new URL(url), params, fileWriter);
	}

	public HttpResponseInfo getContent(URL url, FileWriter fileWriter) throws Exception {
		if (url == null) {
			HttpResponseInfo httpResponseInfo = new HttpResponseInfo();
			httpResponseInfo.setResponseContent("");
			httpResponseInfo.setStatus(404);
			return httpResponseInfo;
		}
		HttpURLConnection urlConn = null;
		String responseContent = "";
		BufferedReader rd = null;
		InputStream in = null;
		try {
			counter.inc();
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setRequestMethod("GET");
			if (headers != null && headers.size() > 0) {
				for (String key : headers.keySet()) {
					urlConn.setRequestProperty(key, headers.get(key).toString());
				}
			}
 
			int n = counter.getCount();
			if (n < warnClientCount) {
				urlConn.setConnectTimeout(connectTimeout);
				fileWriter.write("set request connecttimeout:"+connectTimeout+"\n");
				urlConn.setReadTimeout(readTimeout);
				fileWriter.write("set request readTimeout:"+readTimeout+"\n");
			} else if (n >= maxClientCount) {
				return null;
			} else {
				urlConn.setConnectTimeout(connectTimeout * (maxClientCount - n) / n);
				fileWriter.write("set request connecttimeout:"+connectTimeout * (maxClientCount - n) / n+"\n");
				urlConn.setReadTimeout(readTimeout * (maxClientCount - n) / n);
				fileWriter.write("set request readTimeout:"+readTimeout * (maxClientCount - n) / n+"\n");
			}

			HttpResponseInfo httpResponseInfo = new HttpResponseInfo();
			httpResponseInfo.setStatus(urlConn.getResponseCode());
			try {
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
			} catch (Exception e) {
				logger.info("get inputstream failed");
			}
			httpResponseInfo.setResponseContent(responseContent);
			return httpResponseInfo;
		} catch (IOException e) {
			throw new Exception("取得Url内容失败:url=" + url.toString(), e);
		} finally {
			counter.dec();
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
	
	private HttpResponseInfo getContent4Post(URL url, Map<String, Object> params, FileWriter fileWriter) throws Exception {
    	
		if (url == null) {
			HttpResponseInfo httpResponseInfo = new HttpResponseInfo();
			httpResponseInfo.setResponseContent("");
			httpResponseInfo.setStatus(404);
			return httpResponseInfo;
		}
		HttpURLConnection urlConn = null;
		BufferedReader rd = null;
		InputStream in = null;
		try {
			counter.inc();
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setRequestMethod("POST");
			if (headers != null && headers.size() > 0) {
				for (String key : headers.keySet()) {
					urlConn.setRequestProperty(key, headers.get(key).toString());
				}
			}
			
			int n = counter.getCount();
			if (n < warnClientCount) {
				urlConn.setConnectTimeout(connectTimeout);
				fileWriter.write("set request connecttimeout:"+connectTimeout+"\n");
				urlConn.setReadTimeout(readTimeout);
				fileWriter.write("set request readTimeout:"+readTimeout+"\n");
			} else if (n >= maxClientCount) {
				return null;
			} else {
				urlConn.setConnectTimeout(connectTimeout * (maxClientCount - n) / n);
				fileWriter.write("set request connecttimeout:"+connectTimeout * (maxClientCount - n) / n+"\n");
				urlConn.setReadTimeout(readTimeout * (maxClientCount - n) / n);
				fileWriter.write("set request connecttimeout:"+readTimeout * (maxClientCount - n) / n+"\n");
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
            HttpResponseInfo httpResponseInfo = new HttpResponseInfo();
            httpResponseInfo.setStatus(urlConn.getResponseCode());
            StringBuilder result = new StringBuilder();
            try {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "utf-8"));
				String line;
				while ((line = bufferedReader.readLine()) != null) {
				    result.append(line).append("\n");
				}
			} catch (Exception e) {
				logger.info("get content failed");
			}
            httpResponseInfo.setResponseContent(result.toString());
			return httpResponseInfo;
            
		} catch (IOException e) {
			throw new Exception("取得Url内容失败:url=" + url.toString(), e);
		} finally {
			counter.dec();
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
	
	 public static HttpResponseInfo formUpload(String urlStr, Map<String, String> textMap, Map<String, String> fileMap) {  
			if (urlStr == null) {
				HttpResponseInfo httpResponseInfo = new HttpResponseInfo();
				httpResponseInfo.setResponseContent("");
				httpResponseInfo.setStatus(404);
				return httpResponseInfo;
			}
	        String res = "";  
	        HttpURLConnection conn = null;  
	        String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符    
	        try {  
	            URL url = new URL(urlStr);  
	            conn = (HttpURLConnection) url.openConnection();  
	            conn.setConnectTimeout(5000);  
	            conn.setReadTimeout(30000);  
	            conn.setDoOutput(true);  
	            conn.setDoInput(true);  
	            conn.setUseCaches(false);  
	            conn.setRequestMethod("POST");  
	            conn.setRequestProperty("Connection", "Keep-Alive");  
	            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");  
	            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);  
	            StringBuffer sb = new StringBuffer();
	            if (fileMap != null&&fileMap.size()>0) {  
	                Iterator<Map.Entry<String, String>> iter = fileMap.entrySet().iterator();  
	                while (iter.hasNext()) {  
	                    Map.Entry<String, String> entry = iter.next();  
	                    String inputValue = (String) entry.getValue();  
	                    if (inputValue == null) {  
	                        continue;  
	                    }  
	                    sb.append(entry.getKey()).append(",");
	                }
	                textMap.put("mailAttachment",sb.toString().substring(0,sb.toString().length()-1));
	            }
	            OutputStream out = new DataOutputStream(conn.getOutputStream());  
	            // text    
	            if (textMap != null) {  
	                StringBuffer strBuf = new StringBuffer();  
	                Iterator<Map.Entry<String, String>> iter = textMap.entrySet().iterator();  
	                while (iter.hasNext()) {  
	                    Map.Entry<String, String> entry = iter.next();  
	                    String inputName = (String) entry.getKey();  
	                    String inputValue = (String) entry.getValue();  
	                    if (inputValue == null) {  
	                        continue;  
	                    }  
	                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");  
	                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");  
	                    strBuf.append(inputValue);  
	                }  
	                out.write(strBuf.toString().getBytes());  
	            }  
	  
	            // file    
	            if (fileMap != null) {  
	                Iterator<Map.Entry<String, String>> iter = fileMap.entrySet().iterator();  
	                while (iter.hasNext()) {  
	                    Map.Entry<String, String> entry = iter.next();  
	                    String inputName = (String) entry.getKey();  
	                    String inputValue = (String) entry.getValue();  
	                    if (inputValue == null) {  
	                        continue;  
	                    }  
	                    File file = new File(inputValue);  
	                    String filename = file.getName();  
//	                    MagicMatch match = Magic.getMagicMatch(file, false, true);  
//	                    String contentType = match.getMimeType();  
	  
	                    StringBuffer strBuf = new StringBuffer();  
	                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");  
	                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename + "\"\r\n");  
	                    strBuf.append("Content-Type:text/plain\r\n\r\n");  
	  
	                    out.write(strBuf.toString().getBytes());  
	  
	                    DataInputStream in = new DataInputStream(new FileInputStream(file));  
	                    int bytes = 0;  
	                    byte[] bufferOut = new byte[1024];  
	                    while ((bytes = in.read(bufferOut)) != -1) {  
	                        out.write(bufferOut, 0, bytes);  
	                    }  
	                    in.close();  
	                }  
	            }  
	            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();  
	            out.write(endData);  
	            out.flush();  
	            out.close();  
	  
	            // 读取返回数据    
	            StringBuffer strBuf = new StringBuffer();  
	            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
	            String line = null;  
	            while ((line = reader.readLine()) != null) {  
	                strBuf.append(line).append("\n");  
	            }  
	            res = strBuf.toString();
				HttpResponseInfo httpResponseInfo = new HttpResponseInfo();
				httpResponseInfo.setStatus(conn.getResponseCode());
		        httpResponseInfo.setResponseContent(res);
	            reader.close();  
	            reader = null;  
	            return httpResponseInfo;
	        } catch (Exception e) {  
	        	logger.info("发送POST请求出错。" + urlStr,e);  
	        } finally {  
	            if (conn != null) {  
	                conn.disconnect();  
	                conn = null;  
	            }  
	        }
			return null;  

	    }  
	public void setHeaders(Map<String, Object> headers) {
		this.headers = headers;
	}

	public Map<String, Object> getHeaders() {
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
