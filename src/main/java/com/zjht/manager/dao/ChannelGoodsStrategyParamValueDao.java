package com.zjht.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.zjht.manager.entity.channelProduct.ChannelGoodsStrategyParamValue;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ChannelGoodsStrategyParamValueDao extends Mapper<ChannelGoodsStrategyParamValue> {
	final String selectStrategyParamByGoods = "select spv.id, sp.parameter_code, sp.parameter_name, "
			+ "sp.parameter_type, sp.strategy_id, spv.strategy_param_value, sp.id as strategy_param_id from "
			+ "t_channel_strategy_parameter sp left join t_channel_goods_strategy gs on gs.strategy_id= sp.strategy_id "
			+ "and gs.channel_goods_code=#{channelGoodsCode} left join t_channel_goods_strategy_param_value spv "
			+ "on spv.goods_strategy_id=gs.id and spv.strategy_param_id=sp.id where sp.strategy_id=#{strategyId}";
	@Select(selectStrategyParamByGoods)
	@ResultMap(value = "BaseResultMap")	
	List<ChannelGoodsStrategyParamValue> selectStrategyParamByGoods(@Param("channelGoodsCode") String channelGoodsCode, @Param("strategyId") String strategyId);
	
	final String insertGoodsStrategyParam = "insert into t_channel_goods_strategy_param_value (id, goods_strategy_id, strategy_id, strategy_param_id, strategy_param_value) "
			+ "select lpad(nextval('SEQ_GOODSSTRATEGYPARAMVALUE'),12,'0'), #{goodsStrategyId}, #{strategyId}, #{strategyParamId}, #{strategyParamValue} from dual "
			+ "where not exists (select id from t_channel_goods_strategy_param_value "
			+ "where goods_strategy_id=#{goodsStrategyId} and strategy_param_id=#{strategyParamId})";
	@Insert(insertGoodsStrategyParam)
	int insertGoodsStrategyParam(ChannelGoodsStrategyParamValue channelGoodsStrategyParamValue);	
	
	final String updateGoodsStrategyParam = "update t_channel_goods_strategy_param_value set strategy_param_value=#{strategyParamValue} "
			+ "where goods_strategy_id=#{goodsStrategyId} and strategy_param_id=#{strategyParamId}";
	@Update(updateGoodsStrategyParam)
	int updateGoodsStrategyParam(ChannelGoodsStrategyParamValue channelGoodsStrategyParamValue);	

	final String deleteGoodsStrategyParam = "<script>delete from t_channel_goods_strategy_param_value where goods_strategy_id in "
			+ "(select id from t_channel_goods_strategy where channel_goods_code = #{channelGoodsCode} and strategy_id not in "
			+ "<foreach item='item' index='index' collection='lstStrategyId' open='(' separator=',' close=')'>" + "#{item}" + "</foreach>)" + "</script>";	
	@Delete(deleteGoodsStrategyParam)
	int deleteGoodsStrategyParam(@Param("channelGoodsCode") String channelGoodsCode, @Param("lstStrategyId") List<String> lstStrategyId);	
	
	final String deleteAllGoodsStrategyParam = "delete from t_channel_goods_strategy_param_value where goods_strategy_id "
			+ "in (select id from t_channel_goods_strategy where channel_goods_code = #{channelGoodsCode})";	
	@Delete(deleteAllGoodsStrategyParam)
	int deleteAllGoodsStrategyParam(@Param("channelGoodsCode") String channelGoodsCode);	
}