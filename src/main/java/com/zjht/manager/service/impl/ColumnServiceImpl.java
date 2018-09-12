package com.zjht.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.manager.dao.ArticleDao;
import com.zjht.manager.dao.ColumnDao;
import com.zjht.manager.entity.Column;
import com.zjht.manager.service.ColumnService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;

/**
 * TODO: 栏目ServiceImpl
 * Created by vip on 2017/9/15.
 */
@Service
@Transactional//开启事务
public class ColumnServiceImpl implements ColumnService {

    @Autowired
    private ColumnDao columnDao;
    @Autowired
    private ArticleDao articleDao;

    /**
     * 分页查询
     *
     * @param column
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Column> findPage(Column column, int pageNum, int pageSize) {
        //1.设置分页属性
        PageHelper.startPage(pageNum, pageSize);
        //2.封装查询条件
        Example example = new Example(Column.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(column.getTitle())) {
            criteria.andLike("title", "%" + column.getTitle() + "%");
        }
        if(StringUtils.isNotBlank(column.getChannelId())){
            criteria.andEqualTo("channelId",column.getChannelId());
        }
        return new PageInfo<Column>(columnDao.selectByExample(example));
    }

    /**
     * 根据ID查找
     *
     * @param id
     * @return
     */
    @Override
    public Column findById(String id) {
        return columnDao.selectByPrimaryKey(id);
    }

    /**
     * 修改
     *
     * @param column
     * @return
     */
    @Override
    public int update(Column column) {
        return columnDao.updateByPrimaryKeySelective(column);
    }

    /**
     * 新增
     *
     * @param column
     * @return
     */
    @Override
    public int add(Column column) {
        return columnDao.insert(column);
    }

    /**
     * 根据Id删除，同时需要update文章模块关联的Id
     *
     * @param id
     * @return
     */
    @Override
    public int deleteById(String id) {
        /**
         * 1.修改文章表的栏目Id为null
         * 2.删除栏目
         */
        articleDao.updateColumcIdToNullByColumnId(id);
        return columnDao.deleteByPrimaryKey(id);
    }

    /**
     * 根据Ids删除，同时需要update文章模块关联的Id
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteByIds(String[] ids) {
        /**
         * 1.修改文章表的栏目Id为null
         * 2.删除栏目
         */
        for (String id : ids) {
            articleDao.updateColumcIdToNullByColumnId(id);
        }
        //封装条件对象
        Example example = new Example(Column.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", Arrays.asList(ids));
        return columnDao.deleteByExample(example);
    }

    /**
     * 查询全部
     *
     * @param column
     * @return
     */
    @Override
    public List<Column> findAll(Column column) {
        Example example = new Example(Column.class);
        Example.Criteria criteria = example.createCriteria();
        if (column.getStatus() != null) {
            criteria.andEqualTo("status", column.getStatus());
        }
        return columnDao.selectByExample(example);
    }
}
