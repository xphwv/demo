package com.xphwv.permission.authorized;

/**
 * 如果用户没有权限后的动作
 */
public enum ActionType {
	/**
	 * 重定向
	 */
	REDIRECT,
	/**
	 * 客户端角本
	 */
	SCRIPT,
	/**
	 * JSON数据 
	 */
	JSON,
	/**
	 * 返回信息
	 */
	MESSAGE
}
