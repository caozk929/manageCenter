package com.zjht.manager.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @outhor caozk
 * @create 2017-09-05 19:45
 */
public class CookieManagerBase {

    private static final Log          log             = LogFactory.getLog(CookieManagerBase.class);

    protected static final int        MAX_COOKIE_AGE  = 24 * 60 * 60 * 1000 * 30;

    protected static final int        TEMP_COOKIE_AGE = -1;

    protected static final int        EXPIRED_TIME    = 60 * 60 * 1000;

    private final HttpServletRequest  request;

    private final HttpServletResponse response;

    private final String              domain;

    private final String              path;


    public CookieManagerBase(HttpServletRequest request, HttpServletResponse response, String domain, String path) {

        this.request = request;
        this.response = response;
        this.domain = domain;
        this.path = path;
    }

    @SuppressWarnings("unchecked")
    private Map parseCookie(String cookieName) {
        Map valueMap = (Map) request.getAttribute(cookieName);
        if (valueMap != null) { return valueMap; }

        valueMap = new HashMap();
        request.setAttribute(cookieName, valueMap);

        String cookieValue = getDecryptedCookieValue(cookieName);
        if (StringUtils.isEmpty(cookieValue)) { return valueMap; }

        String[] kvPairs = cookieValue.split("&");
        for (int i = 0; i < kvPairs.length; i++) {
            if (StringUtils.isNotEmpty(kvPairs[i])) {
                int offset = kvPairs[i].indexOf('=');
                if (offset <= 0) {
                    continue;
                }
                String key = kvPairs[i].substring(0, offset);
                String value = kvPairs[i].substring(offset + 1);
                if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
                    try {
                        valueMap.put(key, URLDecoder.decode(value, "UTF-8"));
                    } catch (UnsupportedEncodingException ex) {
                        log.error("failed to decode cookie value: " + value, ex);
                    }
                }
            }
        }

        return valueMap;
    }

    private String getDecryptedCookieValue(String cookieName) {
        String cookieValue = getCookieValue(cookieName, null);
        if (cookieValue == null) { return null; }

        try {
            String decodedValue = URLDecoder.decode(cookieValue, "ISO-8859-1");
            return new String(new Encrypter().decrypt(decodedValue.getBytes("ISO-8859-1")), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("failed to decrypt cookie value: " + cookieValue, e);
            return null;
        }
    }

    public String getCookieValue(String cookieName, String defaultValue) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookieName.equals(cookie.getName())) { return cookie.getValue(); }
            }
        }

        return defaultValue;
    }

    private void addEncryptedCookie(String cookieName, String cookieValue, int age) {
        String encryptedValue;
        try {
            encryptedValue = new String(new Encrypter().encrypt(cookieValue.getBytes("UTF-8")), "ISO-8859-1");

            encryptedValue = URLEncoder.encode(encryptedValue, "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            log.error("failed to encrypt cookie value: " + cookieValue, e);
            return;
        }

        Cookie cookie = new Cookie(cookieName, encryptedValue);
        cookie.setMaxAge(age);
        if (StringUtils.isNotBlank(path)) {
            cookie.setPath(path);
        }

        if (StringUtils.isNotBlank(domain)) {
            cookie.setDomain(domain);
        }

        response.addCookie(cookie);
    }

    protected String getValue(String cookieName, String key) {
        return getValue(cookieName, key, null);
    }

    @SuppressWarnings("unchecked")
    protected String getValue(String cookieName, String key, String defaultValue) {
        Map valueMap = parseCookie(cookieName);
        String value = (String) valueMap.get(key);
        return StringUtils.isEmpty(value) ? defaultValue : value;
    }

    @SuppressWarnings("unchecked")
    protected void setValue(String cookieName, String key, String value) {
        Map valueMap = parseCookie(cookieName);
        if (StringUtils.isNotEmpty(value)) {
            valueMap.put(key, value);
        } else {
            valueMap.remove(key);
        }
    }

    @SuppressWarnings("unchecked")
    protected void save(String cookieName, int age) {
        Map valueMap = parseCookie(cookieName);
        StringBuffer sb = new StringBuffer();

        for (Iterator i = valueMap.entrySet().iterator(); i.hasNext();) {
            Entry e = (Entry) i.next();
            String key = (String) e.getKey();
            if (StringUtils.isNotEmpty(key)) {
                String value = (String) e.getValue();
                if (StringUtils.isNotEmpty(value)) {
                    if (sb.length() > 0) {
                        sb.append('&');
                    }

                    sb.append(key);
                    sb.append('=');

                    try {
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                    } catch (UnsupportedEncodingException ex) {
                        log.error("failed to encode cookie value: " + value, ex);
                    }
                }
            }
        }

        addEncryptedCookie(cookieName, sb.toString(), age);
    }

    protected void remove(String cookieName) {
        save(cookieName, 0);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookieName.equals(cookie.getName())) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }

}
