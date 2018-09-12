package com.zjht.manager.dao;

import com.zjht.manager.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface RoleDao extends Mapper<Role> {

    /**
     * 根据用户Id和角色名称统计
     * @param userId
     * @param name
     * @return
     */
    long countByUserIdAndName(@Param("userId") String userId, @Param("name") String name);

}