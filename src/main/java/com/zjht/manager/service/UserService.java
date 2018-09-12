package com.zjht.manager.service;

import com.github.pagehelper.PageInfo;
import com.zjht.manager.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserService {

    User getUserByUserName(String userName);

    /**
     * 根据Id删除
     * @param id
     * @return
     */
    int deleteById(String id);

    /**
     * 新增
     * @param user
     * @return
     */
    int insert(User user);

    /**
     * 更新
     * @param user
     * @return
     */
    int update(User user);

    /**
     * 根据用户id查询所有的权限编码{查询结果去除重复}
     * @param userId
     * @return
     */
    List<String> getPermissionsById(String userId);

    /**
     * 分页查询
     * @param user
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<User> findPage(User user, int pageNum, int pageSize);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    User findById(String id);

    /**
     * 根据登录名称查询是否存在
     * @param user
     * @return
     */
    long countByUserName(User user);

    /**
     * 修改密码
     * @param user
     * @return
     */
    int changePassword(User user);

    /**
     * 根据角色ID查询角色下的User对象
     *
     * @param roleId
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<User> getUserByRoleId(String roleId, Integer pageNum, Integer pageSize);


}
