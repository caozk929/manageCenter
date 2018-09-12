package com.zjht.manager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.manager.dao.GoodsAttributeDao;
import com.zjht.manager.dao.GoodsDao;
import com.zjht.manager.dao.ProductDao;
import com.zjht.manager.entity.commodity.Goods;
import com.zjht.manager.entity.commodity.GoodsAttribute;
import com.zjht.manager.service.GoodsService;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {
	
	@Resource
	private GoodsDao goodsDao;
	@Resource
	private GoodsAttributeDao goodsAttributeDao;
	@Resource
	private ProductDao productDao;

	@Override
	public Goods addGoods(String productCode,String goodsCount) {
		// TODO Auto-generated method stub
		String id = goodsDao.findId();
		goodsDao.insertGoods(id,productCode,goodsCount);
		return goodsDao.selectByPrimaryKey(id);
	}

	@Override
	public PageInfo<Map<String, String>> getPageByProduct(String productCode, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		Goods goods = new Goods();
		goods.setProductCode(productCode);
		List<Goods> list = goodsDao.select(goods);
		
		if(null == list || list.isEmpty()) {
			PageInfo<Map<String, String>> pageInfo = new PageInfo<>(new ArrayList<Map<String, String>>());
			return pageInfo;			
		}

		List<String> lstGoodsCode = new ArrayList<>();
		for (Goods obj : list) {
			lstGoodsCode.add(obj.getGoodsCode());
		}
		List<GoodsAttribute> lstGoodsAttribute = goodsAttributeDao.selectByGoods(lstGoodsCode);
		Map<String, Map<String, String>> mapFull = new HashMap<>();
		for(GoodsAttribute obj : lstGoodsAttribute) {
			Map<String, String> pojo = mapFull.get(obj.getGoodsCode());
			if(null == pojo) {
				pojo = new HashMap<>();	
				//pojo.put("goodsCode", obj.getGoodsCode());
			}
			pojo.put("attr_" + obj.getAttributeKeyCode(), obj.getAttributeValue());			
			mapFull.put(obj.getGoodsCode(), pojo);
		}

		List<Map<String, String>> lstPojo = new ArrayList<>();
		for (Goods obj : list) {
			Map<String, String> pojo = new HashMap<>();	
			pojo.put("goodsCode", obj.getGoodsCode());
			pojo.put("id", obj.getId());
			pojo.put("productCode", obj.getProductCode());
			if (null != obj.getGoodsCount()) {
				pojo.put("goodsCount", obj.getGoodsCount() + "");
			}
			if(null != mapFull.get(obj.getGoodsCode())) {
				pojo.putAll(mapFull.get(obj.getGoodsCode()));
			}
			lstPojo.add(pojo);
			/*if(null == mapFull.get(obj.getGoodsCode())) {
				Map<String, String> pojo = new HashMap<>();	
				pojo.put("goodsCode", obj.getGoodsCode());
				lstPojo.add(pojo);
			} else {
				lstPojo.add(mapFull.get(obj.getGoodsCode()));
			}*/
		}		

		PageInfo<Map<String, String>> pageInfo = new PageInfo<>(lstPojo);
		return pageInfo;
	}

	@Override
	public void deleteGoods(String goodsCode) {
		// TODO Auto-generated method stub
		Goods goods = new Goods();
		goods.setGoodsCode(goodsCode);
		goodsDao.delete(goods);
		GoodsAttribute record = new GoodsAttribute();
		record.setGoodsCode(goodsCode);
		goodsAttributeDao.delete(record);
	}

	@Override
	public void updateGoods(Goods goods) {
		// TODO Auto-generated method stub
		goodsDao.updateByPrimaryKeySelective(goods);
		productDao.updateCount(goods.getProductCode());
	}

	@Override
	public Goods selectGoodsByCode(String goodsCode) {
        Goods goods = new Goods();
        goods.setGoodsCode(goodsCode);
		return goodsDao.selectOne(goods);
	}

}
