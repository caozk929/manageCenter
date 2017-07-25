package com.zjht.manager.api.common;

public class CommApiException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3196424277276721517L;
	//异常错误码
	private String code;
	//异常错误消息
	private String msg;
	
	public CommApiException(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
