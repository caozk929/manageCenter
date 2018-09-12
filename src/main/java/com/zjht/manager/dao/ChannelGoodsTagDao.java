package com.zjht.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zjht.manager.entity.channelProduct.ChannelGoodsTag;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ChannelGoodsTagDao extends Mapper<ChannelGoodsTag> {
	
	final String deleteAllTag = "delete from t_channel_goods_tag where channel_goods_code = #{channelGoodsCode}";	
	@Delete(deleteAllTag)
	void deleteAllTag(@Param("channelGoodsCode") String channelGoodsCode);

	final String insertTag = "insert into t_channel_goods_tag (id, channel_goods_code, tag_id) "
			+ "select lpad(nextval('SEQ_CHANNELGOODSTAG'),12,'0'), #{channelGoodsCode}, #{tagId} " 
			+ "from dual where not exists (select id from t_channel_goods_tag "
			+ "where channel_goods_code=#{channelGoodsCode} and tag_id=#{tagId})";
	@Insert(insertTag)	
	void insertTag(@Param("channelGoodsCode") String channelGoodsCode, @Param("tagId") String tagId);

	final String deleteTag = "<script>delete from t_channel_goods_tag where channel_goods_code = #{channelGoodsCode} and tag_id not in "
			+ "<foreach item='item' index='index' collection='lstTag' open='(' separator=',' close=')'>" + "#{item}" + "</foreach>" + "</script>";	
	@Delete(deleteTag)	
	void deleteTag(@Param("channelGoodsCode") String channelGoodsCode, @Param("lstTag") List<String> lstTag);
}