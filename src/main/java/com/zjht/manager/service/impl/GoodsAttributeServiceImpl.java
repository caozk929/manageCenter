package com.zjht.manager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjht.manager.dao.GoodsAttributeDao;
import com.zjht.manager.entity.commodity.GoodsAttribute;
import com.zjht.manager.service.GoodsAttributeService;

@Service
@Transactional
public class GoodsAttributeServiceImpl implements GoodsAttributeService {
	
	@Resource
	private GoodsAttributeDao goodsAttributeDao;

	@Override
	public List<GoodsAttribute> getGoodsAttributeByProduct(String categoryCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGoodsAttribute(String goodsCode, List<GoodsAttribute> lstGoodsAttribute) {
		// TODO Auto-generated method stub
		GoodsAttribute record = new GoodsAttribute();
		record.setGoodsCode(goodsCode);
		goodsAttributeDao.delete(record);
        if(null == lstGoodsAttribute || lstGoodsAttribute.size() == 0) {
        	return;
        }
        for(GoodsAttribute goodsAttribute : lstGoodsAttribute) {
        	goodsAttributeDao.insertSelective(goodsAttribute);
        }		
	}

	@Override
	public List<GoodsAttribute> getGoodsAttributeByGoodsCode(String goodsCode) {
		GoodsAttribute goodsAttribute = new GoodsAttribute();
		goodsAttribute.setGoodsCode(goodsCode);
		return goodsAttributeDao.select(goodsAttribute);
	}

}
