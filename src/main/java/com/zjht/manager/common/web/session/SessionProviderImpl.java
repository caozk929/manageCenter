package com.zjht.manager.common.web.session;

import com.zjht.manager.common.web.Constants;
import com.zjht.manager.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * session提供者实现
 * @outhor caozk
 * @create 2017-08-26 11:00
 */
public class SessionProviderImpl implements SessionProvider{
    @Override
    public Serializable getAttribute(String name) {
        Session session = SecurityUtils.getSubject().getSession();
        return (Serializable)session.getAttribute(name);
    }

    @Override
    public void setAttribute(String name, Serializable value) {
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(name,value);
    }

    @Override
    public void setPermissions(ArrayList<String> permissions) {
        setAttribute(Constants.PERMISSIONS, permissions);
    }

    @Override
    public ArrayList<String> getPermissions() {
        return (ArrayList<String>)getAttribute(Constants.PERMISSIONS);
    }

    @Override
    public void setUser(User user) {
        setAttribute(Constants.ADMIN_SESSION_KEY, user);
    }

    @Override
    public User getUser() {
        return (User)getAttribute(Constants.ADMIN_SESSION_KEY);
    }

    @Override
    public void login(String userName, String password) {
        Subject currentUser = SecurityUtils.getSubject();
        if ( !currentUser.isAuthenticated() ) {
            // 登录后存放进shiro token
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
            currentUser.login(token);
        }
    }

    @Override
    public void logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
    }
}
