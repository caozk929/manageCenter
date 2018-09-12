package com.zjht.manager.service;

import com.zjht.manager.entity.commodity.ProductDetails;

/**
 * Created by vip on 2017/9/30.
 */
public interface ProductDetailsService {

    /**
     * 根据产品ID查询
     * @param productId
     * @return
     */
    ProductDetails getByProductId(String productId);

    /**
     * 删除
     * @param productId
     * @return
     */
    int delete(String productId);

    /**
     * 新增
     * @param pd
     * @return
     */
    int add(ProductDetails pd);

    /**
     * 更新
     * @param pd
     * @return
     */
    int update(ProductDetails pd);

    /**
     * 根据产品ID统计
     * @param productId
     * @return
     */
    long countByProductId(String productId);

}
