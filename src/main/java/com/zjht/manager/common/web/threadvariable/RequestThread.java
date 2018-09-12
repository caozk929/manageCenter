package com.zjht.manager.common.web.threadvariable;

import javax.servlet.http.HttpServletRequest;

/**
 * 每次后台请求的上下文，用于在一次请求过程中
 */
public class RequestThread {

    private static ThreadLocal<HttpServletRequest> instance = new ThreadLocal<HttpServletRequest>();


    public static HttpServletRequest get() {
        return instance.get();
    }

    public static void set(HttpServletRequest request) {
        instance.set(request);
    }

    public static void remove() {
        instance.remove();
    }
}
