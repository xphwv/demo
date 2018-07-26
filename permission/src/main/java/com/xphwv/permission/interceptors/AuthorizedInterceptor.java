package com.xphwv.permission.interceptors;

import java.lang.reflect.Method;

import com.xphwv.permission.authorized.AuthorizedCheck;
import org.apache.log4j.Logger;


public class AuthorizedInterceptor extends DWFInterceptor {

	Logger logger = Logger.getLogger(AuthorizedInterceptor.class);

	@Override
	public float order() {
		return 1;
	}

	@Override
	public ActionResult before(BeatContext beat) throws Exception {
		logger.info("-----------------------");
		Method method = ((MethodAction) beat.getAction()).getMethod();

		return AuthorizedCheck.getInstance().filter(beat.getRequest(), beat.getResponse(), method);
	}

	@Override
	public ActionResult after(BeatContext beat) throws Exception {
		return null;
	}

	@Override
	public void complet(BeatContext beat) throws Exception {
	}

}
