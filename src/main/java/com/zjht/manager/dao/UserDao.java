package com.zjht.manager.dao;

import com.zjht.manager.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserDao {
    int insert(User user);
    int update(User user);
    int delete(String userId);
    User getUserById(long userId);
    List<User> getUsersList(
            @Param(value = "params") Map<String, Object> params,
            @Param(value = "fields") String fields,
            @Param(value = "orderby") String orderby,
            @Param(value = "pageIndex")  int pageIndex,
            @Param(value = "pageSize") int pageSize
    );
}
