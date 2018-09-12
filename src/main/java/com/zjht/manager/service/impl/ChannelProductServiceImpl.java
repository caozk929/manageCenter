package com.zjht.manager.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.manager.dao.CategoryDao;
import com.zjht.manager.dao.ChannelProductDao;
import com.zjht.manager.dao.ChannelProductDetailsDao;
import com.zjht.manager.dao.ChannelProductTagDao;
import com.zjht.manager.dao.ChannelTagDao;
import com.zjht.manager.entity.Select;
import com.zjht.manager.entity.channelProduct.ChannelProduct;
import com.zjht.manager.entity.channelProduct.ChannelProductDetails;
import com.zjht.manager.entity.channelProduct.ChannelTag;
import com.zjht.manager.service.ChannelProductService;

@Service
@Transactional
public class ChannelProductServiceImpl implements ChannelProductService {
	@Resource
	private ChannelProductDao channelProductDao;
	@Resource
	private ChannelProductDetailsDao channelProductDetailsDao;	
	@Resource
	private ChannelProductTagDao channelProductTagDao;
	@Resource
	private ChannelTagDao channelTagDao;
	@Resource
	private CategoryDao categoryDao;

	@Override
	public PageInfo<ChannelProduct> getPage(ChannelProduct channelProduct, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<ChannelProduct> list = channelProductDao.selectByChannel(channelProduct.getChannelCode());
		
        List<Select> lstCategory = categoryDao.findSelect(null);
        Map<String, String> mapCategory = new HashMap<>();
        for(Select select : lstCategory) {
        	mapCategory.put(select.getId(), select.getText());
        }
        PageInfo<ChannelProduct> pageInfo = new PageInfo<>(list);
        List<ChannelProduct> lstProduct = pageInfo.getList();
        for(ChannelProduct obj : lstProduct) {
        	obj.setCategoryName(mapCategory.get(obj.getCategoryCode()));
        }
        return pageInfo;		
		/*PageInfo<ChannelProduct> pageInfo = new PageInfo<>(list);
		return pageInfo;*/
	}

	@Override
	public ChannelProduct getChannelProduct(String id) {
		// TODO Auto-generated method stub
		return channelProductDao.selectByPrimaryKey(id);
	}

	@Override
	public PageInfo<ChannelProduct> getPageForBind(ChannelProduct channelProduct, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<ChannelProduct> list = channelProductDao.selectForChannel(channelProduct);
		/*PageInfo<ChannelProduct> pageInfo = new PageInfo<>(list);
		return pageInfo;*/
        List<Select> lstCategory = categoryDao.findSelect(null);
        Map<String, String> mapCategory = new HashMap<>();
        for(Select select : lstCategory) {
        	mapCategory.put(select.getId(), select.getText());
        }
        PageInfo<ChannelProduct> pageInfo = new PageInfo<>(list);
        List<ChannelProduct> lstProduct = pageInfo.getList();
        for(ChannelProduct obj : lstProduct) {
        	obj.setCategoryName(mapCategory.get(obj.getCategoryCode()));
        }
        return pageInfo;		
	}

	@Override
	public void bindChannel(String[] arrayProductCode, String channelCode) {
		// TODO Auto-generated method stub
		if(null == arrayProductCode) {
			return;
		}
		for(String productCode : arrayProductCode) {
			ChannelProduct channelProduct = new ChannelProduct();
			channelProduct.setProductCode(productCode);
			channelProduct.setChannelCode(channelCode);
			channelProductDao.insertChannelProduct(channelProduct);
		}
	}

	@Override
	public ChannelProduct getChannelProductForAll(String id) {
		// TODO Auto-generated method stub
		return channelProductDao.selectById(id);
	}

	@Override
	public void updateChannelProduct(ChannelProduct channelProduct) {
		// TODO Auto-generated method stub
		/*channelProductDao.updateByPrimaryKeySelective(channelProduct);
		channelProductDao.updateForSell(channelProduct);*/	
		if(channelProductDao.updateChannelProduct(channelProduct) < 1) {
			throw new RuntimeException("专有编码不能重复！");
		}
	}

	@Override
	public void updateChannelProductDetails(ChannelProductDetails channelProductDetails) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(channelProductDetails.getId())) {
			channelProductDetailsDao.insertSelective(channelProductDetails);
			return;
		}
		channelProductDetailsDao.updateByPrimaryKeySelective(channelProductDetails);
	}

	@Override
	public ChannelProductDetails getChannelProductDetailsByCode(String channelProductCode) {
		// TODO Auto-generated method stub
		ChannelProductDetails channelProductDetails = new ChannelProductDetails();
		channelProductDetails.setChannelProductCode(channelProductCode);
		return channelProductDetailsDao.selectOne(channelProductDetails);
	}

	@Override
	public void deleteChannelProduct(String id) {
		// TODO Auto-generated method stub
		channelProductDao.deleteByPrimaryKey(id);
	}

	@Override
	public void updateChannelProductSell(ChannelProduct channelProduct) {
		// TODO Auto-generated method stub
		channelProductDao.updateForSell(channelProduct);		
	}

	@Override
	public void setTag(String channelProductCode, String[] tags) {
		// TODO Auto-generated method stub
		if(null == tags) {
			channelProductTagDao.deleteAllTag(channelProductCode);
			return;
		}
		for(String tag : tags) {
			channelProductTagDao.insertTag(channelProductCode, tag);
		}
		
		List<String> lstTag = Arrays.asList(tags);  		
		channelProductTagDao.deleteTag(channelProductCode, lstTag);				
	}

	@Override
	public List<ChannelTag> getTagByProduct(String channelProductCode, String channelCode) {
		// TODO Auto-generated method stub
		return channelTagDao.selectTagByProduct(channelProductCode, channelCode);
	}

}
