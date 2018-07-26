package config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.xphwv.permission.entry.Func;
import com.xphwv.permission.entry.FuncMap;
import com.xphwv.permission.utils.CookieUtil;

public class UserConifg {

	public static long getUserId(HttpServletRequest request) {
		long userId = 0;
		try {
			userId = Long.parseLong(CookieUtil.getCookie(request, "acl_userId"));
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return userId;
	}

	public static FuncMap getFucMapByuserId(long userId) {
		List<Func> funcs = new ArrayList<Func>(40);
		funcs.add(new Func(1, "code.1"));
		funcs.add(new Func(2, "code.2"));
		funcs.add(new Func(3, "code.3"));
		funcs.add(new Func(4, "code.4"));
		FuncMap map = new FuncMap(funcs);
		return map;
	}
}
