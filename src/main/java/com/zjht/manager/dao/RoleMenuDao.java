package com.zjht.manager.dao;

import com.zjht.manager.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface RoleMenuDao extends Mapper<RoleMenu> {

    /**
     * 根据角色Id查询菜单Id
     * @param roleId
     * @return
     */
    List<String> listMenuIdByRoleId(@Param("roleId") String roleId);

}