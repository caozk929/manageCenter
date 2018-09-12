package com.zjht.manager.common.web.shiro;

import com.zjht.manager.common.web.session.SessionProvider;
import com.zjht.manager.entity.User;
import com.zjht.manager.service.UserService;

import com.zjht.manager.common.web.Constants;
import com.zjht.manager.service.MenuService;
import com.zjht.manager.service.RoleService;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义Shiro的Realm
 */
public class CustomRealm extends AuthorizingRealm{

    @Autowired
    private UserService userService;

    @Autowired
    private SessionProvider sessionProvider;

    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();

        User user = userService.getUserByUserName(username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        //1.查询登录用户是否包含超级管理员角色
        List<String> pers = null;
        if (roleService.countByUserIdAndName(user.getId(), "'" + Constants.ADMINISTRATOR + "'") > 0) {//拥有超级管理员角色
            //1.1如果是超级管理员则直接查询全部
            pers = menuService.listAllCode();
        } else {
            //1.2如果不是超级管理员则查询对应的权限，需要去重复
            pers = userService.getPermissionsById(user.getId());
        }

        if (pers != null && !pers.isEmpty()) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            for (String each : pers) {
                //将权限资源添加到用户信息中
                info.addStringPermission(each);
            }
            //将用户权限放入session
            sessionProvider.setPermissions((ArrayList<String>)pers);
            return info;
        }

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();
        if (username != null && !"".equals(username)) {
            User user = userService.getUserByUserName(username);
            if (user != null) {
                return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
            }
        }
        return null;
    }
}
