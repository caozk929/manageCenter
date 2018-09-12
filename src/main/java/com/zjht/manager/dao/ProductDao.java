package com.zjht.manager.dao;

import com.zjht.manager.entity.commodity.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 
 * @author zhangyaohui
 * @since Aug 29, 2017 11:35:48 AM	
 *
 */
@Repository
public interface ProductDao extends Mapper<Product> {
	final String findSelect = "<script>select product_code,product_name from t_product"
			+ "<if test=\"productName !=null \"> where product_name like concat('%', #{productName}, '%')</if></script>";
	@Results({ @Result(property = "id", column = "product_code"), @Result(property = "text", column = "product_name")})
	@Select(findSelect)
	List<com.zjht.manager.entity.Select> findSelect(@Param("productName") String productName);
	
	final String updateCount = "UPDATE t_product SET product_count=(select sum(goods_count) from t_goods where product_code =#{productCode})"
			+ " WHERE product_code =#{productCode}";	
	@Update(updateCount)
	int updateCount(@Param("productCode") String productCode);	
	
	final String insertProduct = "insert into t_product (id, product_code, category_code, product_name, product_type, "
			+ "brand, series, unit, status, synopsis_url) "
			+ "select #{id}, "
			+ "case when count(c.id)=0 then concat(#{categoryCode},'_01') "
			+ "else concat(#{categoryCode},'_',lpad(convert(max(substring(c.product_code, -2)),signed) + 1, 2,'0')) end, "
			+ "#{categoryCode}, #{productName}, #{productType}, #{brand}, #{series}, #{unit}, #{status}, #{synopsisUrl} "
			+ "from t_product p left join t_product c on c.category_code=#{categoryCode}";
	@Insert(insertProduct)
	int insertProduct(Product product);

	@Select("select lpad(nextval('SEQ_PRODUCT'),12,'0') from dual")
	@ResultType(String.class)
	String findId();
	
	final String selectById = "select p.id, p.product_code, p.product_name, p.category_code, p.product_type, "
			+ "c.category_name, p.brand, p.series, p.status, p.unit, p.product_count, p.synopsis_url from t_product p "
			+ "left join t_category c on c.category_code=p.category_code where p.id=#{id}";
	@Select(selectById)
	@ResultMap(value = "BaseResultMap")
	Product selectById(@Param("id") String id);		
	
	final String selectByCode = "select p.id, p.product_code, p.product_name, p.category_code, p.product_type, "
			+ "c.category_name, p.brand, p.series, p.status, p.unit, p.product_count from t_product p "
			+ "left join t_category c on c.category_code=p.category_code where p.product_code=#{productCode}";	
	@Select(selectByCode)
	@ResultMap(value = "BaseResultMap")
	Product selectByCode(@Param("productCode") String productCode);	
	
	final String updateProduct = "UPDATE t_product SET product_name=#{productName}, brand=#{brand}, series=#{series}, unit=#{unit}, status=#{status}, " +
			"synopsis_url=#{synopsisUrl}, product_type=#{productType}"
			+ " WHERE id=#{id} and category_code=#{categoryCode}";	
	@Update(updateProduct)
	int updateProduct(Product product);	
	
	
	final String selectCountByProduct = "select sum(amount) from (select count(1) amount from t_product_attribute a where a.product_code=#{productCode} "
			+ "union select count(1) amount from t_goods g where g.product_code=#{productCode}) p";
	@Select(selectCountByProduct)
	@ResultType(value = Integer.class)
	int selectCountByProduct(@Param("productCode") String productCode);		
}