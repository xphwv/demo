package com.xphwv.permission.authorized;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xphwv.permission.entry.FuncMap;
import org.apache.log4j.Logger;

import com.xphwv.permission.action.AbstractActionResult;
import com.xphwv.permission.action.JsonActionResult;
import com.xphwv.permission.action.RedirectActionResult;
import com.xphwv.permission.action.ScriptActionResult;

import config.SystemConfig;
import config.UserConifg;

public class AuthorizedCheck {
	private static Logger logger = Logger.getLogger(AuthorizedCheck.class);
	public static final String SPLIT_REGEX = ",";
	private static final String USER_FUNCMAP_CAHCENAME = "user_funcmap";

	private AuthorizedCheck() {
	}

	private static AuthorizedCheck instance = new AuthorizedCheck();

	public static AuthorizedCheck getInstance() {
		return instance;
	}

	public AbstractActionResult filter(HttpServletRequest request, HttpServletResponse response, Method method) throws IOException, ServletException {
		response.addHeader("Cache-Control", "private");
		if (SystemConfig.isDebug()) {
			return null;
		}
		Authorized author = method.getAnnotation(Authorized.class);
		boolean access = true;
		if (author == null) {
			return null;
		}
		try {
			long userId = UserConifg.getUserId(request);
			if (userId > 0) {
				// 验证权限
				String[] codes = author.code();
				if (codes != null && codes.length > 0) {
					FuncMap funcMap = getCurrentUserFuncMap(request);
					if (author.checkType() == FuncCheckType.AND) {
						access = funcMap.checkFuncAnd(codes);
					} else {
						access = funcMap.checkFuncOr(codes);
					}
					if (access) {
						request.setAttribute("funcMap", funcMap);
					}
				}

			} else {
				access = false;
			}
			if (access) {
				return null;
			}
		} catch (Exception e) {
			logger.error("权限验证失败", e);
		}
		String errorUrl = author.errorUrl();
		if (errorUrl == null || "".equals(errorUrl)) {
			errorUrl = SystemConfig.getErrorUrl();
		}
		switch (author.actionType()) {
		case JSON:
			String jsonBody = "{msg:\"" + author.msg() + "\",code:" + author.code() + "\"}";
			return new JsonActionResult(jsonBody);
		case REDIRECT:
			return new RedirectActionResult(errorUrl);
		case SCRIPT:
			return new ScriptActionResult(author.script(), errorUrl);
		case MESSAGE:
			return new ScriptActionResult(author.msg());
		default:
			return new RedirectActionResult(errorUrl);
		}
	}

	private FuncMap getCurrentUserFuncMap(HttpServletRequest request) {
		FuncMap userFuncsMap = (FuncMap) request.getAttribute(USER_FUNCMAP_CAHCENAME);
		if (userFuncsMap == null) {
			long userId = UserConifg.getUserId(request);
			if (userId > 0) {
				userFuncsMap = UserConifg.getFucMapByuserId(userId);
				request.setAttribute(USER_FUNCMAP_CAHCENAME, userFuncsMap);
			}
		}
		return userFuncsMap;
	}

}