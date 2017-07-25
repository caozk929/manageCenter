package com.zjht.manager.common.web.threadvariable;

import javax.servlet.http.HttpSession;
/**
 * sessionLocal
 * @author caozhaokui
 *
 */
public class SessionThread {
	private static ThreadLocal<HttpSession> sessionHolder = new ThreadLocal<HttpSession>();

	public static HttpSession get() {
		return sessionHolder.get();
	}

	public static void set(HttpSession session) {
		sessionHolder.set(session);
	}

	public static void remove() {
		sessionHolder.remove();
	}
}
