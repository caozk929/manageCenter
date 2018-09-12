package com.zjht.manager.dao;

import com.zjht.manager.entity.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserRoleDao extends Mapper<UserRole> {

    /**
     * 根据用户Id查询角色Id
     * @param userId
     * @return
     */
    List<String> listRoleIdByUserId(@Param("userId") String userId);

}