package com.zjht.manager.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 提供 cookie的统一管理
 * @outhor caozk
 * @create 2017-09-05 19:45
 */
public class CookieManager extends CookieManagerBase {

    public static final String TEMP_COOKIE_NAME = "PAY_IN_CODE_COOKIE_TEMP";

    public static final String BASE_COOKIE_NAME = "PAY_IN_CODE_COOKIE";

    public static final String USER_ID          = "USER_ID";

    public static final String USER_CODE        = "USER_CODE";

    public static final String MOBLIE           = "MOBLIE";

    public static final String USER_TYPE        = "USER_TYPE";

    public static final String LOGIN_TIME       = "LOGIN_TIME";

    public static final String UPDATE_TIME      = "UPDATE_TIME";

    public static final String USER_NAME        = "USER_NAME";

    public static final String COMPANY_NO       = "COMPANY_NO";

    public static final String EMAIL            = "EMAIL";

    public static final String CITY             = "CITY";

    public static final String CITY_NAME        = "CITY_NAME";

    public static final String TEMPLATES        = "TEMPLATES";

    public static final String REMEMBER         = "REMEMBER";

    public static final String CART_SHOPPING    = "CART";                   // 购物车订单记录

    public static final String ZT_URL           = "ZT_URL";                 // 专题URL


    public CookieManager(HttpServletRequest request, HttpServletResponse response, String domain, String path) {
        super(request, response, domain, path);
    }

    public CookieManager(HttpServletRequest request) {
        this(request, null, null, null);
    }

    /**
     * 获得临时cookie中的一个项值
     */
    public String getTempCookie(String key) {
        return getTempCookie(key, null);
    }

    /**
     * 获得cookie的值
     */
    public String getCookie(String cookieName) {
        return getCookieValue(cookieName, null);
    }

    /**
     * 获得临时cookie中的一个项值。若为空，则返回defaultValue
     */
    public String getTempCookie(String key, String defaultValue) {
        return getValue(TEMP_COOKIE_NAME, key, defaultValue);
    }

    /**
     * 设置临时cookie的一个项值。若value为空，则相当于removeTempCookie
     */
    public void setTempCookie(String key, String value) {
        setValue(TEMP_COOKIE_NAME, key, value);
    }

    /**
     * 删除一个临时cookie项
     */
    public void removeTempCookie(String key) {
        setValue(TEMP_COOKIE_NAME, key, null);
    }

    /**
     * 保存临时cookie中所有的项，并且写入response
     */
    public void saveTempCookie() {
        save(TEMP_COOKIE_NAME, TEMP_COOKIE_AGE);
    }

    public void removeCookie() {
        remove(TEMP_COOKIE_NAME);
    }
}
