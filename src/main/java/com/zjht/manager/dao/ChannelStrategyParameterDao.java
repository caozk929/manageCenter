package com.zjht.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjht.manager.entity.channelProduct.ChannelStrategyParameter;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ChannelStrategyParameterDao extends Mapper<ChannelStrategyParameter> {
	final String selectParamByStrategy = "select id, parameter_code, parameter_name, parameter_type, strategy_id from "
			+ "t_channel_strategy_parameter where strategy_id=#{strategyId}";
	@Select(selectParamByStrategy)
	@ResultMap(value = "BaseResultMap")	
	List<ChannelStrategyParameter> selectParamByStrategy(@Param("strategyId") String strategyId);		
}