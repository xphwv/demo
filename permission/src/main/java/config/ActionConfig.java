package config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class ActionConfig extends ActionResult {
	protected HttpServletRequest request;
	protected HttpServletResponse response;

	public ActionConfig() {
		this.request = beat().getRequest();
		this.response = beat().getResponse();
	}

	

	@Override
	public void render() {
		try {
			action();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	protected abstract void action() throws Exception;
}
