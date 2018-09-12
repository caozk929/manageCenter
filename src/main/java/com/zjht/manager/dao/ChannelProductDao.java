package com.zjht.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.zjht.manager.entity.channelProduct.ChannelProduct;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ChannelProductDao extends Mapper<ChannelProduct> {
	final String selectByChannel = "select cp.id, cp.channel_product_code, cp.channel_code, cp.product_code, p.product_name, p.category_code, "
			+ "p.brand, p.series, cp.product_title, cp.product_price, "
			+ "case when (cp.gmt_on_sell is not null and now() >= cp.gmt_on_sell and ((cp.gmt_off_sell is not null and cp.gmt_off_sell > now()) "
			+ "or cp.gmt_off_sell is null)) then 1 else 0 end as status from t_channel_product cp, "
			+ "t_product p where p.product_code = cp.product_code and cp.channel_code=#{channelCode}";
	@Select(selectByChannel)
	@ResultMap(value = "BaseResultMap")
	List<ChannelProduct> selectByChannel(@Param("channelCode") String channelCode);	
	
	final String selectForChannel = "<script>select cp.id, cp.channel_product_code, cp.channel_code, p.product_code, p.product_name, p.category_code, "
			+ "p.brand, p.series, p.status from t_product p left join t_channel_product cp "
			+ "on p.product_code = cp.product_code and cp.channel_code=#{channelCode} where 1=1"
			+ "<if test=\"categoryCode !=null and categoryCode!='' \"> and p.category_code=#{categoryCode}</if>"
			+ "<if test=\"productName !=null and productName!='' \"> and p.product_name like concat('%', #{productName}, '%')</if></script>";
	@Select(selectForChannel)
	@ResultMap(value = "BaseResultMap")
	List<ChannelProduct> selectForChannel(ChannelProduct channelProduct);
	
	final String insertChannelProduct = "insert into t_channel_product (id, channel_product_code, product_code, channel_code) "
			+ "select lpad(nextval('SEQ_CHANNELPRODUCT'),12,'0'), concat(#{channelCode},'_',#{productCode}), #{productCode}, #{channelCode}" 
			+ "from dual where not exists (select id from t_channel_product "
			+ "where product_code=#{productCode} and channel_code=#{channelCode})";
	@Insert(insertChannelProduct)
	int insertChannelProduct(ChannelProduct channelProduct);	
	
	/*final String selectById = "select cp.id, cp.channel_product_code, cp.channel_code, cp.product_code, p.product_name, p.category_code, "
			+ "c.category_name, p.brand, p.series, cp.product_title, cp.product_price, cp.status from t_channel_product cp, "
			+ "t_product p, t_category c where cp.id=#{id} and cp.product_code = p.product_code and p.category_code=c.category_code";*/
	
	final String selectById = "select cp.id, cp.channel_product_code, cp.channel_code, cp.product_code, p.product_name, p.category_code, "
			+ "c.category_name, p.brand, p.series, cp.product_title, cp.product_price, cp.status from t_channel_product cp, t_product p "
			+ "left join t_category c on p.category_code=c.category_code where cp.id=#{id} and cp.product_code = p.product_code";	
	@Select(selectById)
	@ResultMap(value = "BaseResultMap")
	ChannelProduct selectById(@Param("id") String id);	
	
	@Update("update t_channel_product set gmt_on_sell=#{gmtOnSell}, gmt_off_sell=#{gmtOffSell} where id = #{id}")
	int updateForSell(ChannelProduct channelProduct);	
	
	final String updateChannelProduct = "update t_channel_product p inner join (select #{id} as id, "
			+ "count(1) as countCode from t_channel_product where channel_code=#{channelCode} "
			+ "and specific_product_code=#{specificProductCode} and id <> #{id}) as tp on tp.id=p.id set p.product_title=#{productTitle}, "
			+ "p.specific_product_code=#{specificProductCode}, p.gmt_on_sell=#{gmtOnSell}, p.gmt_off_sell=#{gmtOffSell} "
			+ "where p.id = #{id} and ((tp.countCode = 0 and #{specificProductCode} > '') "
			+ "or #{specificProductCode} is null or #{specificProductCode} = '')";
	@Update(updateChannelProduct)
	int updateChannelProduct(ChannelProduct channelProduct);		
}