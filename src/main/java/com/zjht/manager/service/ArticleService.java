package com.zjht.manager.service;

import com.github.pagehelper.PageInfo;
import com.zjht.manager.entity.Article;

/**
 * 文章模块Service
 * Created by vip on 2017/9/13.
 */
public interface ArticleService {

    /**
     * 分页查询
     * @param article
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Article> findPage(Article article, int pageNum, int pageSize);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    Article findById(String id);

    /**
     * 修改
     * @param article
     * @return
     */
    int update(Article article);

    /**
     * 新增
     * @param article
     * @return
     */
    int add(Article article);

    /**
     * 根据单个ID删除
     * @param id
     * @return
     */
    int deleteById(String id);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int deleteByIds(String[] ids);

}
