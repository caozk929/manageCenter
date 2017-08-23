package com.zjht.manager.service.impl;

import com.zjht.manager.dao.UserDao;
import com.zjht.manager.entity.User;
import com.zjht.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    public List<User> getUserList(Map<String,Object> params, int pageIndex, int pageSize) {
        return userDao.getUsersList(params, "t.*", "regdate desc", pageIndex, pageSize);
    }
}
