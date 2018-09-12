package com.zjht.manager.action.admin;

import com.github.pagehelper.PageInfo;
import com.zjht.manager.common.dto.ResultDto;
import com.zjht.manager.common.dto.ResultFailDto;
import com.zjht.manager.common.dto.ResultSuccessDto;
import com.zjht.manager.common.web.Constants;
import com.zjht.manager.common.web.WebSite;
import com.zjht.manager.common.web.annotation.NoNeedAuth;
import com.zjht.manager.common.web.session.SessionProvider;
import com.zjht.manager.entity.ApiChannel;
import com.zjht.manager.entity.Role;
import com.zjht.manager.entity.User;
import com.zjht.manager.service.*;
import com.zjht.manager.util.CookieUtils;
import com.zjht.manager.util.PageUtil;
import com.zjht.manager.util.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 用户
 * Created by zyj on 2017/8/29.
 */
@Controller
public class UserAct {

    @Autowired
    private UserService userService;//用户
    @Autowired
    private LogService logService;//日志
    @Autowired
    private RoleService roleService;//角色
    @Autowired
    private UserRoleService userRoleService;//用户角色
    @Autowired
    private ChannelService channelService;//渠道
    @Autowired
    private SessionProvider sessionProvider;

    private static final String PASSWORD ="123456";//默认密码设置

    @RequestMapping(value = "/user/v_list.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String v_list(ModelMap modelMap, User user, Integer pageNum, HttpServletRequest request) {
        //1.分页查询
        WebSite.setParameters(request, modelMap);
        PageInfo<User> pageInfo = userService.findPage(user, PageUtil.cpn(pageNum), CookieUtils.getPageSize(request));
        //1.1封装渠道名称
        for (User u : pageInfo.getList()) {
            if (StringUtils.isNotBlank(u.getChannelId())) {
                u.setChannelName(channelService.getNameById(u.getChannelId()));
            }
        }
        modelMap.put("pageInfo", pageInfo);
        //2.查询条件保存数据回显
        modelMap.put("user", user);
        return "system/user/list";
    }

    @ResponseBody
    @RequestMapping(value = "/user/delete.do", method = RequestMethod.POST)
    public Object delete(String id) {
        if (StringUtils.isBlank(id)) {
            return new ResultFailDto("参数错误...");
        }
        userService.deleteById(id);
        //保存操作信息
        logService.add("删除用户信息，用户Id：" + id);
        return new ResultSuccessDto("操作成功");
    }

    @RequestMapping(value = "/user/v_saveOrUpdate.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_saveOrUpdate(ModelMap modelMap, HttpServletRequest request, String id) {
        //0.封装请求参数
        WebSite.setParameters(request, modelMap);
        //1.1创建查询条件
        Role role = new Role();
        role.setStatus(Constants.ONE);
        //1.2查询角色
        List<Role> roles = roleService.listAllByCondition(role);
        modelMap.put("roles", roles);
        //2.编辑则查询用户信息
        if (StringUtils.isNotBlank(id)) {
            //2.1查询用户信息
            User user = userService.findById(id);
            modelMap.put("user", user);
            //2.2查询用户角色
            List<String> roleIds = userRoleService.listRoleIdByUserId(id);
            modelMap.put("roleIds", StringUtil.listToStr(roleIds));
        }
        //3.查询渠道信息{获取状态是正常的渠道}
        ApiChannel channel = new ApiChannel();
        channel.setStatus(1);
        modelMap.put("channels", channelService.getList(channel));
        return "system/user/saveOrUpdate";
    }

    @ResponseBody
    @RequestMapping(value = "/user/saveOrUpdate.do", method = RequestMethod.POST)
    private Object saveOrUpdate(User user) {
        if (checkUserParam(user)) {
            return new ResultFailDto("参数错误...");
        }
        //判断登录名称是否存在
        if (userService.countByUserName(user) > 0) {
            return new ResultFailDto("用户名称被占用...");
        }
        user.setType(1);//默认后台管理账户
        if (StringUtils.isNotBlank(user.getId())) {//更新
            user.setUpdateTime(new Date());//最后修改时间
            userService.update(user);
        } else {//新增
            user.setCreateTime(new Date());//新增时间
            if (StringUtils.isEmpty(user.getPassword())) {//密码为空，设置默认密码
                user.setPassword(PASSWORD);
            }
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));//MD5加密
            userService.insert(user);
        }
        //保存操作信息
        logService.add("新增/修改用户信息，用户名称：" + user.getUsername());
        return new ResultSuccessDto("操作成功");
    }

    @NoNeedAuth
    @RequestMapping(value = "/user/v_changePassword.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_changePassword() {
        return "system/user/changePassword";
    }

    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/user/changePassword.do", method = RequestMethod.POST)
    public Object changePassword(String oldpwd, String newpwd) {
        User user = sessionProvider.getUser();
        //1.旧密码加密和原密码匹配
        if (!DigestUtils.md5Hex(oldpwd).equals(user.getPassword())) {
            return new ResultFailDto("原密码错误...");
        }
        User u = new User();
        u.setId(user.getId());
        u.setPassword(DigestUtils.md5Hex(newpwd));
        userService.changePassword(u);
        user.setPassword(DigestUtils.md5Hex(newpwd));//修改缓存密码
        //保存操作信息
        logService.add("修改用户密码，用户名称：" + user.getUsername());
        return new ResultSuccessDto("操作成功...");
    }

    @ResponseBody
    @RequestMapping(value = "/user/o_resetPassWord.do", method = RequestMethod.POST)
    public ResultDto o_resetPassWord(String id) {
        if (StringUtils.isBlank(id)) {
            return new ResultFailDto("参数错误...");
        }
        User u = userService.findById(id);
        u.setPassword(DigestUtils.md5Hex(PASSWORD));//重置为默认密码
        userService.update(u);
        //保存操作信息
        logService.add("重置用户密码，用户Id：" + id);
        return new ResultSuccessDto("操作成功");
    }

    /**
     * 校验用户参数
     * @param user
     * @return
     */
    private boolean checkUserParam(User user) {
        if (user == null) {
            return true;
        }
        //登录账号
        if (StringUtils.isBlank(user.getUsername())) {
            return true;
        }
        return false;
    }


}
