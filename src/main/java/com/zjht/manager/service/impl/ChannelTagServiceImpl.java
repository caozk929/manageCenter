package com.zjht.manager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.manager.dao.ChannelTagDao;
import com.zjht.manager.entity.Select;
import com.zjht.manager.entity.channelProduct.ChannelTag;
import com.zjht.manager.service.ChannelTagService;

import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class ChannelTagServiceImpl implements ChannelTagService {

	@Resource
	private ChannelTagDao channelTagDao;
	
	@Override
	public PageInfo<ChannelTag> getPage(ChannelTag tag, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		Example example = new Example(ChannelTag.class);
		Example.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(tag.getTagName())) {
			criteria.andLike("tagName", "%" + tag.getTagName() + "%");
		}
		criteria.andEqualTo("channelCode", tag.getChannelCode());
		List<ChannelTag> list = channelTagDao.selectByExample(example);

		PageInfo<ChannelTag> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public ChannelTag getTag(String id) {
		// TODO Auto-generated method stub
		return channelTagDao.selectByPrimaryKey(id);
	}

	@Override
	public int addTag(ChannelTag tag) {
		// TODO Auto-generated method stub
		return channelTagDao.insert(tag);
		//return tagDao.insertSelective(tag);
	}

	@Override
	public int updateTag(ChannelTag tag) {
		// TODO Auto-generated method stub
		return channelTagDao.updateByPrimaryKeySelective(tag);
	}

	@Override
	public int deleteTag(String id) {
		// TODO Auto-generated method stub
		return channelTagDao.deleteByPrimaryKey(id);
	}

	@Override
	public List<Select> getSelect(String tagName, String channelCode) {
		// TODO Auto-generated method stub
		return channelTagDao.findSelect(tagName,channelCode);
	}

}
