package com.zjht.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zjht.manager.entity.channelProduct.ChannelProductTag;
import tk.mybatis.mapper.common.Mapper;


@Repository
public interface ChannelProductTagDao extends Mapper<ChannelProductTag> {
	final String deleteAllTag = "delete from t_channel_product_tag where channel_product_code = #{channelProductCode}";	
	@Delete(deleteAllTag)
	void deleteAllTag(@Param("channelProductCode") String channelProductCode);

	final String insertTag = "insert into t_channel_product_tag (id, channel_product_code, tag_id) "
			+ "select lpad(nextval('SEQ_CHANNELPRODUCTTAG'),12,'0'), #{channelProductCode}, #{tagId} " 
			+ "from dual where not exists (select id from t_channel_product_tag "
			+ "where channel_product_code=#{channelProductCode} and tag_id=#{tagId})";
	@Insert(insertTag)	
	void insertTag(@Param("channelProductCode") String channelProductCode, @Param("tagId") String tagId);

	final String deleteTag = "<script>delete from t_channel_product_tag where channel_product_code = #{channelProductCode} and tag_id not in "
			+ "<foreach item='item' index='index' collection='lstTag' open='(' separator=',' close=')'>" + "#{item}" + "</foreach>" + "</script>";	
	@Delete(deleteTag)		
	void deleteTag(@Param("channelProductCode") String channelProductCode, @Param("lstTag") List<String> lstTag);
}