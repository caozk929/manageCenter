package com.zjht.manager.common.web.session;

import com.zjht.manager.entity.User;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * session提供者
 * @outhor caozk
 * @create 2017-08-26 11:00
 */
public interface SessionProvider{

    /**
     * 获取session中name的值
     * @param name
     * @return
     */
    Serializable getAttribute(String name);

    /**
     * 将key-value存入session
     * @param name
     * @param value
     */
    void setAttribute(String name, Serializable value);
    /**
     * 将当前用户信息存入session
     */
    void setPermissions(ArrayList<String> permissions);

    /**
     * 获取当前用户
     * @return User
     */
    ArrayList<String> getPermissions();

    /**
     * 将当前用户信息存入session
     */
    void setUser(User user);

    /**
     * 获取当前用户
     * @return User
     */
    User getUser();

    /**
     * 用户登录
     * @param userName
     * @param password
     */
    void login(String userName, String password);

    /**
     * 用户退出
     */
    void logout();



}
