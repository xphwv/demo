package com.xphwv.permission.action;



public final class RedirectActionResult extends AbstractActionResult {
	private String url;

	public RedirectActionResult(String url) {
		this.url = url;
	}

	@Override
	protected void action()  throws Exception{
		resultRedirect(url);
	}

}
