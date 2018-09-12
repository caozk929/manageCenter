package com.zjht.manager.service.impl;

import com.zjht.manager.common.web.Constants;
import com.zjht.manager.dao.MenuDao;
import com.zjht.manager.entity.Menu;
import com.zjht.manager.service.MenuService;
import com.zjht.manager.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单MenuServiceImpl
 * Created by zyj on 2017/8/29.
 */
@Service
@Transactional//注解事物
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Override
    public int insert(Menu menu) {
        return menuDao.insertSelective(menu);
    }

    @Override
    public int deleteById(String id) {
        //根据主键删除下级菜单
        List<String> list = menuDao.listChildrenIdByPid(id);//查询全部下级
        if (list.size() > 0) {
            String ids = StringUtil.listToStr(list);
            List<String> btnIds = menuDao.listChildrenIdByPid(ids);
            if (btnIds.size() > 0) {
                list.addAll(btnIds);//合并
            }
            //执行删除封装条件
            Example example = new Example(Menu.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andIn("id", list);
            menuDao.deleteByExample(example);
        }
        return menuDao.deleteByPrimaryKey(id);//删除自己
    }

    @Override
    public int update(Menu menu) {
        return menuDao.updateByPrimaryKeySelective(menu);
    }

    @Override
    public long countByCode(Menu menu) {
        Example example = new Example(Menu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("code", menu.getCode());
        if (StringUtils.isNotBlank(menu.getId())) {
            criteria.andNotEqualTo("id", menu.getId());
        }
        return menuDao.selectCountByExample(example);
    }

    @Override
    public Menu findById(String id) {
        return menuDao.selectByPrimaryKey(id);
    }

    private List<Menu> listByPid(String pid, List<String> status) {
        Example example = new Example(Menu.class);
        Example.Criteria criteria = example.createCriteria();
        //封装查询条件
        criteria.andEqualTo("parentId", pid);
        criteria.andIn("status", status);
        example.setOrderByClause("sort");//根据sort排序
        return menuDao.selectByExample(example);
    }

    @Override
    public List<Menu> listAll() {
        List<String> status = new ArrayList<String>();
        status.add(String.valueOf(Constants.ZERO));
        status.add(String.valueOf(Constants.ONE));
        //后面需要改为根据用户的角色Id查询
        List<Menu> list = listByPid(String.valueOf(Constants.ZERO), status);//查询一级菜单
        if (list.size() > 0) {
            for (Menu menu : list) {
                List<Menu> childrens = listByPid(menu.getId(), status);//二级菜单
                if (childrens.size() > 0) {
                    for (Menu children : childrens) {
                        List<Menu> btns = listByPid(children.getId(), status);//三级菜单{按钮}
                        children.setChildren(btns);//二级菜单中保存按钮
                    }
                }
                menu.setChildren(childrens);//一级菜单中保存二级菜单
            }
        }
        return list;
    }

    /**
     * 查询全部，停用的不查询{只查询一级二级菜单，不包含按钮级别(三级菜单)}
     *
     * @return
     */
    @Override
    public List<Menu> listAllExclusiveBtn() {
        List<String> status = new ArrayList<String>();
        status.add(String.valueOf(Constants.ONE));
        //后面需要改为根据用户的角色Id查询
        List<Menu> list = listByPid("0", status);//查询一级菜单
        if (list.size() > 0) {
            for (Menu menu : list) {
                List<Menu> childrens = listByPid(menu.getId(), status);//二级菜单
                menu.setChildren(childrens);//一级菜单中保存二级菜单
            }
        }
        return list;
    }

    @Override
    public Menu findByCode(String code) {
        Menu record = new Menu();
        record.setCode(code);
        return menuDao.selectOne(record);
    }

    @Override
    public Menu findByPath(String path) {
        Menu record = new Menu();
        record.setPath(path);
        return menuDao.selectOne(record);
    }

    /**
     * 查询全部菜单Code
     *
     * @return
     */
    @Override
    public List<String> listAllCode() {
        return menuDao.listAllCode();
    }

    /**
     * 根据用户Id级联查询菜单
     *
     * @param userId
     * @return
     */
    @Override
    public List<Menu> listMenuByUserId(String userId) {
        List<Menu> result = menuDao.listMenuByUserId(userId, "0");
        if (result.size() > 0) {
            for (Menu menu : result) {
                //级联查询
                List<Menu> childrens = menuDao.listMenuByUserId(userId, menu.getId());//二级菜单
                menu.setChildren(childrens);//一级菜单中保存二级菜单
            }
        }
        return result;
    }

    /**
     * 根据用户Id级联查询菜单{查询一二三级}
     *
     * @param userId
     * @return
     */
    @Override
    public List<Menu> listAllByUserId(String userId) {
        List<Menu> result = menuDao.listMenuByUserId(userId, "0");
        if (result.size() > 0) {
            for (Menu menu : result) {
                //级联查询
                List<Menu> childrens = menuDao.listMenuByUserId(userId, menu.getId());//二级菜单
                if (childrens.size() > 0) {
                    for (Menu children : childrens) {
                        List<Menu> btns = menuDao.listMenuByUserId(userId, children.getId());//三级菜单{按钮}
                        children.setChildren(btns);//二级菜单中保存按钮
                    }
                }
                menu.setChildren(childrens);//一级菜单中保存二级菜单
            }
        }
        return result;
    }
}
