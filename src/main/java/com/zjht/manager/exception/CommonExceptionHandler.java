package com.zjht.manager.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.zjht.manager.common.dto.ApiConstants;
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
		ModelAndView mv = new ModelAndView();
        /*  使用FastJson提供的FastJsonJsonView视图返回，不需要捕获异常   */
		FastJsonJsonView view = new FastJsonJsonView();
		JSONObject json = new JSONObject();
		json.put(ApiConstants.STATUS, ApiConstants.FAIL);
		String errMsg = ex.getMessage()==null?ApiConstants.FAIL_COMM_MSG:ex.getMessage();
		json.put(ApiConstants.MSG, errMsg);
		view.setAttributesMap(json);
		mv.setView(view);

		return mv;
	}
}