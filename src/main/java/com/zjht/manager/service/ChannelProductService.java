package com.zjht.manager.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zjht.manager.entity.channelProduct.ChannelProduct;
import com.zjht.manager.entity.channelProduct.ChannelProductDetails;
import com.zjht.manager.entity.channelProduct.ChannelTag;

public interface ChannelProductService {

	public PageInfo<ChannelProduct> getPage(ChannelProduct channelProduct, int pageNum, int pageSize);
	
	public PageInfo<ChannelProduct> getPageForBind(ChannelProduct channelProduct, int pageNum, int pageSize);	

	public ChannelProduct getChannelProduct(String id);
	
	public ChannelProduct getChannelProductForAll(String id);	
	
	public void bindChannel(String[] arrayProductCode, String ChannelProductDetails);
	
	public void updateChannelProduct(ChannelProduct channelProduct);	
	
	public void updateChannelProductDetails(ChannelProductDetails channelProductDetails);	
	
	public void updateChannelProductSell(ChannelProduct channelProduct);
	
	public ChannelProductDetails getChannelProductDetailsByCode(String channelProductCode);	
	
	public void deleteChannelProduct(String id);

	public void setTag(String channelProductCode, String[] tags);

	public List<ChannelTag> getTagByProduct(String channelProductCode, String channelCode);	

}
