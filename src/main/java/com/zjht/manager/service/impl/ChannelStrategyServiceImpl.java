package com.zjht.manager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.manager.dao.ChannelStrategyDao;
import com.zjht.manager.entity.channelProduct.ChannelStrategy;
import com.zjht.manager.service.ChannelStrategyService;

import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class ChannelStrategyServiceImpl implements ChannelStrategyService {

	@Resource
	private ChannelStrategyDao channelStrategyDao;
	
	@Override
	public PageInfo<ChannelStrategy> getPage(ChannelStrategy channelStrategy, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		Example example = new Example(ChannelStrategy.class);
		Example.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(channelStrategy.getStrategyName())) {
			criteria.andLike("channelStrategyName", "%" + channelStrategy.getStrategyName() + "%");
		}
		List<ChannelStrategy> list = channelStrategyDao.selectByExample(example);

		PageInfo<ChannelStrategy> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public ChannelStrategy getChannelStrategy(String id) {
		// TODO Auto-generated method stub
		return channelStrategyDao.selectByPrimaryKey(id);
	}

	@Override
	public int addChannelStrategy(ChannelStrategy channelStrategy) {
		// TODO Auto-generated method stub
		return channelStrategyDao.insertSelective(channelStrategy);
	}

	@Override
	public int updateChannelStrategy(ChannelStrategy channelStrategy) {
		// TODO Auto-generated method stub
		return channelStrategyDao.updateByPrimaryKeySelective(channelStrategy);
	}

	@Override
	public int deleteChannelStrategy(String id) {
		// TODO Auto-generated method stub
		return channelStrategyDao.deleteByPrimaryKey(id);
	}

	@Override
	public List<ChannelStrategy> getStrategyByChannel(String channelCode) {
		// TODO Auto-generated method stub
		return channelStrategyDao.selectStrategyByChannel(channelCode);
	}

}
