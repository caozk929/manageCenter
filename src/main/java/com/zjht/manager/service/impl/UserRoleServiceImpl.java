package com.zjht.manager.service.impl;

import com.zjht.manager.dao.UserRoleDao;
import com.zjht.manager.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by vip on 2017/9/7.
 */
@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleDao userRoleDao;

    /**
     * 根据用户Id查询角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<String> listRoleIdByUserId(String userId) {
        return userRoleDao.listRoleIdByUserId(userId);
    }
}
