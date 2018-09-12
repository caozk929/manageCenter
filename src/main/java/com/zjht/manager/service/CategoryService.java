package com.zjht.manager.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zjht.manager.entity.Select;
import com.zjht.manager.entity.commodity.AttributeKey;
import com.zjht.manager.entity.commodity.Category;
import com.zjht.manager.entity.commodity.CategoryAttributeKey;

/**
 * 
 * @author zhangyaohui
 * @since Aug 30, 2017 10:11:56 AM	
 *
 */
public interface CategoryService {
	public List<Category> selectAll();
	public PageInfo<Category> getPage(Category category, int pageNum, int pageSize);
	public Category getCategory(String id);
	public Category getCategoryByCode(String categoryCode);
	public Category addCategroy(Category category);
	public Category updateCategroy(Category category);
	public int deleteCategroy(String id);
	
	public void setAttrbuteKey(String categoryCode, String[] attributeKeys);
	public List<AttributeKey> getAttributeKey(String categoryCode);
	public int sortAttrbuteKey(String id, Byte attributeKeyOrder);
	public PageInfo<CategoryAttributeKey> getAttributeKeyPage(String categoryCode, int pageNum, int pageSize);
	public List<Select> getSelect(String categoryName);	
	public List<Select> getTreeSelect();
	public List<Object>  getAll();
}
