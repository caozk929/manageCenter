package com.zjht.manager.api.common;

/**
 * 接口数据传输对象
 * @author caozk
 * @param <T>
 */
public class ResultDto<T> {
	//接口业务处理状态
	private String status;
	//接口业务处理结果
	private String msg;
	//接口返回数据
	private T data;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
}
