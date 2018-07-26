package com.xphwv.entity;

public class HttpResponseInfo {
	private int status;
	private String responseContent;
	/**
	 * HttpResponse返回个状态码<br/>
	 */
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getResponseContent() {
		return responseContent;
	}
	public void setResponseContent(String responseContent) {
		this.responseContent = responseContent;
	}
}
