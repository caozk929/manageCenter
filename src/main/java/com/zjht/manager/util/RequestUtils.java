package com.zjht.manager.util;

import com.zjht.manager.common.web.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HttpServletRequest帮助类
 * @outhor caozk
 * @create 2017-09-05 19:45
 */
public class RequestUtils {

    private static final Logger log = LoggerFactory.getLogger(RequestUtils.class);

    /**
     * 获取QueryString的参数，并使用URLDecoder以UTF-8格式转码。如果请求是以post方法提交的，
     * 那么将通过HttpServletRequest#getParameter获取。
     * 
     * @param request
     *            web请求
     * @param name
     *            参数名称
     * @return
     */
    public static String getQueryParam(HttpServletRequest request, String name) {
        if (StringUtils.isBlank(name)) { return null; }
        if (request.getMethod().equalsIgnoreCase(Constants.POST)) { return request.getParameter(name); }
        String s = request.getQueryString();
        if (StringUtils.isBlank(s)) { return null; }
        try {
            s = URLDecoder.decode(s, Constants.UTF8);
        } catch (UnsupportedEncodingException e) {
            log.error("encoding " + Constants.UTF8 + " not support?", e);
        }
        String[] values = parseQueryString(s).get(name);
        if (values != null && values.length > 0) {
            return values[values.length - 1];
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getQueryParams(HttpServletRequest request) {
        Map<String, String[]> map;
        if (request.getMethod().equalsIgnoreCase(Constants.POST)) {
            map = request.getParameterMap();
        } else {
            String s = request.getQueryString();
            if (StringUtils.isBlank(s)) { return new HashMap<String, Object>(); }
            try {
                s = URLDecoder.decode(s, Constants.UTF8);
            } catch (UnsupportedEncodingException e) {
                log.error("encoding " + Constants.UTF8 + " not support?", e);
            }
            map = parseQueryString(s);
        }

        Map<String, Object> params = new HashMap<String, Object>(map.size());
        int len;
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            len = entry.getValue().length;
            if (len == 1) {
                params.put(entry.getKey(), entry.getValue()[0]);
            } else if (len > 1) {
                params.put(entry.getKey(), entry.getValue());
            }
        }
        return params;
    }

    /**
     * Parses a query string passed from the client to the server and builds a <code>HashTable</code> object with key-value pairs. The query string
     * should be in the form of a string packaged by the GET or POST method,
     * that is, it should have key-value pairs in the form <i>key=value</i>,
     * with each pair separated from the next by a &amp; character.
     * <p>
     * A key can appear more than once in the query string with different values. However, the key appears only once in the hashtable, with its value being an array of strings containing the multiple
     * values sent by the query string.
     * <p>
     * The keys and values in the hashtable are stored in their decoded form, so any + characters are converted to spaces, and characters sent in hexadecimal notation (like <i>%xx</i>) are converted
     * to ASCII characters.
     * 
     * @param s
     *            a string containing the query to be parsed
     * @return a <code>HashTable</code> object built from the parsed key-value
     *         pairs
     * @exception IllegalArgumentException
     *                if the query string is invalid
     */
    public static Map<String, String[]> parseQueryString(String s) {
        String valArray[] = null;
        if (s == null) { throw new IllegalArgumentException(); }
        Map<String, String[]> ht = new HashMap<String, String[]>();
        StringTokenizer st = new StringTokenizer(s, "&");
        while (st.hasMoreTokens()) {
            String pair = st.nextToken();
            int pos = pair.indexOf('=');
            if (pos == -1) {
                continue;
            }
            String key = pair.substring(0, pos);
            String val = pair.substring(pos + 1, pair.length());
            if (ht.containsKey(key)) {
                String oldVals[] = ht.get(key);
                valArray = new String[oldVals.length + 1];
                for (int i = 0; i < oldVals.length; i++) {
                    valArray[i] = oldVals[i];
                }
                valArray[oldVals.length] = val;
            } else {
                valArray = new String[1];
                valArray[0] = val;
            }
            ht.put(key, valArray);
        }
        return ht;
    }

    public static Map<String, String> getRequestMap(HttpServletRequest request, String prefix) {
        return getRequestMap(request, prefix, false);
    }

    public static Map<String, String> getRequestMapWithPrefix(HttpServletRequest request, String prefix) {
        return getRequestMap(request, prefix, true);
    }

    @SuppressWarnings("unchecked")
    private static Map<String, String> getRequestMap(HttpServletRequest request, String prefix, boolean nameWithPrefix) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration<String> names = request.getParameterNames();
        String name, key, value;
        while (names.hasMoreElements()) {
            name = names.nextElement();
            if (name.startsWith(prefix)) {
                key = nameWithPrefix ? name : name.substring(prefix.length());
                value = StringUtils.join(request.getParameterValues(name), ',');
                map.put(key, value);
            }
        }
        return map;
    }

    /**
     * 获取访问者IP 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     * 
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) { return ip; }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }

    /**
     * 获得当的访问路径
     * HttpServletRequest.getRequestURL+"?"+HttpServletRequest.getQueryString
     * 
     * @param request
     * @return
     */
    public static String getLocation(HttpServletRequest request) {
        UrlPathHelper helper = new UrlPathHelper();
        StringBuffer buff = request.getRequestURL();
        String uri = request.getRequestURI();
        String origUri = helper.getOriginatingRequestUri(request);
        buff.replace(buff.length() - uri.length(), buff.length(), origUri);
        String queryString = helper.getOriginatingQueryString(request);
        if (queryString != null) {
            buff.append("?").append(queryString);
        }
        return buff.toString();
    }

    public static String getParameter(String key, RequestContext request) {
        String s = request.getQueryString();
        if (StringUtils.isBlank(key) || request == null || StringUtils.isBlank(s)) { return ""; }
        try {
            s = URLDecoder.decode(s, Constants.UTF8);
        } catch (UnsupportedEncodingException e) {
            log.error("encoding " + Constants.UTF8 + " not support?", e);
        }
        String[] values = parseQueryString(s).get(key);
        if (values != null && values.length > 0) {
            return values[values.length - 1];
        } else {
            return "";
        }
    }

    /**
     * 将/xxx/xxx.xxx转换为/xxx/xxx
     * 
     * @param request
     * @return
     */
    public static String getRestUrl(HttpServletRequest request) {
        String url = request.getRequestURI();
        if (url.lastIndexOf(".") > -1) {
            url = url.split("\\.")[0];
        }
        return url;
    }

    /**
     * 获取请求中的域名
     * 
     * @param request
     * @return
     */
    public static String getDomain(HttpServletRequest request) {
        String fromURL = request.getHeader("Referer");
        Pattern p = Pattern.compile("[a-zA-z]+://[^/]*");
        Matcher matcher = p.matcher(fromURL);
        matcher.find();
        return matcher.group() + "/";
    }

    /**
     * 获得请求的session id，但是HttpServletRequest#getRequestedSessionId()方法有一些问题。
     * 当存在部署路径的时候，会获取到根路径下的jsessionid。
     * 
     * @see HttpServletRequest#getRequestedSessionId()
     * 
     * @param request
     * @return
     */
    public static String getRequestedSessionId(HttpServletRequest request) {
        String sid = request.getRequestedSessionId();
        String ctx = request.getContextPath();
        // 如果session id是从url中获取，或者部署路径为空，那么是在正确的。
        if (request.isRequestedSessionIdFromURL() || StringUtils.isBlank(ctx)) {
            return sid;
        } else {
            // 手动从cookie获取
            Cookie cookie = CookieUtils.getCookie(request, Constants.JSESSION_COOKIE);
            if (cookie != null) {
                return cookie.getValue();
            } else {
                return null;
            }
        }
    }

    /**
     * 添加SESSION项
     * 
     * @param request
     * @param key
     * @param value
     */
    public static void addSessionItem(HttpServletRequest request, String key, Object value) {
        HttpSession session = request.getSession();
        session.setAttribute(key, value);
    }

    /**
     * 获得SESSION项
     * 
     * @param request
     * @param key
     * @return
     */
    public static Object getSessionItem(HttpServletRequest request, String key) {
        HttpSession session = request.getSession();
        return session.getAttribute(key);
    }

    /**
     * 删除SESSION项
     * 
     * @param request
     * @param key
     */
    public static void removeSessionItem(HttpServletRequest request, String key) {
        HttpSession session = request.getSession();
        session.removeAttribute(key);
    }

    /**
     * 获取ip地址
     * @param request
     * @return
     */   
    public static String getClientIP(HttpServletRequest request) {
    	//从SSL取客户IP
    	String ip = request.getHeader( "entrust-client-ip" );
    	//从格尔的SSL取
    	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
	         // 获取客户IP
			Cookie[] cookies = request.getCookies();
		    if(cookies == null) {
		        cookies = new Cookie[0];
		    }
		    for(int i = 0; i < cookies.length; i ++) {   
		    	Cookie cookie = cookies[i];
		    	if("KOAL_CLIENT_IP".equals(cookie.getName())) {
		    		ip = cookie.getValue();
		    	}
		    }
        }
    	// 反向代理
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if(ip.equals("127.0.0.1")){
            	//根据网卡取本机配置的 IP
            	InetAddress inet=null;
            	try {
            		inet = InetAddress.getLocalHost();
            	} catch (UnknownHostException e) {
            		// e.printStackTrace();
            	}
            	ip= inet.getHostAddress();
            }
        }
        //存在多级反向代理的情况下，从x-forwarded-for获取的值为逗号分隔的ip串
        if(ip.indexOf(",") > 0){
        	ip = ip.substring(0, ip.indexOf(",")).trim();
        }
        return ip;
    }
    /**
     * 获取客户端平台
     * @param request
     * @return pc,android,iphone,ipad,windows phone;
     */
    public static String getClientplatform(HttpServletRequest request){
    	String requestHeader = request.getHeader("user-agent");
		String[] deviceArray = new String[]{"android","iphone","ipad","windows phone"};
		if(requestHeader != null){
			requestHeader = requestHeader.toLowerCase();
			for(int i=0;i<deviceArray.length;i++){
				if(requestHeader.indexOf(deviceArray[i])>0){
					return deviceArray[i];
				}
			}
		}
		return "pc";
    }

    public static void main(String[] args) {
        String url = "http://www.payincode.com";
        // Pattern.compile("(?<=//).*");
        Pattern p = Pattern.compile("[a-zA-z]+://[^/]*");
        Matcher matcher = p.matcher(url);
        matcher.find();
        System.out.println(matcher.group());
    }
}
