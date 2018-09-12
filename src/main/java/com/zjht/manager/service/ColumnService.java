package com.zjht.manager.service;

import com.github.pagehelper.PageInfo;
import com.zjht.manager.entity.Column;

import java.util.List;

/**
 * 栏目Service
 * Created by vip on 2017/9/15.
 */
public interface ColumnService {

    /**
     * 分页查询
     * @param column
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Column> findPage(Column column, int pageNum, int pageSize);

    /**
     * 根据ID查找
     * @param id
     * @return
     */
    Column findById(String id);

    /**
     * 修改
     * @param column
     * @return
     */
    int update(Column column);

    /**
     * 新增
     * @param column
     * @return
     */
    int add(Column column);

    /**
     * 根据Id删除，同时需要update文章模块关联的Id
     * @param id
     * @return
     */
    int deleteById(String id);

    /**
     * 根据Ids删除，同时需要update文章模块关联的Id
     * @param ids
     * @return
     */
    int deleteByIds(String[] ids);

    /**
     * 查询全部
     * @param column
     * @return
     */
    List<Column> findAll(Column column);

}
