package com.zjht.manager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.manager.dao.ChannelStrategyParameterDao;
import com.zjht.manager.entity.channelProduct.ChannelStrategyParameter;
import com.zjht.manager.service.ChannelStrategyParameterService;

@Service
@Transactional
public class ChannelStrategyParameterServiceImpl implements ChannelStrategyParameterService {
	
	@Resource
	private ChannelStrategyParameterDao channelStrategyParameterDao;

	@Override
	public int insertChannelStrategyParameter(ChannelStrategyParameter channelStrategyParameter) {
		// TODO Auto-generated method stub
		return channelStrategyParameterDao.insertSelective(channelStrategyParameter);
	}

	@Override
	public int updateChannelStrategyParameter(ChannelStrategyParameter channelStrategyParameter) {
		// TODO Auto-generated method stub
		return channelStrategyParameterDao.updateByPrimaryKeySelective(channelStrategyParameter);
	}

	@Override
	public int deleteChannelStrategyParameter(String id) {
		// TODO Auto-generated method stub
		return channelStrategyParameterDao.deleteByPrimaryKey(id);
	}

	@Override
	public PageInfo<ChannelStrategyParameter> getPage(ChannelStrategyParameter channelStrategyParameter, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);

		List<ChannelStrategyParameter> list = channelStrategyParameterDao.select(channelStrategyParameter);
		PageInfo<ChannelStrategyParameter> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public ChannelStrategyParameter getChannelStrategyParameter(String id) {
		// TODO Auto-generated method stub
		return channelStrategyParameterDao.selectByPrimaryKey(id);
	}

	/*@Override
	public String getChannelStrategyParamView(String strategyId) {
		// TODO Auto-generated method stub
		StringBuilder data = new StringBuilder();
		List<ChannelStrategyParameter> lstChannelStrategyParameter = channelStrategyParameterDao.selectParamByStrategy(strategyId);
		if(null == lstChannelStrategyParameter || lstChannelStrategyParameter.size() == 0) {
			return "";
		}
		data.append("<div class=\"layui-form-item\" id=\"").append(strategyId).append("\">");
		for(ChannelStrategyParameter channelStrategyParameter : lstChannelStrategyParameter) {
			data.append(getSingleParamView(channelStrategyParameter));
		}
		data.append("</div>");
		return data.toString();
	}
	
	private StringBuilder getSingleParamView(ChannelStrategyParameter parameter) {
		StringBuilder data = new StringBuilder();
		data.append("<div class=\"layui-inline\"><label class=\"layui-form-label\">");
		data.append(parameter.getParameterName());
		data.append("</label><div class=\"layui-input-inline\"><input type=\"text\" name=\"");
		data.append(parameter.getStrategyId()).append('_').append(parameter.getParameterCode()).append("\"");
		data.append(paramType(parameter.getParameterType().intValue()));
		data.append(" autocomplete=\"off\" class=\"layui-input\"></div></div>");
		return data;
	}
	
	private StringBuilder paramType(int paramType) {
		StringBuilder data = new StringBuilder();
		switch (paramType) {
			case 1 :
				return data.append(" lay-verify=\"number\"");	
			case 3 :
				return data.append(" lay-verify=\"date\"");					
			default:
				return data;
			}

	}*/

}
