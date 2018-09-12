package com.zjht.manager.action.admin;

import com.zjht.manager.common.dto.ResultDto;
import com.zjht.manager.common.dto.ResultFailDto;
import com.zjht.manager.common.dto.ResultSuccessDto;
import com.zjht.manager.common.web.Constants;
import com.zjht.manager.common.web.WebSite;
import com.zjht.manager.common.web.annotation.NoNeedAuth;
import com.zjht.manager.common.web.session.SessionProvider;
import com.zjht.manager.entity.Menu;
import com.zjht.manager.entity.User;
import com.zjht.manager.service.LogService;
import com.zjht.manager.service.MenuService;
import com.zjht.manager.service.RoleService;
import com.zjht.manager.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;

@Controller
public class IndexAct {
    @Autowired
    private RedisTemplate<Serializable, Object> redisTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private SessionProvider sessionProvider;
    @Autowired
    private RoleService roleService;

    //日志记录
    private static Logger logger = LoggerFactory.getLogger(IndexAct.class);

    @RequestMapping(value = "/index/index.html")
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {


        User user = sessionProvider.getUser();
        ValueOperations operations = redisTemplate.opsForValue();
        User u = new User();
        u.setUsername("xiaohei");
        u.setId("1");

        operations.set("userId" + u.getId(), u);
        final String s = String.valueOf(2);

        System.out.println("hahdk");
        Object o = operations.get("userId" + u.getId());
        model.addAttribute("user",user );
        return "index/index";
    }

    @NoNeedAuth
    @RequestMapping(value = "/index/login.html")
    public String loginHtml(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        WebSite.setParameters(request, model);
        return "index/login";
    }

    @NoNeedAuth
    @RequestMapping(value = "/index/home.html")
    public String home(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        //1.在session中获取用户的菜单权限列表数据
        List<Menu> menus = (List<Menu>)sessionProvider.getAttribute(Constants.MENU_LIST);
        if (menus == null) {//为空重新查询
            User user = sessionProvider.getUser();
            //1.查询用户所属的角色中是否包含超级管理员角色，如果包含则查询全部菜单即可
            if (roleService.countByUserIdAndName(user.getId(), "'" + Constants.ADMINISTRATOR + "'") > 0) {
                menus = menuService.listAllExclusiveBtn();
            } else {//2.如果用户不是超级管理员，则查询对于的菜单
                menus = menuService.listMenuByUserId(user.getId());
            }
        }
        model.put("menus", menus);
        return "index/home";
    }

    @NoNeedAuth
    @RequestMapping(value = "/index/login.do")
    public @ResponseBody ResultDto login(String userName, String password, ModelMap model) {
        if(StringUtils.isBlank(userName)){
            return new ResultFailDto("用户名为空");
        }
        if(StringUtils.isBlank(password)){
            return new ResultFailDto("密码为空");
        }
        User user = userService.getUserByUserName(userName);
        if(user == null){
            return new ResultFailDto("用户不存在");
        }
        //校验用户名密码
        if(!user.getPassword().equals(DigestUtils.md5Hex(password))){
            return new ResultFailDto("用户名或密码不正确");
        }
        //用户登录
        sessionProvider.login(user.getUsername(), user.getPassword());
        //将用户信息存入session
        sessionProvider.setUser(user);
        logService.add("登陆");
        return new ResultSuccessDto("登陆成功");
    }

    @NoNeedAuth
    @RequestMapping(value = "/index/logout.do")
    public String logout(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        //退出登录
        sessionProvider.logout();
        return "index/login";
    }

}
