package com.zjht.manager.service.impl;

import com.zjht.manager.dao.RoleMenuDao;
import com.zjht.manager.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 目前只有查询方法，不开启事物
 * Created by zyj on 2017/9/5.
 */
@Service
public class RoleMenuServiceImpl implements RoleMenuService {

    @Autowired
    private RoleMenuDao roleMenuDao;

    /**
     * 根据角色Id查询菜单Id
     *
     * @param roleId
     * @return
     */
    @Override
    public List<String> listMenuIdByRoleId(String roleId) {
        return roleMenuDao.listMenuIdByRoleId(roleId);
    }
}
