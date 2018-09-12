package com.zjht.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjht.manager.entity.channelProduct.ChannelGoodsStrategy;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ChannelGoodsStrategyDao extends Mapper<ChannelGoodsStrategy> {	
	final String selectStrategyByGoods = "select gs.id, s.strategy_Code, s.strategy_name, s.channel_code, gs.channel_goods_code, "
			+ "s.id as strategy_id from t_channel_strategy s left join t_channel_goods_strategy gs "
			+ "on gs.strategy_id=s.id and gs.channel_goods_code=#{channelGoodsCode}";
	@Select(selectStrategyByGoods)
	@ResultMap(value = "BaseResultMap")	
	List<ChannelGoodsStrategy> selectStrategyByGoods(@Param("channelGoodsCode") String channelGoodsCode);	
	
	final String deleteAllStrategy = "delete from t_channel_goods_strategy where channel_goods_code = #{channelGoodsCode}";	
	@Delete(deleteAllStrategy)
	int deleteAllStrategy(@Param("channelGoodsCode") String channelGoodsCode);
	
	final String insertStrategy = "insert into t_channel_goods_strategy (id, channel_goods_code, strategy_id) "
			+ "select #{id}, #{channelGoodsCode}, #{strategyId} " 
			+ "from dual where not exists (select id from t_channel_goods_strategy "
			+ "where channel_goods_code=#{channelGoodsCode} and strategy_id=#{strategyId})";
	@Insert(insertStrategy)
	int insertStrategy(ChannelGoodsStrategy channelGoodsStrategy);		
	@Select("select lpad(nextval('SEQ_CHANNELGOODSSTRATEGY'),12,'0') from dual")
	@ResultType(String.class)
	String findChannelGoodsStrategyId();	
	
	@Select("select id from t_channel_goods_strategy where channel_goods_code=#{channelGoodsCode} and strategy_id=#{strategyId}")
	@ResultType(String.class)
	String selectChannelGoodsStrategyId(ChannelGoodsStrategy channelGoodsStrategy);		
	
	final String deleteStrategy = "<script>delete from t_channel_goods_strategy where channel_goods_code = #{channelGoodsCode} and strategy_id not in "
			+ "<foreach item='item' index='index' collection='lstStrategyId' open='(' separator=',' close=')'>" + "#{item}" + "</foreach>" + "</script>";	
	@Delete(deleteStrategy)
	int deleteStrategy(@Param("channelGoodsCode") String channelGoodsCode, @Param("lstStrategyId") List<String> lstStrategyId);	
}