package com.zjht.manager.action.admin;

import com.github.pagehelper.PageInfo;
import com.zjht.manager.common.dto.ResultFailDto;
import com.zjht.manager.common.dto.ResultSuccessDto;
import com.zjht.manager.common.web.Constants;
import com.zjht.manager.common.web.WebSite;
import com.zjht.manager.common.web.session.SessionProvider;
import com.zjht.manager.entity.Menu;
import com.zjht.manager.entity.Role;
import com.zjht.manager.entity.User;
import com.zjht.manager.service.*;
import com.zjht.manager.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Role
 * Created by zyj on 2017/8/29.
 */
@Controller
public class RoleAct {

    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private LogService logService;
    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private UserService userService;
    @Autowired
    private SessionProvider sessionProvider;

    private static final Logger logger = LoggerFactory.getLogger(RoleAct.class);

    @RequestMapping(value = "/role/v_list.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_list(ModelMap modelMap, HttpServletRequest request, Role role, Integer pageNum) {
        //1.根据分页参数查询数据
        WebSite.setParameters(request, modelMap);
        PageInfo<Role> pageInfo = roleService.findPage(role,  PageUtil.cpn(pageNum), CookieUtils.getPageSize(request));
        modelMap.put("pageInfo", pageInfo);
        modelMap.put("role", role);//角色，查询数据回显
        return "system/role/list";
    }

    @RequestMapping(value = "/role/v_saveOrUpdate.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_saveOrUpdate(ModelMap modelMap, HttpServletRequest request, String id) {
        WebSite.setParameters(request, modelMap);
        //查询菜单
        List<Menu> menus = null;
        User user = sessionProvider.getUser();
        //1.查询用户所属的角色中是否包含超级管理员角色，如果包含则查询全部菜单即可
        if (roleService.countByUserIdAndName(user.getId(), "'" + Constants.ADMINISTRATOR + "'") > 0) {
            menus = menuService.listAll();
        } else {//2.如果用户不是超级管理员，则查询对应的菜单
            menus = menuService.listAllByUserId(user.getId());
        }
        modelMap.put("menus", menus);
        //根据ID查询数据
        if (StringUtils.isNotBlank(id)) {
            //1.查询角色信息
            Role role = roleService.findById(id);
            modelMap.put("role", role);
            //2.查询角色的权限菜单ID
            List<String> menuIds = roleMenuService.listMenuIdByRoleId(id);
            modelMap.put("menuIds", StringUtil.listToStr(menuIds));
        }
        return "system/role/saveOrUpdate";
    }

    @ResponseBody
    @RequestMapping(value = "/role/saveOrUpdate.do", method = RequestMethod.POST)
    public Object saveOrUpdate(Role role) {
        if (checkRoleParam(role)) {
            return new ResultFailDto("参数错误...");
        }
        //判断名称是否重复
        if (roleService.countByName(role) > 0) {
            return new ResultFailDto("角色名称被占用...");
        }
        if (StringUtils.isNotBlank(role.getId())) {//修改
            roleService.update(role);
        } else {//新增
            roleService.insert(role);
        }
        //保存用户更新操作
        logService.add("新增/修改角色信息，角色名称：" + role.getName());
        return new ResultSuccessDto("操作成功...");
    }

    @ResponseBody
    @RequestMapping(value = "/role/delete.do", method = RequestMethod.POST)
    public Object delete(String id) {
        if (StringUtils.isBlank(id)) {
            return new ResultFailDto("参数错误...");
        }
        roleService.deleteById(id);
        logService.add("删除角色信息，角色Id：" + id);
        return new ResultSuccessDto("操作成功");
    }

    /**
     * 获取角色下所属用户
     * @param modelMap
     * @param request
     * @param id
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/role/o_userList.do",method = {RequestMethod.POST, RequestMethod.GET})
    public String o_userList(ModelMap modelMap, HttpServletRequest request, String id, Integer pageNum){
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("参数错误...");
        }

        WebSite.setParameters(request, modelMap);

        PageInfo<User> pageInfo = userService.getUserByRoleId(id,PageUtil.cpn(pageNum), CookieUtils.getPageSize(request));
        modelMap.put("pageInfo",pageInfo);
        modelMap.put("role",roleService.findById(id));

        return "system/role/userList";
    }

    /**
     * 导出角色所属用户信息
     *
     * @param modelMap
     * @param request
     * @param id
     * @param name
     * @param response
     */
    @RequestMapping(value = "/role/o_exportUsers.do",method = {RequestMethod.POST, RequestMethod.GET})
    public void o_exportUsers(ModelMap modelMap, HttpServletRequest request, String id,String name, HttpServletResponse response){
        if (StringUtils.isBlank(id)) {
          throw new IllegalArgumentException("参数错误...");
        }

        WebSite.setParameters(request, modelMap);

        PageInfo<User> pageInfo = userService.getUserByRoleId(id,1, 2147483646);
        List<List<String>> list = new ArrayList<>();
        for (int i = 0; i < pageInfo.getList().size(); i++) {
            List<String> a = new ArrayList<>();
            a.add(pageInfo.getList().get(i).getNickName());
            a.add(pageInfo.getList().get(i).getUsername());
            a.add(pageInfo.getList().get(i).getMobile());
            a.add(pageInfo.getList().get(i).getEmail());
            a.add(DateTimeUtils.formatDateStr(pageInfo.getList().get(i).getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
            list.add(a);
        }

        try {
            String[] heads = {"昵称","用户名称","电话","电子邮件","创建时间"};
            String fileName = new String(name.getBytes("iso8859-1"), "utf-8")+DateTimeUtils.formatDateStr(new Date(),"yyyyMMdd")+".xls";
            ExcelUtils.exportExcel(fileName,request,response,heads,list);
            logService.add("导出角色信息，角色Id：" + id);
        } catch (IOException e) {
            logger.error("[method:o_exportUsers,meg:导出角色信息异常，角色id："+id+"]",e);
        }

    }

    /**
     * 校验Role参数
     * @param role
     * @return
     */
    private boolean checkRoleParam(Role role) {
        if (role == null) {
            return true;
        }
        if (StringUtils.isBlank(role.getName())) {
            return true;
        }
        return false;
    }


}
