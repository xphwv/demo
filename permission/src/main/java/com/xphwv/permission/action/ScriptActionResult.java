package com.xphwv.permission.action;


public final class ScriptActionResult extends AbstractActionResult {
	private String message;
	private String errorUrl;

	public ScriptActionResult(String message) {
		this.message = message;
	}

	public ScriptActionResult(String message, String errorUrl) {
		this.message = message;
		this.errorUrl = errorUrl;
	}

	@Override
	protected void action()  throws Exception{
		StringBuffer script = new StringBuffer();
		script.append("<script type=\"text/javascript\">");
		if (message != null && !"".equals(message))
			script.append("alert(\"").append(message).append("\");");
		else if (errorUrl != null && !"".equals(errorUrl))
			script.append("top.window.location.href=\"").append(errorUrl).append("\"");
		script.append("</script>");
		resultHtml(script.toString());
	}
}
