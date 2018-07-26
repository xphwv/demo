package com.xphwv.permission.action;

import config.ActionConfig;

public abstract class AbstractActionResult extends ActionConfig {

	protected void resultHtml(String html) throws Exception {
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		response.setHeader("Pragma", "No-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().write(html);
	}

	protected void resultRedirect(String url) throws Exception {
		response.sendRedirect(url);
	}
}
