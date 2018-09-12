package com.zjht.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.manager.dao.UserDao;
import com.zjht.manager.dao.UserRoleDao;
import com.zjht.manager.entity.User;
import com.zjht.manager.entity.UserRole;
import com.zjht.manager.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional//开启事物
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public User getUserByUserName(String userName) {
        return userDao.getUserByUserName(userName);
    }

    @Override
    public int deleteById(String id) {
        /**
         * 1.删除用户和角色关系表
         * 2.删除用户信息
         */
        deleteUserRoleByUserId(id);
        return userDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(User user) {
        /**
         * 1.保存用户角色关系
         * 2.保存用户信息
         */
        int result = userDao.insert(user);
        saveUserRole(user);
        return result;
    }

    @Override
    public int update(User user) {
        /**
         * 1.修改用户信息
         * 2.删除用户角色关系
         * 2.1重新保存用户角色关系
         */
        int result = userDao.updateByPrimaryKeySelective(user);//修改
        deleteUserRoleByUserId(user.getId());//删除
        saveUserRole(user);//保存
        return result;
    }

    /**
     * 修改密码
     *
     * @param user
     * @return
     */
    @Override
    public int changePassword(User user) {
        return userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    public List<String> getPermissionsById(String userId) {
        return userDao.getPermissionsById(userId);
    }

    /**
     * 分页查询
     *
     * @param user
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<User> findPage(User user, int pageNum, int pageSize) {
        //1.设置分页查询参数
        PageHelper.startPage(pageNum, pageSize);
        //2.封装查询参数
        //2.1创建条件对象
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        //2.2判断查询条件
        if (StringUtils.isNotBlank(user.getUsername())) {//登录账号
            criteria.andLike("username", "%" + user.getUsername() + "%");
        }
        return new PageInfo<User>(userDao.selectByExample(example));
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public User findById(String id) {
        return userDao.selectByPrimaryKey(id);
    }

    /**
     * 根据登录名称查询是否存在
     *
     * @param user
     * @return
     */
    @Override
    public long countByUserName(User user) {
        //1.封装查询参数
        //1.1创建条件对象
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        //1.2判断查询条件
        criteria.andEqualTo("username", user.getUsername());//根据登录名称查询
        if (StringUtils.isNotBlank(user.getId())) {//修改不包含自己
            criteria.andNotEqualTo("id", user.getId());
        }
        return userDao.selectCountByExample(example);
    }

    /**
     * 根据用户ID删除用户-角色关系表
     * @param userId
     */
    private void deleteUserRoleByUserId(String userId) {
        Example example = new Example(UserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        userRoleDao.deleteByExample(example);
    }

    /**
     * 保存用户-角色关系表
     * @param user
     */
    private void saveUserRole(User user) {
        if (user.getRoleIds() != null && user.getRoleIds().length > 0) {
            for(String roleId : user.getRoleIds()) {
                UserRole ur = new UserRole();
                ur.setRoleId(roleId);
                ur.setUserId(user.getId());
                userRoleDao.insert(ur);
            }
        }
    }

    /**
     * 根据角色ID查询角色下的User对象
     *
     * @param roleId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<User> getUserByRoleId(String roleId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<User>(userDao.getUserByRoleId(roleId));
    }
}
