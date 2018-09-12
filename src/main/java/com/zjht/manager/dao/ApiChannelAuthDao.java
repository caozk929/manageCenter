package com.zjht.manager.dao;

import com.zjht.manager.common.mybatis.BatchInsertMapper;
import com.zjht.manager.entity.ApiChannel;
import com.zjht.manager.entity.ApiChannelAuth;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ApiChannelAuthDao extends Mapper<ApiChannelAuth>,BatchInsertMapper<ApiChannelAuth> {
}