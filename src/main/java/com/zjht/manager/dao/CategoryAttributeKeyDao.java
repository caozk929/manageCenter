package com.zjht.manager.dao;


import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.zjht.manager.entity.commodity.CategoryAttributeKey;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * @author zhangyaohui
 * @since Aug 29, 2017 11:57:51 AM	
 *
 */
@Repository
public interface CategoryAttributeKeyDao extends Mapper<CategoryAttributeKey> {
	final String insertAttributeKey = "insert into t_category_attribute_key (id, category_code, attribute_key_code) "
			+ "select lpad(nextval('SEQ_CATEGORYATTRIBUTEKEY'),12,'0'), #{categoryCode}, #{attributeKeyCode} " 
			+ "from dual where not exists (select id from t_category_attribute_key "
			+ "where category_code=#{categoryCode} and attribute_key_code=#{attributeKeyCode})";
	@Insert(insertAttributeKey)
	int insertAttributeKey(@Param("categoryCode") String categoryCode, @Param("attributeKeyCode") String attributeKeyCode);
	
	final String deleteAttributeKey = "<script>delete from t_category_attribute_key where category_code = #{categoryCode} and attribute_key_code not in "
			+ "<foreach item='item' index='index' collection='lstAttributeKey' open='(' separator=',' close=')'>" + "#{item}" + "</foreach>" + "</script>";	
	@Delete(deleteAttributeKey)
	int deleteAttributeKey(@Param("categoryCode") String categoryCode, @Param("lstAttributeKey") List<String> lstAttributeKey);
	
	final String deleteAllAttributeKey = "delete from t_category_attribute_key where category_code = #{categoryCode}";	
	@Delete(deleteAllAttributeKey)
	int deleteAllAttributeKey(@Param("categoryCode") String categoryCode);		
	
	@Update("update t_category_attribute_key set attribute_key_order = #{attributeKeyOrder} where id = #{id}")
	int updateAttributeKeyBySort(@Param("id")String id, @Param("attributeKeyOrder")Byte attributeKeyOrder);
	
	final String selectAttributeKeybyCategory = "select ca.id as id, a.attribute_key_code as attribute_key_code,"
			+ "a.attribute_key_name as attribute_key_name,a.attribute_key_type as attribute_key_type,"
			+ "ca.attribute_key_order as attribute_key_order from t_attribute_key a,t_category_attribute_key ca "
			+ "where ca.category_code=#{categoryCode} and a.attribute_key_code=ca.attribute_key_code order by ca.attribute_key_order";
	@Select(selectAttributeKeybyCategory)
	@ResultMap(value = "BaseResultMap")
	List<CategoryAttributeKey> selectByCategory(@Param("categoryCode") String categoryCode);	
}