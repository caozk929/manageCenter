package com.zjht.manager.entity.commodity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "t_goods")
public class Goods implements Serializable {
	private static final long serialVersionUID = -2622067472771354295L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select lpad(nextval('SEQ_GOODS'),12,'0')")
    private String id;

    /**
     * 商品编码
     */
    @Column(name = "product_code")
    private String productCode;

    /**
     * 单品编码
     */
    @Column(name = "goods_code")
    private String goodsCode;
    
    /**
     * 单品数量
     */
    @Column(name = "goods_count")
    private Long goodsCount;    

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取商品编码
     *
     * @return product_code - 商品编码
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * 设置商品编码
     *
     * @param productCode 商品编码
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    /**
     * 获取单品编码
     *
     * @return goods_code - 单品编码
     */
    public String getGoodsCode() {
        return goodsCode;
    }

    /**
     * 设置单品编码
     *
     * @param goodsCode 单品编码
     */
    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

	public Long getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(Long goodsCount) {
		this.goodsCount = goodsCount;
	}    
}