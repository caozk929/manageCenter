package com.zjht.manager.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
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
        /*  使用FastJson提供的FastJsonJsonView视图返回，不需要捕获异常   */  
        FastJsonJsonView view = new FastJsonJsonView();
        JSONObject json = new JSONObject();
        json.put("code", "1000001");  
        json.put("msg", "请求处理错误");  
        view.setAttributesMap(json); 
        mv.setView(view);   
        log.debug("接口处理发生异常:", ex);  
        return mv; 
	}
}