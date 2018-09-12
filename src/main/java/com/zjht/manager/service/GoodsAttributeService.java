package com.zjht.manager.service;

import java.util.List;
import com.zjht.manager.entity.commodity.GoodsAttribute;

public interface GoodsAttributeService {
	public List<GoodsAttribute> getGoodsAttributeByProduct(String categoryCode);

	public void setGoodsAttribute(String goodsCode, List<GoodsAttribute> lstGoodsAttribute);

	List<GoodsAttribute> getGoodsAttributeByGoodsCode(String goodsCode);
}
