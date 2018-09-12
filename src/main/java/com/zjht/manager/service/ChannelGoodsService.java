package com.zjht.manager.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zjht.manager.entity.channelProduct.ChannelGoods;
import com.zjht.manager.entity.channelProduct.ChannelGoodsStrategy;
import com.zjht.manager.entity.channelProduct.ChannelProduct;
import com.zjht.manager.entity.channelProduct.ChannelTag;

public interface ChannelGoodsService {
	public PageInfo<Map<String, String>> getPageByProduct(ChannelProduct channelProduct, int pageNum, int pageSize);
	public void addChannelGoods(ChannelGoods channelGoods);
	public void updateChannelGoods(ChannelGoods channelGoods);
	public int deleteChannelGoods(String id);
	public ChannelGoods getChannelGoods(String id);
	public List<ChannelTag> getTagByGoods(String channelGoodsCode, String channelCode);
	public void setTag(String channelGoodsCode, String[] tags);
	
	public List<ChannelGoodsStrategy> getGoodsStrategyByGoods(String channelGoodsCode);		
	public void setStrategy(String channelGoodsCode, String[] strategyIds, Map<String, String[]> mapStrategyParam);	
	public String getGoodsStrategyParamView(String channelGoodsCode, String strategyId);
	public boolean saveChannelGoods(ChannelGoods channelGoods);
}
