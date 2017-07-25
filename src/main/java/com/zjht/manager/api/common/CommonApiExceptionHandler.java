package com.zjht.manager.api.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
/**
 * 统一异常处理器，主要用来记录运行时异常日志
 * @author caozhaokui
 *
 */
public class CommonApiExceptionHandler implements HandlerExceptionResolver {
	//日志记录器
	public static Logger log = LoggerFactory.getLogger(CommonApiExceptionHandler.class);
	
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, 
			Object handler,	Exception ex) {
		ModelAndView mv = new ModelAndView();  
        JSONObject json = new JSONObject();
        if(ex instanceof CommApiException){
        	CommApiException commEx = (CommApiException)ex; 
            json.put(ApiConstants.STATUS, commEx.getCode());  
            json.put(ApiConstants.MSG, commEx.getMsg()); 
        }else{
            json.put(ApiConstants.STATUS, ApiConstants.FAIL);  
            json.put(ApiConstants.MSG, ApiConstants.FAIL_MSG); 
        }
        FastJsonJsonView view = new FastJsonJsonView();
        view.setAttributesMap(json); 
        mv.setView(view);   
        log.debug("接口处理发生异常:", ex);  
        return mv; 
	}
}