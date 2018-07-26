package com.xphwv.permission.authorized;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 权限控制拦截器
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Authorized {

	/**
	 * 功能代码（如有多个功能，请用逗号分隔,如果为空则只验证是否为用户 ） 如果页面中加了@Authorized 拦截器的话，会自动在
	 * beat.getModel().add("funcMap", funcMap)写入用户的功能表 在VIEW 层里可以用
	 * #if($funcMap.checkFunc("zhaopin.jingpin")) **** #end 判断权限
	 * 
	 * @return
	 */
	String [] code() default {};

	/**
	 * 功能检查类型 (默认只要有一个功能验证通过则为真)
	 * 
	 * @return
	 */
	FuncCheckType checkType() default FuncCheckType.OR;

	/**
	 * 如果没有权限则转向的页面,如果为 "" 则使用 .properties里配置的 errorUrl。
	 * ActionType为ActionType.REDIRECT时有效
	 * 
	 * @return
	 */
	String errorUrl() default "";

	/**
	 * 如果没有权限的提示信息,ActionType为ActionType.MESSAGE 时有效
	 * 
	 * @return
	 */
	String msg() default "没有权限";

	/**
	 * 权限验证失败后的操作方式
	 * 
	 * @return
	 */
	ActionType actionType() default ActionType.REDIRECT;

	/**
	 * 执行脚本,ActionType为ActionType.SCRIPT时有效
	 * 
	 * @return
	 */
	String script() default "";

	/**
	 * 检查是否为用户,只允许用户登录
	 * 
	 * @return
	 */
	boolean check() default true;


}
