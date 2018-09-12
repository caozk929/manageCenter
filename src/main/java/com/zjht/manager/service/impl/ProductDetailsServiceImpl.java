package com.zjht.manager.service.impl;

import com.zjht.manager.dao.ProductDetailsDao;
import com.zjht.manager.entity.commodity.ProductDetails;
import com.zjht.manager.service.ProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

/**
 * Created by vip on 2017/9/30.
 */
@Service
@Transactional//注解事物
public class ProductDetailsServiceImpl implements ProductDetailsService {

    @Autowired
    private ProductDetailsDao productDetailsDao;

    /**
     * 根据产品ID查询
     *
     * @param productId@return
     */
    @Override
    public ProductDetails getByProductId(String productId) {
        ProductDetails pd = new ProductDetails();
        pd.setProductId(productId);
        return productDetailsDao.selectOne(pd);
    }

    /**
     * 删除
     *
     * @param productId
     * @return
     */
    @Override
    public int delete(String productId) {
        Example example = new Example(ProductDetails.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId", productId);
        return productDetailsDao.deleteByExample(example);
    }

    /**
     * 新增
     *
     * @param pd
     * @return
     */
    @Override
    public int add(ProductDetails pd) {
        return productDetailsDao.insert(pd);
    }

    /**
     * 更新
     *
     * @param pd
     * @return
     */
    @Override
    public int update(ProductDetails pd) {
        Example example = new Example(ProductDetails.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId", pd.getProductId());
        return productDetailsDao.updateByExample(pd, example);
    }

    /**
     * 根据产品ID统计
     *
     * @param productId
     * @return
     */
    @Override
    public long countByProductId(String productId) {
        Example example = new Example(ProductDetails.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId", productId);
        return productDetailsDao.selectCountByExample(example);
    }
}
