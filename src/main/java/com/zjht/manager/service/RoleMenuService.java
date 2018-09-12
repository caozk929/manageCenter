package com.zjht.manager.service;

import java.util.List;

/**
 * Created by zyj on 2017/9/5.
 */
public interface RoleMenuService {

    /**
     * 根据角色Id查询菜单Id
     * @param roleId
     * @return
     */
    List<String> listMenuIdByRoleId(String roleId);

}
