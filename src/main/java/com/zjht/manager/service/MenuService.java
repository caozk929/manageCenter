package com.zjht.manager.service;

import com.zjht.manager.entity.Menu;

import java.util.List;

/**
 * 菜单MenuService
 * Created by zyj on 2017/8/29.
 */
public interface MenuService {

    /**
     * 新增
     * @param menu
     * @return
     */
    int insert(Menu menu);

    /**
     * 根据Id删除{如果一级菜单删除，那么对应的二级三级菜单都要删除}
     * @param id
     * @return
     */
    int deleteById(String id);

    /**
     * 更新
     * @param menu
     * @return
     */
    int update(Menu menu);

    /**
     * 根据编码统计{如果ID不为空，则不包含}
     * @param menu
     * @return
     */
    long countByCode(Menu menu);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    Menu findById(String id);

    /**
     * 查询全部{三级全部查询}
     * @return
     */
    List<Menu> listAll();

    /**
     * 查询全部{只查询一级二级菜单，不包含按钮级别(三级菜单)}
     * @return
     */
    List<Menu> listAllExclusiveBtn();

    /**
     * 通过菜单编码查询
     * @param code
     * @return
     */
    Menu findByCode(String code);
    /**
     * 通过菜单路径查询，主要用于查询不需要权限的请求
     * @param path 必须是唯一的
     * @return
     */
    Menu findByPath(String path);
    /**
     * 查询全部菜单Code
     * @return
     */
    List<String> listAllCode();

    /**
     * 根据用户Id级联查询菜单{只查询一二级}
     * @param userId
     * @return
     */
    List<Menu> listMenuByUserId(String userId);

    /**
     * 根据用户Id级联查询菜单{查询一二三级}
     * @param userId
     * @return
     */
    List<Menu> listAllByUserId(String userId);

}
