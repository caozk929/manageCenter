package com.zjht.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjht.manager.entity.commodity.GoodsAttribute;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface GoodsAttributeDao extends Mapper<GoodsAttribute> {
	final String selectByProduct = "select a.attribute_key_code,a.attribute_key_name, ga.attribute_value from t_attribute_key a left join "
			+ "t_goods_attribute ga on a.attribute_key_code=ga.attribute_key_code where a.attribute_key_code in "
			+ "(select attribute_key_code from t_product_attribute_key where product_code=#{productCode})";
	@Select(selectByProduct)
	@ResultMap(value = "BaseResultMap")
	List<GoodsAttribute> selectByProduct(@Param("productCode") String productCode);	
	
	final String deleteByGoods = "delete from t_goods_attribute where goods_code = #{goodsCode}";	
	@Delete(deleteByGoods)
	int deleteByGoods(@Param("goodsCode") String goodsCode);	
	
	final String selectByGoods = "<script>select goods_code, attribute_key_code,attribute_value from t_goods_attribute where goods_code in "
			+ "<foreach item='item' index='index' collection='lstGoodsCode' open='(' separator=',' close=')'>" + "#{item}" + "</foreach></script>";
	@Select(selectByGoods)
	@ResultMap(value = "BaseResultMap")
	List<GoodsAttribute> selectByGoods(@Param("lstGoodsCode") List<String> lstGoodsCode);		
}