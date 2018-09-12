package com.zjht.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.manager.common.web.Constants;
import com.zjht.manager.dao.ArticleDao;
import com.zjht.manager.entity.Article;
import com.zjht.manager.service.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;

/**
 * 文章管理模块
 * Created by vip on 2017/9/13.
 */
@Service
@Transactional // 开启事物
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    /**
     * 分页查询
     *
     * @param article
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Article> findPage(Article article, int pageNum, int pageSize) {
        //1.设置分页属性
        PageHelper.startPage(pageNum, pageSize);
        //2.封装查询条件
        Example example = new Example(Article.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(article.getTitle())) {//文章标题
            criteria.andLike("title", "%" + article.getTitle() + "%");
        }
        if (StringUtils.isNotBlank(article.getSearchDate())) {//发布时间搜索
            String[] dateTime = article.getSearchDate().split(" " + Constants.MIDDLE_LINE + " ");
            criteria.andBetween("publishTime", dateTime[0], dateTime[1]);
        }
        if(StringUtils.isNotBlank(article.getChannelId())){//渠道搜索
            criteria.andEqualTo("channelId",article.getChannelId());
        }
        return new PageInfo<Article>(articleDao.selectByExample(example));
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @Override
    public Article findById(String id) {
        return articleDao.selectByPrimaryKey(id);
    }

    /**
     * 修改
     *
     * @param article
     * @return
     */
    @Override
    public int update(Article article) {
        return articleDao.updateByPrimaryKeySelective(article);
    }

    /**
     * 新增
     *
     * @param article
     * @return
     */
    @Override
    public int add(Article article) {
        return articleDao.insert(article);
    }

    /**
     * 根据单个ID删除
     *
     * @param id
     * @return
     */
    @Override
    public int deleteById(String id) {
        return articleDao.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteByIds(String[] ids) {
        //封装条件对象
        Example example = new Example(Article.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", Arrays.asList(ids));
        return articleDao.deleteByExample(example);
    }
}
