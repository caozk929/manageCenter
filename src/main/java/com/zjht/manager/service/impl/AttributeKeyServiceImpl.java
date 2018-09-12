package com.zjht.manager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.manager.dao.AttributeKeyDao;
import com.zjht.manager.entity.Select;
import com.zjht.manager.entity.commodity.AttributeKey;
import com.zjht.manager.service.AttributeKeyService;

import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class AttributeKeyServiceImpl implements AttributeKeyService {

	@Resource
	private AttributeKeyDao attributeKeyDao;
	
	@Override
	public PageInfo<AttributeKey> getPage(AttributeKey attributeKey, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		Example example = new Example(AttributeKey.class);
		Example.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(attributeKey.getAttributeKeyName())) {
			criteria.andLike("attributeKeyName", "%" + attributeKey.getAttributeKeyName() + "%");
		}
		List<AttributeKey> list = attributeKeyDao.selectByExample(example);

		PageInfo<AttributeKey> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public AttributeKey getAttributeKey(String id) {
		// TODO Auto-generated method stub
		return attributeKeyDao.selectByPrimaryKey(id);
	}

	@Override
	public AttributeKey addAttributeKey(AttributeKey attributeKey) {
		// TODO Auto-generated method stub
		attributeKey.setId(attributeKeyDao.findId());
		attributeKeyDao.insertAttributeKey(attributeKey);
		return attributeKeyDao.selectByPrimaryKey(attributeKey.getId());
		//return attributeKeyDao.insertSelective(attributeKey);
	}

	@Override
	public AttributeKey updateAttributeKey(AttributeKey attributeKey) {
		// TODO Auto-generated method stub
		attributeKeyDao.updateByPrimaryKeySelective(attributeKey);
		return attributeKeyDao.selectByPrimaryKey(attributeKey.getId());
	}

	@Override
	public int deleteAttributeKey(String id) {
		// TODO Auto-generated method stub
		return attributeKeyDao.deleteByPrimaryKey(id);
	}

	@Override
	public List<Select> getSelect(String attributeKeyName) {
		// TODO Auto-generated method stub
		return attributeKeyDao.findSelect(attributeKeyName);
	}

}
