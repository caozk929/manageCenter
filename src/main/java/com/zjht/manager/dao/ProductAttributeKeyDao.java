package com.zjht.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.zjht.manager.entity.commodity.ProductAttributeKey;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * @author zhangyaohui
 * @since Aug 29, 2017 2:21:29 PM	
 *
 */
@Repository
public interface ProductAttributeKeyDao extends Mapper<ProductAttributeKey> {
	final String insertAttributeKey = "insert into t_product_attribute_key (id, product_code, attribute_key_code) "
			+ "select lpad(nextval('SEQ_PRODUCTATTRIBUTEKEY'),12,'0'), #{productCode}, #{attributeKeyCode} " 
			+ "from dual where not exists (select id from t_product_attribute_key "
			+ "where product_code=#{productCode} and attribute_key_code=#{attributeKeyCode})";
	@Insert(insertAttributeKey)
	int insertAttributeKey(@Param("productCode") String productCode, @Param("attributeKeyCode") String attributeKeyCode);
	
	final String deleteAttributeKey = "<script>delete from t_product_attribute_key where product_code = #{productCode} and attribute_key_code not in "
			+ "<foreach item='item' index='index' collection='lstAttributeKey' open='(' separator=',' close=')'>" + "#{item}" + "</foreach>" + "</script>";	
	@Delete(deleteAttributeKey)
	int deleteAttributeKey(@Param("productCode") String productCode, @Param("lstAttributeKey") List<String> lstAttributeKey);
	
	final String deleteAllAttributeKey = "delete from t_product_attribute_key where product_code = #{productCode}";	
	@Delete(deleteAllAttributeKey)
	int deleteAllAttributeKey(@Param("productCode") String productCode);	
	
	@Update("update t_product_attribute_key set attribute_key_order = #{attributeKeyOrder} where id = #{id}")
	int updateAttributeKeyBySort(@Param("id")String id, @Param("attributeKeyOrder")Byte attributeKeyOrder);
	
	final String selectAttributeKeybyProduct = "select pa.id as id, a.attribute_key_code as attribute_key_code,"
			+ "a.attribute_key_name as attribute_key_name,a.attribute_key_type as attribute_key_type,"
			+ "pa.attribute_key_order as attribute_key_order from t_attribute_key a,t_product_attribute_key pa "
			+ "where pa.product_code=#{productCode} and a.attribute_key_code=pa.attribute_key_code order by pa.attribute_key_order";
	@Select(selectAttributeKeybyProduct)
	@ResultMap(value = "BaseResultMap")
	List<ProductAttributeKey> selectByProduct(@Param("productCode") String productCode);	
}