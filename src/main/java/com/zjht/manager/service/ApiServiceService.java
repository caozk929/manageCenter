package com.zjht.manager.service;

import com.github.pagehelper.PageInfo;
import com.zjht.manager.entity.ApiService;

import java.util.List;

/**
 * 接口服务service
 *
 * @outhor caozk
 * @create 2017-09-05 19:45
 */
public interface ApiServiceService {

    ApiService getById(String id);

    int addChannel(ApiService service);

    int update(ApiService service);

    int updateSelective(ApiService service);

    int delete(ApiService service);

    /**
     * 通过渠道id删除
     * @param ids
     */
    void deleteByIds(String[] ids);

    /**
     * 批量更新状态
     * @param ids
     * @param status
     */
    void updateStatus(String[] ids, int status);

    List<ApiService> getList(ApiService service);

    PageInfo<ApiService> getPage(ApiService service, Integer pageNum, Integer pageSize);

}
