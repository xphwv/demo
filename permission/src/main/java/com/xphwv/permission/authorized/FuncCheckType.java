package com.xphwv.permission.authorized;

/**
 * 多功能CODE的验证方式
 */
public enum FuncCheckType {
	/**
	 * 只要有一个为假则返回false
	 */
	AND,
	/**
	 * 只要有一个为真则返回true
	 * 
	 */
	OR
}
