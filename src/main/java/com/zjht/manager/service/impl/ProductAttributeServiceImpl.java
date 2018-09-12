package com.zjht.manager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjht.manager.dao.ProductAttributeDao;
import com.zjht.manager.entity.commodity.Product;
import com.zjht.manager.entity.commodity.ProductAttribute;
import com.zjht.manager.service.ProductAttributeService;

@Service
@Transactional
public class ProductAttributeServiceImpl implements ProductAttributeService {
	
	@Resource
	private ProductAttributeDao productAttributeDao;

	@Override
	public List<ProductAttribute> getProductAttributeByProduct(Product product) {
		// TODO Auto-generated method stub
		return productAttributeDao.selectByProduct(product);
	}

	@Override
	public void setProductAttribute(String productCode, List<ProductAttribute> lstProductAttribute) {
		// TODO Auto-generated method stub
		productAttributeDao.deleteByProduct(productCode);
        if(null == lstProductAttribute || lstProductAttribute.size() == 0) {
        	return;
        }
        for(ProductAttribute productAttribute : lstProductAttribute) {
        	productAttributeDao.insertSelective(productAttribute);
        }
	}

}
