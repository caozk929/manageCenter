package com.zjht.manager.util.logback;

import com.zjht.manager.util.DateTimeUtils;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
/**
 * logback日志扩展，输出线程号
 * @author caozhaokui
 *
 */
public class ThreadNumConverter extends ClassicConverter {
	//线程本地变量，用于保存随机数
	private ThreadLocal<String> local = new ThreadLocal<String>();
	@Override
	public String convert(ILoggingEvent event) {
		if(local.get()==null){
			String threadId = String.valueOf(System.currentTimeMillis()).substring(4);
			local.set(threadId);
		}
		return local.get();
	}
}
