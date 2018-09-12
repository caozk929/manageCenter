package com.zjht.manager.service;

import com.github.pagehelper.PageInfo;
import com.zjht.manager.entity.Select;
import com.zjht.manager.entity.commodity.AttributeKey;
import com.zjht.manager.entity.commodity.Product;
import com.zjht.manager.entity.commodity.ProductAttributeKey;

import java.util.List;

public interface ProductService {
	public PageInfo<Product> getPage(Product product, int pageNum, int pageSize);
	public Product getProduct(String id);
	public Product getFullProduct(String id);
	public Product getFullProductByCode(String productCode);	
	public String addProduct(Product product);
	public String updateProduct(Product product);
	public int deleteProduct(String id);
	public Product getProductByCode(String productCode);		
	
	public void setAttrbuteKey(String productCode, String[] attributeKeys);
	public List<AttributeKey> getAttributeKey(String productCode);
	public int sortAttrbuteKey(String id, Byte attributeKeyOrder);
	public PageInfo<ProductAttributeKey> getAttributeKeyPage(String productCode, int pageNum, int pageSize);
	List<Select> getSelect(String productName);
	
	public int getCountByProduct(String productCode);	
}
