package com.zjht.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.manager.dao.RoleDao;
import com.zjht.manager.dao.RoleMenuDao;
import com.zjht.manager.dao.UserDao;
import com.zjht.manager.entity.Role;
import com.zjht.manager.entity.RoleMenu;
import com.zjht.manager.entity.User;
import com.zjht.manager.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 角色
 * Created by zyj on 2017/8/29.
 */
@Service
@Transactional//开启事物
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleMenuDao roleMenuDao;

    @Override
    public int insert(Role role) {
        /**
         * 1.保存角色获取角色的Id
         * 2.保存角色和菜单的关系{}
         */
        int result = roleDao.insert(role);
        saveRoleMenu(role);
        return result;
    }

    @Override
    public int deleteById(String id) {
        /**
         * 1.删除角色和菜单关系
         * 2.删除角色信息
         */
        deleteRoleMenuByRoleId(id);
        return roleDao.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Role role) {
        /**
         * 1.保存角色获取角色的Id
         * 2.删除之前的中间关系记录{根据roleId删除}
         * 3.保存角色和菜单的关系{}
         */
        int result = roleDao.updateByPrimaryKeySelective(role);
        //删除中间关系表
        deleteRoleMenuByRoleId(role.getId());
        //重新保存新的数据
        saveRoleMenu(role);
        return result;
    }

    /**
     * 根据角色ID查询数据
     *
     * @param id
     * @return
     */
    @Override
    public Role findById(String id) {
        return roleDao.selectByPrimaryKey(id);
    }

    /**
     * 查询全部
     *
     * @return
     */
    @Override
    public List<Role> findAll() {
        return roleDao.selectAll();
    }

    /**
     * 根据实体类中的属性值查询，条件使用等号
     *
     * @param role
     * @return
     */
    @Override
    public List<Role> listAllByCondition(Role role) {
        return roleDao.select(role);
    }

    /**
     * 分页查询
     *
     * @param role
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Role> findPage(Role role, int pageNum, int pageSize) {
        //封装分页参数
        PageHelper.startPage(pageNum, pageSize);
        //封装查询条件
        Example example = new Example(Role.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(role.getName())) {//名称搜索
            criteria.andLike("name", "%" + role.getName() + "%");
        }
        return new PageInfo<Role>(roleDao.selectByExample(example));
    }

    /**
     * 根据名称统计，ID不为空，则包括当前角色
     *
     * @param role
     * @return
     */
    @Override
    public long countByName(Role role) {
        Example example = new Example(Role.class);
        Example.Criteria criteria = example.createCriteria();
        //名称条件
        criteria.andEqualTo("name", role.getName());
        //修改则不包含包含当前
        if (StringUtils.isNotBlank(role.getId())) {
            criteria.andNotEqualTo("id", role.getId());
        }
        return roleDao.selectCountByExample(example);
    }

    /**
     * 根据用户Id和角色名称统计
     *
     * @param userId
     * @param name
     * @return
     */
    @Override
    public long countByUserIdAndName(String userId, String name) {
        return roleDao.countByUserIdAndName(userId, name);
    }



    /**
     * 根据角色Id删除角色-菜单权限表
     *
     * @param roleId
     */
    private void deleteRoleMenuByRoleId(String roleId) {
        //创建条件对象
        Example example = new Example(RoleMenu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId", roleId);
        roleMenuDao.deleteByExample(example);
    }

    /**
     * 保存角色-菜单权限表
     *
     * @param role
     */
    private void saveRoleMenu(Role role) {
        if (role.getMenuIds() != null && role.getMenuIds().length > 0) {
            for (String menuId : role.getMenuIds()) {
                RoleMenu rm = new RoleMenu();
                rm.setRoleId(role.getId());
                rm.setMenuId(menuId);
                roleMenuDao.insert(rm);
            }
        }
    }

}
