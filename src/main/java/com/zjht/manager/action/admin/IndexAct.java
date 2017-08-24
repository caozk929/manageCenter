package com.zjht.manager.action.admin;

import com.zjht.manager.entity.User;
import com.zjht.manager.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

@Controller
public class IndexAct {
    @Autowired
    private RedisTemplate<Serializable, Object> redisTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityManager securityManager;
    //日志记录
    private static Logger logger = LoggerFactory.getLogger(IndexAct.class);

    @RequestMapping(value = "/index/index.html")
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        ValueOperations operations = redisTemplate.opsForValue();
        User u = new User();
        u.setUserName("xiaohei");
        u.setId(1L);
        operations.set("userId" + u.getId(), u);
        final String s = String.valueOf(2);

        Object o = operations.get("userId" + u.getId());
        return "index/index";
    }

    @RequestMapping(value = "/index/login.html")
    public String loginHtml(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return "index/login";
    }

    @RequestMapping(value = "/index/login.do")
    public String login(String userName, String password, ModelMap model) {
        if(StringUtils.isBlank(userName)){
            return "";
        }
        if(StringUtils.isBlank(password)){
            return "";
        }
        User user = userService.getUserByUserName(userName);
        if(user == null){
            logger.warn("用户不存在");
            return "";
        }
        if(!user.getUserPwd().equals(password)){
            logger.warn("用户名或密码不正确");
            return "";
        }
        SecurityUtils.getSecurityManager().logout(SecurityUtils.getSubject());
        // 登录后存放进shiro token
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getUserPwd());
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);

        return "index/home";
    }
    @RequestMapping(value = "/index/logout.do")
    public String logout(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        //退出登录
        securityManager.logout(SecurityUtils.getSubject());
        return "index/login";
    }

}
