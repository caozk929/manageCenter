package com.zjht.manager.dao;

import com.zjht.manager.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserDao extends Mapper<User> {

    User getUserByUserName(@Param("userName") String userName);

    List<String> getPermissionsById(@Param("userId") String userId);

    List<User> getUserByRoleId(@Param("roleId") String roleId);
}