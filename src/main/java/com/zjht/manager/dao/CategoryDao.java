package com.zjht.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import com.zjht.manager.entity.commodity.Category;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * @author zhangyaohui
 * @since Aug 29, 2017 11:57:58 AM	
 *
 */
@Repository
public interface CategoryDao extends Mapper<Category>{
	final String findSelect = "<script>select category_code,category_name from t_category"
			+ "<if test=\"categoryName !=null \"> where category_name like concat('%', #{categoryName}, '%')</if></script>";
	@Results({ @Result(property = "id", column = "category_code"), @Result(property = "text", column = "category_name")})
	@Select(findSelect)
	List<com.zjht.manager.entity.Select> findSelect(@Param("categoryName") String categoryName);
	
	final String insertCategory = "insert into t_category (id, category_code, category_name, up_code, level, status) "
			+ "select #{id}, "
			+ "case when max(c.category_code) is null then concat(#{upCode},'_01') "
			+ "else concat(#{upCode},'_',lpad(convert(max(substring(c.category_code, -2)),signed) + 1, 2,'0')) end, "
			+ "#{categoryName}, #{upCode}, p.level+1, #{status} from t_category p left join t_category c "
			+ "on c.up_code=#{upCode} where p.category_code=#{upCode}";
	@Insert(insertCategory)
	int insertCategory(Category category);

	@Select("select lpad(nextval('SEQ_CATEGORY'),12,'0') from dual")
	@ResultType(String.class)
	String findId();
}
