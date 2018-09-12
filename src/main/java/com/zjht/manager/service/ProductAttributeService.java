package com.zjht.manager.service;

import java.util.List;

import com.zjht.manager.entity.commodity.Product;
import com.zjht.manager.entity.commodity.ProductAttribute;

public interface ProductAttributeService {
	public List<ProductAttribute> getProductAttributeByProduct(Product product);
	public void setProductAttribute(String productCode, List<ProductAttribute> lstProductAttribute);
}
