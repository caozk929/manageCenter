package com.zjht.manager.service;

import com.github.pagehelper.PageInfo;
import com.zjht.manager.entity.channelProduct.ChannelStrategyParameter;

public interface ChannelStrategyParameterService {
	public int insertChannelStrategyParameter(ChannelStrategyParameter channelStrategyParameter);
	public int updateChannelStrategyParameter(ChannelStrategyParameter channelStrategyParameter);
	public int deleteChannelStrategyParameter(String id);
	public PageInfo<ChannelStrategyParameter> getPage(ChannelStrategyParameter channelStrategyParameter, int cpn, int pageSize);
	public ChannelStrategyParameter getChannelStrategyParameter(String id);
	
	//public String getChannelStrategyParamView(String strategyId);	
}
