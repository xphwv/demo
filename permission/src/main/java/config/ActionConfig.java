package config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class ActionConfig  {
	protected HttpServletRequest request;
	protected HttpServletResponse response;

	public ActionConfig() {
    }

	

	public void render() {
		try {
			action();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	protected abstract void action() throws Exception;
}
