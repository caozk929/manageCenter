package com.zjht.manager.service;

import com.github.pagehelper.PageInfo;
import com.zjht.manager.entity.ApiChannelPayconfig;

/**
 * 渠道支付配置
 * Created by vip on 2017/9/19.
 */
public interface ApiChannelPayconfigService {

    /**
     * 查询分页数据
     * @param acp
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<ApiChannelPayconfig> findPage(ApiChannelPayconfig acp, int pageNum, int pageSize);

    /**
     * 新增
     * @param acp
     * @return
     */
    int add(ApiChannelPayconfig acp);

    /**
     * 修改
     * @param acp
     * @return
     */
    int update(ApiChannelPayconfig acp);

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteById(String id);

    /**
     * 查询
     * @param id
     * @return
     */
    ApiChannelPayconfig findById(String id);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int deleteByIds(String[] ids);

}
