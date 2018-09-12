package com.zjht.manager.service.impl;

import com.zjht.manager.dao.ApiChannelAuthDao;
import com.zjht.manager.entity.ApiChannelAuth;
import com.zjht.manager.service.ApiChannelAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @outhor caozk
 * @create 2017-09-18 9:48
 */
@Service
public class ApiChannelAuthServiceImpl implements ApiChannelAuthService {
    @Autowired
    private ApiChannelAuthDao apiChannelAuthDao;
    @Override
    public ApiChannelAuth getById(String id) {
        return apiChannelAuthDao.selectByPrimaryKey(id);
    }

    @Override
    public int addChannel(ApiChannelAuth channelAuth) {
        return apiChannelAuthDao.insert(channelAuth);
    }

    @Override
    public int delete(ApiChannelAuth channelAuth) {
        return apiChannelAuthDao.delete(channelAuth);
    }

    @Override
    public void deleteByIds(String[] ids) {
        for (int i=0; i<ids.length; i++){
            apiChannelAuthDao.deleteByPrimaryKey(ids[i]);
        }
    }

    @Override
    public List<ApiChannelAuth> getListByChannelId(String channelId) {
        if(channelId == null){
            return new ArrayList<ApiChannelAuth>();
        }
        ApiChannelAuth channel = new ApiChannelAuth();
        channel.setChannelId(channelId);
        return apiChannelAuthDao.select(channel);
    }
}
