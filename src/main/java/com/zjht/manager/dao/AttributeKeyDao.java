package com.zjht.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import com.zjht.manager.entity.commodity.AttributeKey;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * @author zhangyaohui
 * @since Aug 29, 2017 12:08:18 PM
 *
 */
@Repository
public interface AttributeKeyDao extends Mapper<AttributeKey> {
	
	final String findSelect = "<script>select attribute_key_code,attribute_key_name from t_attribute_key"
			+ "<if test=\"attributeKeyName !=null \"> where attribute_key_name like concat('%', #{attributeKeyName}, '%')</if></script>";
	@Results({ @Result(property = "id", column = "attribute_key_code"), @Result(property = "text", column = "attribute_key_name")})
	@Select(findSelect)
	List<com.zjht.manager.entity.Select> findSelect(@Param("attributeKeyName") String attributeKeyName);
	

	final String selectAttributeKeybyCategory = "select attribute_key_code,attribute_key_name from t_attribute_key "
			+ "where attribute_key_code in (select attribute_key_code from t_category_attribute_key where category_code=#{categoryCode})";
	@Select(selectAttributeKeybyCategory)
	@ResultMap(value = "BaseResultMap")
	List<AttributeKey> selectAttributeKeybyCategory(@Param("categoryCode") String categoryCode);
	
	/*final String selectAttributeKeybyProduct = "select attribute_key_code,attribute_key_name from t_attribute_key "
			+ "where attribute_key_code in "
			+ "(select attribute_key_code from t_product_attribute_key where product_code=#{productCode})";*/
	final String selectAttributeKeybyProduct = "select a.attribute_key_code,a.attribute_key_name, pa.attribute_key_order from t_attribute_key a, "
			+ "t_product_attribute_key pa where pa.product_code=#{productCode} and a.attribute_key_code=pa.attribute_key_code";	
	@Select(selectAttributeKeybyProduct)
	@ResultMap(value = "BaseResultMap")
	List<AttributeKey> selectAttributeKeybyProduct(@Param("productCode") String productCode);	
	
	final String insertAttributeKey = "insert into t_attribute_key (id, attribute_key_code, attribute_key_name, attribute_key_type) "
			+ "select #{id}, lpad(currval('SEQ_ATTRIBUTEKEY'),6,'0'), "
			+ "#{attributeKeyName}, #{attributeKeyType} from dual";
	@Insert(insertAttributeKey)
	int insertAttributeKey(AttributeKey attributeKey);

	@Select("select lpad(nextval('SEQ_ATTRIBUTEKEY'),12,'0') from dual")
	@ResultType(String.class)
	String findId();
}