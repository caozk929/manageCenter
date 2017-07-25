package com.zjht.manager.common.web.interceptor;

import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.LocaleEditor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

public class AdminLocaleInterceptor extends HandlerInterceptorAdapter {
	public static final String LOCALE = "locale";

    @Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
    		  Object obj) throws ServletException{
        LocaleResolver localeresolver = RequestContextUtils.getLocaleResolver(request);
        if(localeresolver == null){
            throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
        } else{
        	String language="zh_CN";
            LocaleEditor localeeditor = new LocaleEditor();
            localeeditor.setAsText(language);
            localeresolver.setLocale(request, response, (Locale)localeeditor.getValue());
            return true;
        }
    }

    @Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, 
    		Object obj, ModelAndView modelandview)  throws Exception{
        LocaleResolver localeresolver = RequestContextUtils.getLocaleResolver(request);
        if(localeresolver == null){
            throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
        }
        if(modelandview != null){
            modelandview.getModelMap().addAttribute(LOCALE, localeresolver.resolveLocale(request).toString());
        }
    }
}
