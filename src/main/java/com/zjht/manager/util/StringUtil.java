package com.zjht.manager.util;


import java.util.List;

/**
 * String工具类
 * @author zhangyongjun
 * 2017年6月13日 下午5:16:15
 */

public class StringUtil {

	/**
	 * 失败返回，不返回null,避免空指针
	 */
	private static final String RETURN_FAIL = "";
	
	/**
	 * 分隔符
	 */
	private static final String SEPARATOR = ",";
	
	public static String arrayToStr(long[] o) {
		if (o == null || o.length == 0) {
			return RETURN_FAIL;
		}
		StringBuilder sb = new StringBuilder();
		for (int inxex = 0; inxex < o.length; inxex++) {
			if (inxex != 0) {
				sb.append(SEPARATOR + o[inxex]);
			} else {
				sb.append(o[inxex]);
			}
		}
		return sb.toString();
	}

	/**
	 * 中间带有逗号的字符串
	 * @param o
	 * @return
	 */
	public static String listToStr(List<String> o) {
		if (o == null || o.size() == 0) {
			return RETURN_FAIL;
		}
		StringBuilder sb = new StringBuilder();
		for (int inxex = 0; inxex < o.size(); inxex++) {
			if (inxex == 0) {
				sb.append(o.get(inxex));
			} else {
				sb.append(SEPARATOR).append(o.get(inxex));
			}
		}
		return sb.toString();
	}
	
	
	
	
	
	
	
	
	
	
}
