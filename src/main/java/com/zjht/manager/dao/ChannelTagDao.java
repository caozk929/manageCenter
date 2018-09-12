package com.zjht.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjht.manager.entity.channelProduct.ChannelTag;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ChannelTagDao extends Mapper<ChannelTag> {
	final String findSelect = "<script>select id, tag_name from t_channel_tag where channel_code=#{channelCode}"
			+ "<if test=\"tagName !=null \"> and tag_name like concat('%', #{tagName}, '%')</if></script>";
	@Results({ @Result(property = "id", column = "id"), @Result(property = "text", column = "tag_name")})
	@Select(findSelect)
	List<com.zjht.manager.entity.Select> findSelect(@Param("tagName") String tagName, @Param("channelCode") String channelCode);

	final String selectTagByGoods = "select id, tag_name from t_channel_tag where channel_code=#{channelCode} "
			+ "and id in (select tag_id from t_channel_goods_tag where channel_goods_code=#{channelGoodsCode})";
	@Select(selectTagByGoods)
	@ResultMap(value = "BaseResultMap")	
	List<ChannelTag> selectTagByGoods(@Param("channelGoodsCode") String channelGoodsCode, @Param("channelCode") String channelCode);	
	
	final String selectTagByProduct = "select id, tag_name from t_channel_tag where channel_code=#{channelCode} "
			+ "and id in (select tag_id from t_channel_product_tag where channel_product_code=#{channelProductCode})";
	@Select(selectTagByProduct)
	@ResultMap(value = "BaseResultMap")	
	List<ChannelTag> selectTagByProduct(@Param("channelProductCode") String channelProductCode, @Param("channelCode") String channelCode);			
}