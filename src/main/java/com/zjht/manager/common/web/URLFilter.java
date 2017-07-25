package com.zjht.manager.common.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.zjht.manager.common.web.threadvariable.SessionThread;

/**
 * 此类是用于对二级域名进行跳转
 * 
 * @author yangxiaoyong
 * @version 创建时间：2014年5月30日 下午2:43:15 参考：www.sql8.net
 */
public class URLFilter implements Filter {
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	/** 
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain) 
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		SessionThread.set(httpServletRequest.getSession());
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		String domain = "www.cm.youoil.cn";
		String domain2 = "ttj.youoil.cn";
		String domain3 = "yzf.youoil.cn";
		String domain4 = "dtkfc.youoil.cn";
		String domain5 = "66ka.com";
		String domain6 = "www.66ka.com";
        String domain7 = "gzcm.youoil.cn";
        String domain8 = "dgcm.youoil.cn";
		
		String serverName = httpServletRequest.getServerName().toLowerCase();
		String url = httpServletRequest.getRequestURI();
		String gotoUrl = "";
		if (serverName.indexOf(domain) >= 0) {
			if (url.equals("/") || url.toLowerCase().equals("/index.htm")) {
				gotoUrl = "http://www.cm.youoil.cn/front/telephone/index.htm";
				httpServletResponse.sendRedirect(gotoUrl);
				return;
			}
		} else if (serverName.indexOf(domain2) != -1) {
			if (url.equals("/") || url.toLowerCase().equals("/index.html")) {
				gotoUrl = "http://www.youoil.cn/front/ccr/index.html";
				httpServletResponse.sendRedirect(gotoUrl);
				return;
			}
		} else if (serverName.indexOf(domain3) != -1) {
			if (url.equals("/") || url.toLowerCase().equals("/index")) {
				gotoUrl = "http://www.youoil.cn/front/yzf/index";
				httpServletResponse.sendRedirect(gotoUrl);
				return;
			}
		} else if (serverName.indexOf(domain4) != -1) {
			if (url.equals("/") || url.toLowerCase().equals("/index")) {
				gotoUrl = "http://www.youoil.cn/front/dt/index.htm";
				httpServletResponse.sendRedirect(gotoUrl);
				return;
			}
		} else if (serverName.indexOf(domain5) != -1
 || serverName.indexOf(domain6) != -1) {
            if (url.equals("/") || url.toLowerCase().equals("/index")) {
                gotoUrl = "http://www.youoil.cn/webapp/demand/index.html";
                httpServletResponse.sendRedirect(gotoUrl);
                return;
            }
        } else if (serverName.indexOf(domain7) != -1) {
            if (url.equals("/") || url.toLowerCase().equals("/index")) {
                gotoUrl = "http://www.youoil.cn/front/telephoneIntegral/index.htm?cityCode=4401";
                httpServletResponse.sendRedirect(gotoUrl);
                return;
            }
        } else if (serverName.indexOf(domain8) != -1) {
            if (url.equals("/") || url.toLowerCase().equals("/index")) {
                gotoUrl = "http://www.youoil.cn/front/telephoneIntegral/index.htm?cityCode=4402";
                httpServletResponse.sendRedirect(gotoUrl);
                return;
            }
        }
		if(url.contains("webapp/brushRed/verify.do")){
			HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper(httpServletResponse) {
	            @Override
	           public String encodeRedirectUrl(String url) {
	               return url;
	            }
	           public String encodeRedirectURL(String url) {
	               return url;
	            }
	           public String encodeUrl(String url) {
	               return url;
	            }
	           public String encodeURL(String url) {
	               return url;
	            }
	        };
			//处理
			chain.doFilter(request, wrappedResponse);
		}else{
			// 不处理
			chain.doFilter(request, response);
		}

	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {}
}
