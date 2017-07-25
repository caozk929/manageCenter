package com.zjht.manager.common.web.interceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zjht.manager.common.web.Constants;
import com.zjht.manager.common.web.session.SessionProvider;
import com.zjht.manager.common.web.threadvariable.AdminThread;
import com.zjht.manager.entity.User;

/**
 * 鍚庡彴淇℃伅鎷︽埅鍣�
 * 
 * 鐧诲綍淇℃伅銆佹潈闄愪俊鎭�
 * 
 * @author LIJUNJIE
 * 
 */
public class AdminContextInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler)
			throws ServletException {
		User user=(User) session.getAttribute(request, Constants.ADMIN_SESSION_KEY);
		// 鎸囧畾绠＄悊鍛樺瓨鍦�
		if (user!=null) {
			// 灏嗙鐞嗗憳淇℃伅鏀惧叆ThreadLocal
			AdminThread.set(user);
			// 绠＄悊鍛樿璇佹斁鍏equest
			request.setAttribute(Constants.AUTH_KEY, user.getUserName()+user.getId());
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler, ModelAndView mav)
			throws Exception {
		//鑾峰彇绠＄悊鍛樹俊鎭�
		User user=AdminThread.get();
		//璇锋眰杞彂
		if (user!=null && mav != null && mav.getModelMap() != null
				&& mav.getViewName() != null
				&& !mav.getViewName().startsWith("redirect:")) {
			mav.getModelMap().addAttribute(Constants.AUTH_KEY,
					user.getUserName()+user.getId());
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		AdminThread.remove();
	}
//	@Autowired
	private SessionProvider session;
	
	private String loginUrl;

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
}
