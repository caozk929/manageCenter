package com.zjht.manager.common.web.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zjht.manager.common.web.Constants;
import com.zjht.manager.common.web.annotation.Secured;
import com.zjht.manager.common.web.threadvariable.MemberThread;
import com.zjht.manager.entity.User;

/**
 * 前台安全拦截器
 * 
 * @author
 * 
 */
public class FrontSecureInterceptor extends HandlerInterceptorAdapter implements InitializingBean {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		request.setAttribute(Constants.REQUEST_LOGIN_URL_KEY, loginUrl);
		Secured secured = handler.getClass().getAnnotation(Secured.class);
		if (secured != null) {
			if (MemberThread.get() == null) {
				redirectToLogin(request, response);
				return false;
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		User user = MemberThread.get();
		if (user != null && modelAndView != null) {
			modelAndView.addObject(Constants.MEMBER, user);
		}
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(loginUrl);
	}

	private void redirectToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String s = request.getRequestURI();
		int i = StringUtils.countMatches(s, "/");
		StringBuilder stringbuilder = new StringBuilder();
		int j = 2;
		if (!StringUtils.isBlank(request.getContextPath())) {
			j++;
		}
		for (; j < i; j++) {
			stringbuilder.append("../");
		}
		stringbuilder.append("login.do");
		response.sendRedirect(stringbuilder.toString());
	}

	private String loginUrl;

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

}
