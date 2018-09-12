package com.zjht.manager.service;

import com.github.pagehelper.PageInfo;
import com.zjht.manager.entity.Role;
import com.zjht.manager.entity.User;

import java.util.List;

/**
 * 角色
 * Created by zyj on 2017/8/29.
 */
public interface RoleService {

    /**
     * 新增
     *
     * @param role
     * @return
     */
    int insert(Role role);

    /**
     * 根据Id删除
     *
     * @param id
     * @return
     */
    int deleteById(String id);

    /**
     * 更新
     *
     * @param role
     * @return
     */
    int update(Role role);

    /**
     * 根据角色ID查询数据
     *
     * @param id
     * @return
     */
    Role findById(String id);

    /**
     * 查询全部
     *
     * @return
     */
    List<Role> findAll();

    /**
     * 根据实体类中的属性值查询，条件使用等号
     *
     * @param role
     * @return
     */
    List<Role> listAllByCondition(Role role);

    /**
     * 分页查询，pageSize传递的时候如果修改了
     *
     * @param role
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Role> findPage(Role role, int pageNum, int pageSize);

    /**
     * 根据名称统计，ID不为空，则包括当前角色
     *
     * @param role
     * @return
     */
    long countByName(Role role);

    /**
     * 根据用户Id和角色名称统计
     *
     * @param userId
     * @param name
     * @return
     */
    long countByUserIdAndName(String userId, String name);



}
