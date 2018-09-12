package com.zjht.manager.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zjht.manager.entity.Select;
import com.zjht.manager.entity.commodity.AttributeKey;

/**
 * 
 * @author zhangyaohui
 * @since Aug 30, 2017 10:11:56 AM	
 *
 */
public interface AttributeKeyService {
	public PageInfo<AttributeKey> getPage(AttributeKey attributeKey, int pageNum, int pageSize);
	public AttributeKey getAttributeKey(String id);
	public AttributeKey addAttributeKey(AttributeKey attributeKey);
	public AttributeKey updateAttributeKey(AttributeKey attributeKey);
	public int deleteAttributeKey(String id);
	public List<Select> getSelect(String attributeKeyName);
}
