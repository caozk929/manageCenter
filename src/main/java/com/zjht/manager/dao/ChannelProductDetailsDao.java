package com.zjht.manager.dao;

import org.springframework.stereotype.Repository;

import com.zjht.manager.entity.channelProduct.ChannelProductDetails;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ChannelProductDetailsDao extends Mapper<ChannelProductDetails> {
}