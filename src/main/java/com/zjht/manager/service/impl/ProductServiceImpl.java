package com.zjht.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.manager.dao.AttributeKeyDao;
import com.zjht.manager.dao.CategoryDao;
import com.zjht.manager.dao.ProductAttributeKeyDao;
import com.zjht.manager.dao.ProductDao;
import com.zjht.manager.entity.Select;
import com.zjht.manager.entity.commodity.AttributeKey;
import com.zjht.manager.entity.commodity.Product;
import com.zjht.manager.entity.commodity.ProductAttributeKey;
import com.zjht.manager.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	
	@Resource
	ProductDao productDao;	
	@Resource
	AttributeKeyDao attributeKeyDao;
	@Resource
	ProductAttributeKeyDao productAttributeKeyDao;
	@Resource
	CategoryDao categoryDao; 

	@Override
	public PageInfo<Product> getPage(Product product, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
        PageHelper.startPage(pageNum,pageSize);
        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(product.getProductName())){
            criteria.andLike("productName","%" + product.getProductName() + "%");
        }
        if(StringUtils.isNotBlank(product.getCategoryCode())){
        	criteria.andEqualTo("categoryCode", product.getCategoryCode());
        }        
        List<Product> list = productDao.selectByExample(example);
        
        List<Select> lstCategory = categoryDao.findSelect(null);
        Map<String, String> mapCategory = new HashMap<>();
        for(Select select : lstCategory) {
        	mapCategory.put(select.getId(), select.getText());
        }
        PageInfo<Product> pageInfo = new PageInfo<>(list);
        List<Product> lstProduct = pageInfo.getList();
        for(Product obj : lstProduct) {
        	obj.setCategoryName(mapCategory.get(obj.getCategoryCode()));
        }
        return pageInfo;
	}

	@Override
	public Product getProduct(String id) {
		// TODO Auto-generated method stub
		return productDao.selectByPrimaryKey(id);
	}

	@Override
	public String addProduct(Product product) {
		// TODO Auto-generated method stub
		product.setId(productDao.findId());
		productDao.insertProduct(product);
		return product.getId();
	}

	@Override
	public String updateProduct(Product product) {
		// TODO Auto-generated method stub
		int flat = productDao.updateProduct(product);
		if(flat < 1) {
			productDao.deleteByPrimaryKey(product);
			productDao.insertProduct(product);
		}
		return product.getId();
	}

	@Override
	public int deleteProduct(String id) {
		// TODO Auto-generated method stub
		return productDao.deleteByPrimaryKey(id);
	}

	@Override
	public void setAttrbuteKey(String productCode, String[] attributeKeys) {
		// TODO Auto-generated method stub
		if(null == attributeKeys) {
			productAttributeKeyDao.deleteAllAttributeKey(productCode);
			return;
		}
		for(String attributeKeyCode : attributeKeys) {
			productAttributeKeyDao.insertAttributeKey(productCode, attributeKeyCode);
		}
		
		List<String> lstAttributeKey = Arrays.asList(attributeKeys);  		
		productAttributeKeyDao.deleteAttributeKey(productCode, lstAttributeKey);		
	}

	@Override
	public List<AttributeKey> getAttributeKey(String productCode) {
		// TODO Auto-generated method stub
		return attributeKeyDao.selectAttributeKeybyProduct(productCode);
	}

	@Override
	public int sortAttrbuteKey(String id, Byte attributeKeyOrder) {
		// TODO Auto-generated method stub
		return productAttributeKeyDao.updateAttributeKeyBySort(id, attributeKeyOrder);
	}

	@Override
	public PageInfo<ProductAttributeKey> getAttributeKeyPage(String productCode, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<ProductAttributeKey> list = productAttributeKeyDao.selectByProduct(productCode);
		PageInfo<ProductAttributeKey> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public List<Select> getSelect(String productName) {
		// TODO Auto-generated method stub
		return productDao.findSelect(productName);
	}
	
	@Override
	public Product getProductByCode(String productCode) {
		// TODO Auto-generated method stub		
		Product product = new Product();
		product.setProductCode(productCode);
		return productDao.selectOne(product);
	}

	@Override
	public Product getFullProduct(String id) {
		// TODO Auto-generated method stub
		return productDao.selectById(id);
	}

	@Override
	public Product getFullProductByCode(String productCode) {
		// TODO Auto-generated method stub
		return productDao.selectByCode(productCode);
	}

	@Override
	public int getCountByProduct(String productCode) {
		// TODO Auto-generated method stub
		return productDao.selectCountByProduct(productCode);
	}

}
