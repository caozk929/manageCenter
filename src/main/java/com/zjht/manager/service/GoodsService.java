package com.zjht.manager.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zjht.manager.entity.commodity.Goods;

public interface GoodsService {
	public PageInfo<Map<String, String>> getPageByProduct(String productCode, int pageNum, int pageSize);
	public Goods addGoods(String productCode,String goodsCount);
	public void deleteGoods(String goodsCode);
	public void updateGoods(Goods goods);
	public Goods selectGoodsByCode(String goodsCode);
}
