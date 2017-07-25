package com.zjht.manager.dao;

import java.util.List;

import com.zjht.manager.entity.Role;
import com.zjht.manager.entity.RoleMenu;
import com.zjht.manager.entity.SysMenu;

public interface RoleMenuDao {

    /**
     * 获取列表
     * 
     * @return
     */
    public List<RoleMenu> getList(Long id);

    /**
     * 通过id查找
     * 
     * @param id
     * @return
     */
    public RoleMenu findById(Long id);
    
    /**
     * 通过roleId查找
     * 
     * @param id
     * @return
     */
    public List<RoleMenu> findByRoleId(Long roleId);

    /**
     * 通过roles查找
     * 
     * @param id
     * @return
     */
    public List<SysMenu> findByRoles(List<Role> roles);

    /**
     * 保存
     * 
     * @param bean
     * @return
     */
    public RoleMenu save(RoleMenu bean);

    /**
     * 删除
     * 
     * @param id
     */
    public void delete(Long id);

}
