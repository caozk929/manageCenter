package com.zjht.manager.common.dto;

public class ApiConstants {
	//接口返回代码
	public static String STATUS = "status";
	//接口返回消息
	public static String MSG = "msg";
	
	//接口处理发生异常错误而失败
	public static String FAIL = "99999";
	//接口处理发生异常错误时提示消息
	public static String FAIL_API_MSG = "接口处理发生错误";
	//服务器异常时错误消息
	public static String FAIL_COMM_MSG = "服务器内部错误";
	//接口业务处理成功
	public static String SUCCESS = "00000";
	//接口业务处理成功消息
	public static String SUCCESS_MSG = "处理成功";

	/**
	 * 参数赋值符号
	 */
	public static final String PARA_ASSIGN = "=";

	/**
	 * 参数分隔符
	 */
	public static final String PARA_SPT = "&";
	
}
