package com.zjht.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjht.manager.entity.commodity.Product;
import com.zjht.manager.entity.commodity.ProductAttribute;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * @author zhangyaohui
 * @since Aug 29, 2017 2:16:26 PM	
 *
 */
@Repository
public interface ProductAttributeDao extends Mapper<ProductAttribute> {
	final String selectByProduct = "select a.attribute_key_code,a.attribute_key_name, pa.attribute_value from t_attribute_key a left join "
			+ "t_product_attribute pa on pa.attribute_key_code=a.attribute_key_code and pa.product_code=#{productCode} where a.attribute_key_code in "
			+ "(select attribute_key_code from t_category_attribute_key where category_code=#{categoryCode})";
	/*final String selectByCategory = "select a.attribute_key_code,a.attribute_key_name, pa.attribute_value from t_attribute_key a, "
			+ "t_product_attribute pa where a.attribute_key_code=pa.attribute_key_code and a.attribute_key_code in "
			+ "(select attribute_key_code from t_category_attribute_key where category_code=#{categoryCode})";	*/
	@Select(selectByProduct)
	@ResultMap(value = "BaseResultMap")
	List<ProductAttribute> selectByProduct(Product product);	
	
	final String deleteByProduct = "delete from t_product_attribute where product_code = #{productCode}";	
	@Delete(deleteByProduct)
	int deleteByProduct(@Param("productCode") String productCode);
}