package config;

public final class SystemConfig {

	/**
	 * 没有权限后的转向页面
	 */
	private static String errorUrl = "/404.html";
	/**
	 * 是否是调试状态
	 */
	private static boolean debug = false;

	/**
	 * 没有权限后的转向页面
	 */
	public static String getErrorUrl() {
		return errorUrl;
	}

	/**
	 * 是否是调试状态
	 */
	public static boolean isDebug() {
		return debug;
	}

}
