package com.zjht.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjht.manager.entity.channelProduct.ChannelStrategy;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ChannelStrategyDao extends Mapper<ChannelStrategy> {
	final String selectStrategyByChannel = "select id, strategy_Code, strategy_name, channel_code from "
			+ "t_channel_strategy where channel_code=#{channelCode}";
	@Select(selectStrategyByChannel)
	@ResultMap(value = "BaseResultMap")	
	List<ChannelStrategy> selectStrategyByChannel(@Param("channelCode") String channelCode);			
}