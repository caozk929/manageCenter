package com.zjht.manager.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zjht.manager.entity.channelProduct.ChannelStrategy;

public interface ChannelStrategyService {

	ChannelStrategy getChannelStrategy(String id);

	int addChannelStrategy(ChannelStrategy channelStrategy);

	int updateChannelStrategy(ChannelStrategy channelStrategy);

	int deleteChannelStrategy(String id);

	PageInfo<ChannelStrategy> getPage(ChannelStrategy channelStrategy, int pageNum, int pageSize);
	
	List<ChannelStrategy> getStrategyByChannel(String channelCode);	

}
