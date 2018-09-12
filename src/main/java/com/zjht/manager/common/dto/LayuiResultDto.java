package com.zjht.manager.common.dto;

/**
 * layUI前端接口数据传输对象
 * @author caozk
 * @param <T>
 */
public class LayuiResultDto<T> extends ResultDto<Object>{

	//用于前端layUI请求-记录总数
	private long count;

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

}
