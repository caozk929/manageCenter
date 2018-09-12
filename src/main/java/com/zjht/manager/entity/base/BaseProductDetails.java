package com.zjht.manager.entity.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "t_product_details")
public class BaseProductDetails implements Serializable {
    /**
     * 商品Id
     */
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String productId;

    /**
     * 商品详情
     */
    private String details;

    private static final long serialVersionUID = 1L;

    /**
     * 获取商品Id
     *
     * @return product_id - 商品Id
     */
    public String getProductId() {
        return productId;
    }

    /**
     * 设置商品Id
     *
     * @param productId 商品Id
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * 获取商品详情
     *
     * @return details - 商品详情
     */
    public String getDetails() {
        return details;
    }

    /**
     * 设置商品详情
     *
     * @param details 商品详情
     */
    public void setDetails(String details) {
        this.details = details;
    }
}