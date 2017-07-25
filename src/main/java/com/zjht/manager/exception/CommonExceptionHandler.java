package com.zjht.manager.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
/**
 * 统一异常处理器，主要用来记录运行时异常日志
 * @author caozhaokui
 *
 */
public class CommonExceptionHandler implements HandlerExceptionResolver {
	//日志记录器
	public static Logger log = LoggerFactory.getLogger(CommonExceptionHandler.class);
	
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, 
			Object handler,	Exception ex) {
		if(handler instanceof HandlerMethod){
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			log.error(handlerMethod.getBeanType().getName(), ex);
		}
		return null;
	}
}