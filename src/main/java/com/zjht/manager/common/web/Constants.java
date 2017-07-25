package com.zjht.manager.common.web;

/**
 * web常量
 * 
 * @author liufang
 * 
 */
public abstract class Constants {
	/**
	 * 路径分隔符
	 */
	public static final String SPT = "/";
	/**
	 * 点 .
	 */
    public static final String POINT = ".";
	/**
	 * ?
	 */
    public static final String QUESTION_MARK = "?";
	/**
	 * _
	 */
    public static final String UNDER_LINE = "_";
	/**
	 * -
	 */
    public static final String MIDDLE_LINE = "-";
	/**
	 * ,
	 */
    public static final String LIST_SPLIT = ",";
	/**
	 * http://
	 */
    public static final String HTTP_PROTOCOL = "http://";
	/**
	 * WEB-INF
	 */
    public static final String WEB_INF = "WEB-INF";
    public static final String MEMBER = "member";
    /**
     * 前台登录用户session Key（预留）
     */
    public static final String MEMBER_SESSION_KEY = "_member_key";
    /**
     *  后台登录用户session Key
     */
    public static final String ADMIN_SESSION_KEY = "_admin_key";
	/**
	 * UTF-8编码
	 */
	public static final String UTF8 = "UTF-8";
	/**
	 * 提示信息
	 */
	public static final String MESSAGE = "message";
	/**
	 * cookie中的JSESSIONID名称
	 */
	public static final String JSESSION_COOKIE = "JSESSIONID";
	/**
	 * url中的jsessionid名称
	 */
	public static final String JSESSION_URL = "jsessionid";
	/**
     * 认证用户对象
     */
    public static final String SESSION_USER       = "session_user";
	
	/**
	 * HTTP POST请求
	 */
	public static final String POST = "POST";
    /**
     * HTTP GET请求
     */
    public static final String GET = "GET";
	/**
	 * 后台页面目录
	 */
	public static final String ADMIN_PART = "admin";
	/**
	 * 前台页面目录
	 */
	public static final String FRONT_PART = "front";
	
	public static final String TEMPLATES_PATH     = "defaults";

    public static final String DEFAULT_CITY       = "4401";

    public static final String ERROR_MESSAGE      = "error_message";
    
    /**
     * 登录成功，并处理后，返回到该地址。
     */
    public static final String RETURN_URL         = "returnUrl";

    /**
     * 登录成功后的处理地址。登录成功后即重定向到该页面，并将returnUrl、auth_key作为参数。
     */
    public static final String PROCESS_URL        = "processUrl";

    /**
     * 认证信息session key
     */
    public static final String AUTH_KEY           = "auth_key";
    
    /**
     * 用户权限菜单
     */
    public static final String MENU_LIST		  = "menu_key";
    
	/**
	 * 支付状态
	 */
	public static final String CEP_PAYMENT_SUCCESS	= "S";//支付成功
	public static final String CEP_PAYMENT_FAILED	= "F";//支付失败

	public static final String SESSION_PAY_PAYMENTINFO = "session_pay_paymentInfo";
	public static final String SESSION_PAY_VERIFYCODE = "session_pay_verifycode";
	
	public static final String REQUEST_LOGIN_URL_KEY = "_login_url";
}
