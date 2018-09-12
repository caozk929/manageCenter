package com.zjht.manager.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import com.zjht.manager.entity.commodity.Goods;

import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface GoodsDao extends Mapper<Goods> {
	final String insertGoods = "insert into t_goods (id, product_code,goods_count,goods_code) "
			+ "select #{id}, #{productCode}, #{goodsCount},"
			+ "case when max(goods_code) is null then concat(#{productCode},'_01') "
			+ "else concat(#{productCode},'_',lpad(convert(max(substring(goods_code, -2)),signed) + 1, 2,'0')) end "
			+ "from t_goods where product_code=#{productCode}";
	@Insert(insertGoods)
	int insertGoods(@Param("id") String id,@Param("productCode") String productCode,@Param("goodsCount") String goodsCount);

	@Select("select lpad(nextval('SEQ_GOODS'),12,'0') from dual")
	@ResultType(String.class)
	String findId();
	
}