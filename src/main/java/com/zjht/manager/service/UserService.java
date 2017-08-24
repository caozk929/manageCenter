package com.zjht.manager.service;

import com.zjht.manager.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface UserService {

    User getUserById(Long id);

    List<User> getUserList(Map<String,Object> params, int pageIndex, int pageSize);

    User getUserByUserName(String userName);
}
