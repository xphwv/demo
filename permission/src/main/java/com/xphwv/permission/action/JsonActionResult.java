package com.xphwv.permission.action;

public final class JsonActionResult extends AbstractActionResult {
	private String json;

	public JsonActionResult(String json) {
		this.json = json;
	}

	@Override
	protected void action() throws Exception {
		resultHtml(json);
	}

}
