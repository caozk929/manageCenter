package com.zjht.manager.action.admin;

import com.zjht.manager.common.dto.ResultFailDto;
import com.zjht.manager.common.dto.ResultSuccessDto;
import com.zjht.manager.common.web.WebSite;
import com.zjht.manager.common.web.session.SessionProvider;
import com.zjht.manager.entity.Menu;
import com.zjht.manager.entity.User;
import com.zjht.manager.service.LogService;
import com.zjht.manager.service.MenuService;
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
 * 菜单Controller
 * Created by zyj on 2017/8/29.
 */
@Controller
public class MenuAct {

    @Autowired
    private MenuService menuService;
    @Autowired
    private LogService logService;
    @Autowired
    private SessionProvider sessionProvider;

    @RequestMapping(value = "/menu/v_list.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String v_list(ModelMap modelMap, HttpServletRequest request) {
        //查询菜单{需要查询三级}
        WebSite.setParameters(request, modelMap);
        List<Menu> menus = menuService.listAll();
        modelMap.put("menus", menus);
        return "system/menu/list";
    }

    @ResponseBody
    @RequestMapping(value = "/menu/delete.do", method = RequestMethod.POST)
    public Object delete(String id) {
        //1.判断参数是否合法
        if (StringUtils.isBlank(id)) {
            return new ResultFailDto("参数错误...");
        }
        menuService.deleteById(id);
        logService.add("删除菜单信息，菜单Id：" + id);
        return new ResultSuccessDto("操作成功...");
    }

    /**
     * 新增修改菜单
     * @param modelMap
     * @param id
     * @return
     */
    @RequestMapping(value = "/menu/v_saveOrUpdate.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String v_saveOrUpdate(ModelMap modelMap, String id, HttpServletRequest request) {
        //查询对应的数据
        //1.读取菜单信息{暂时查询全部，后面需要根据用户的角色权限查询}
        WebSite.setParameters(request, modelMap);
        List<Menu> menus = menuService.listAllExclusiveBtn();
        modelMap.put("menus", menus);
        //2.如果id不为空则表示修改，需要查询对应的菜单
        if (StringUtils.isNotBlank(id)) {
            Menu menu = menuService.findById(id);
            modelMap.put("menu", menu);
        }
        return "system/menu/saveOrUpdate";
    }

    /**
     * 新增修改菜单，异步保存数据
     * @param menu
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/menu/saveOrUpdate.do", method = RequestMethod.POST)
    public Object saveOrUpdate(Menu menu) {
        //校验参数是否合法
        if (checkInsertParam(menu) != null) {
            return new ResultFailDto("请输入正确的参数...");
        }
        //校验特定数据是否在数据库中存在{目前只要编码唯一即可,根据ID判断是否为修改}
        if (menuService.countByCode(menu) > 0) {
            return new ResultFailDto("编码已经存在...");
        }
        User user = sessionProvider.getUser();
        menu.setUserId(user.getId());//操作用户ID
        //设置上级ID串
        setParentIds(menu);
        if (StringUtils.isNotBlank(menu.getId())) {//修改
            menuService.update(menu);
        } else {//新增
            menu.setCreateTime(new Date());
            menuService.insert(menu);
        }
        //保存用户更新操作
        logService.add("新增/修改菜单信息，菜单名称：" + menu.getName());
        return new ResultSuccessDto("操作成功...");
    }

    /**
     * 添加下级菜单{添加下级菜单，上级菜单下拉框只显示当前菜单即可，不用显示全部}
     * @param modelMap
     * @param id
     * @return
     */
    @RequestMapping(value = "/menu/v_addChildren.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_addChildren(ModelMap modelMap, String id, HttpServletRequest request) {
        WebSite.setParameters(request, modelMap);
        Menu menu = menuService.findById(id);
        modelMap.put("addChildren", menu);
        return "system/menu/saveOrUpdate";
    }

    /**
     * 设置上级ID串
     * @param menu
     */
    private void setParentIds(Menu menu) {
        String pid = menu.getParentId();
        if (!"0".equals(pid)) {//有上级按钮
            Menu pMenu = menuService.findById(pid);//上级
            if (pMenu != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(pMenu.getParentIds() + ", " + pMenu.getId());
                menu.setParentIds(sb.toString());
            }
        } else {//没有上级按钮
            menu.setParentIds("0");
        }
    }

    /**
     * 检查新增时候参数是否合法
     * @param menu
     * @return
     */
    private Object checkInsertParam(Menu menu) {
        if (menu == null) {//
            return new ResultFailDto();
        }
        if (StringUtils.isBlank(menu.getName())) {//名称不为空
            return new ResultFailDto();
        }
        if (StringUtils.isBlank(menu.getCode())) {//编码
            return new ResultFailDto();
        }
        if (menu.getSort() == null || menu.getSort() == 0) {//序号
            return new ResultFailDto();
        }
        return null;
    }

}
