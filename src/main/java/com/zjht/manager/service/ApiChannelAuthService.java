package com.zjht.manager.service;

import com.zjht.manager.entity.ApiChannelAuth;

import java.util.List;

/**
 * 渠道权限service
 *
 * @outhor caozk
 * @create 2017-09-05 19:45
 */
public interface ApiChannelAuthService {

    ApiChannelAuth getById(String id);

    int addChannel(ApiChannelAuth channelAuth);

    int delete(ApiChannelAuth channelAuth);
    /**
     * 通过渠道id删除
     * @param ids
     */
    void deleteByIds(String[] ids);

    List<ApiChannelAuth> getListByChannelId(String channelId);

}
