package com.zjht.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.zjht.manager.entity.channelProduct.ChannelGoods;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ChannelGoodsDao extends Mapper<ChannelGoods> {
	final String selectByChannelProduct = "select cg.id, g.product_code, g.goods_code, cg.channel_goods_code, cp.channel_product_code, "
			+ "g.goods_count, cg.price, cg.amount, cg.specific_goods_code, cg.logo "
			+ "from t_goods g left join t_channel_goods cg on cg.goods_code=g.goods_code "
			+ "left join t_channel_product cp on g.product_code=cp.product_code "
			+ "and cp.channel_product_code=#{channelProductCode} where g.product_code=#{productCode}";
	@Select(selectByChannelProduct)
	@ResultMap(value = "BaseResultMap")
	List<ChannelGoods> selectByChannelProduct(@Param("productCode") String productCode, @Param("channelProductCode") String channelProductCode);
	
	@Update("update t_goods g, t_channel_goods cg set g.goods_count=g.goods_count + cg.amount where cg.id = #{id} and cg.goods_code=g.goods_code")
	int updateGoodsForUnfreeze(@Param("id") String id);	
	
	@Update("update t_goods set goods_count= goods_count - #{amount} where goods_code=#{goodsCode} and goods_count >= #{amount}")
	int updateGoodsForFreeze(ChannelGoods channelGoods);		
	
	final String updateProductCount = "UPDATE t_product p, t_goods g SET p.product_count=(select sum(goods_count) from t_goods where product_code =p.product_code)"
			+ " WHERE g.product_code =p.product_code and g.goods_code=#{goodsCode}";	
	@Update(updateProductCount)
	int updateProductCount(@Param("goodsCode") String goodsCode);
	
	final String insertChannelGoods = "insert into t_channel_goods (id, channel_goods_code, goods_code, "
			+ "channel_product_code, specific_goods_code, price, amount, channel_code) "
			+ "select lpad(nextval('SEQ_CHANNELGOODS'),12,'0'), #{channelGoodsCode}, #{goodsCode}, "
			+ "#{channelProductCode}, #{specificGoodsCode}, #{price}, #{amount}, #{channelCode} " 
			+ "from dual where not exists (select id from t_channel_goods "
			+ "where specific_goods_code=#{specificGoodsCode} and channel_code=#{channelCode} and #{specificGoodsCode} > '')";
	@Insert(insertChannelGoods)
	int insertChannelGoods(ChannelGoods channelGoods);	
	
	final String updateChannelGoods = "update t_channel_goods g inner join (select #{id} as id, "
			+ "count(1) as countCode from t_channel_goods where channel_product_code=#{channelProductCode} "
			+ "and specific_goods_code=#{specificGoodsCode} and id <> #{id}) as tg on tg.id=g.id set g.specific_goods_code=#{specificGoodsCode}, "
			+ "g.price=#{price}, g.amount=#{amount} where g.id = #{id} and ((tg.countCode = 0 and #{specificGoodsCode} > '') "
			+ "or #{specificGoodsCode} is null or #{specificGoodsCode} = '')";
	@Update(updateChannelGoods)
	int updateChannelGoods(ChannelGoods channelGoods);
	
	final String updateProductPrice = "UPDATE t_channel_product SET product_price="
			+ "(select concat(min(price),'~',max(price)) from t_channel_goods where channel_product_code =#{channelProductCode}) "
			+ "where channel_product_code =#{channelProductCode}";	
	@Update(updateProductPrice)
	int updateProductPrice(@Param("channelProductCode") String channelProductCode);	
}