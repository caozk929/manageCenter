package com.zjht.manager.common.web.interceptor;

import com.zjht.manager.common.web.Constants;
import com.zjht.manager.common.web.annotation.NoNeedAuth;
import com.zjht.manager.common.web.session.SessionProvider;
import com.zjht.manager.common.web.threadvariable.RequestThread;
import com.zjht.manager.entity.Menu;
import com.zjht.manager.entity.User;
import com.zjht.manager.service.MenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminSecureInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private MenuService menuService;
    @Autowired
    private SessionProvider sessionProvider;

    private Logger logger = LoggerFactory.getLogger(AdminSecureInterceptor.class);

    @Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
    		 Object obj) throws Exception{
        logger.debug("前置处理");

        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String url = httpRequest.getRequestURI();
        url = url.split(";")[0];
        //如果请求配置了不需要权限注解，直接放过请求
        if(obj instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod)obj;
            NoNeedAuth noNeedAuth = handlerMethod.getMethod().getAnnotation(NoNeedAuth.class);
            if(noNeedAuth != null){
                Menu menu = menuService.findByPath(url);
                if(menu != null){
                    RequestThread.get().setAttribute(Constants.BUSINESS_CODE, menu.getCode());
                }
                return true;
            }
        }
        Subject currentUser = SecurityUtils.getSubject();
        //操作的业务编码
        String bizCode = request.getParameter(Constants.BUSINESS_CODE);
        if(bizCode == null){
            throw new IllegalArgumentException(Constants.BUSINESS_CODE + "为空");
        }
        Menu menu = menuService.findByCode(bizCode);
        if(menu==null){
            throw new IllegalArgumentException(Constants.BUSINESS_CODE + ":" + bizCode + "对应的请求不存在");
        }
        //如果请求的url和配置的菜单不匹配
        if(!menu.getPath().contains(url)){
            throw new IllegalArgumentException(Constants.BUSINESS_CODE + ":" + bizCode + "和请求" + url + "不匹配");
        }
        if(!currentUser.isPermitted(bizCode)){
            throw new IllegalArgumentException("您不具有：" + bizCode + "请求的权限");
        }
        return true;//返回 false 将中断后续拦截器链的执行
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //获取管理员信息
        User user=sessionProvider.getUser();
        //将用户信息添加入model
        if (user != null && modelAndView != null) {
            modelAndView.addObject(Constants.MEMBER, user);
            modelAndView.addObject(Constants.PERMISSIONS, sessionProvider.getPermissions());
        }
        super.postHandle(request, response, handler, modelAndView);
    }

}
