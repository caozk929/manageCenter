package com.zjht.manager.entity.channelProduct;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

@Table(name = "t_channel_goods")
public class ChannelGoods implements Serializable {
	private static final long serialVersionUID = 1610183304294127490L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select lpad(nextval('SEQ_CHANNELGOODS'),12,'0')")
    private String id;

    /**
     * 渠道单品编码
     */
    @Column(name = "channel_goods_code")
    private String channelGoodsCode;

    /**
     * 渠道商品编码
     */
    @Column(name = "channel_product_code")
    private String channelProductCode;

    /**
     * 单品编码
     */
    @Column(name = "goods_code")
    private String goodsCode;

    /**
     * 单品价格
     */
    private BigDecimal price;

    /**
     * 渠道单品分配数量
     */
    private Long amount;

    /**
     * 单品logo
     */
    private String logo;
    
	// 渠道专有编码
	@Column(name = "specific_goods_code")
	private String specificGoodsCode;
	
	// 渠道编码
	@Column(name = "channel_code")	
	private String channelCode;
    
    /**
     * 商品编码
     */
    @Transient
    @Column(name = "product_code")
    private String productCode; 
    
    /**
     * 单品数量
     */
    @Transient    
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
     * 获取渠道单品编码
     *
     * @return channel_goods_code - 渠道单品编码
     */
    public String getChannelGoodsCode() {
        return channelGoodsCode;
    }

    /**
     * 设置渠道单品编码
     *
     * @param channelGoodsCode 渠道单品编码
     */
    public void setChannelGoodsCode(String channelGoodsCode) {
        this.channelGoodsCode = channelGoodsCode;
    }

    /**
     * 获取渠道商品编码
     *
     * @return channel_product_code - 渠道商品编码
     */
    public String getChannelProductCode() {
        return channelProductCode;
    }

    /**
     * 设置渠道商品编码
     *
     * @param channelProductCode 渠道商品编码
     */
    public void setChannelProductCode(String channelProductCode) {
        this.channelProductCode = channelProductCode;
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

    /**
     * 获取单品价格
     *
     * @return price - 单品价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置单品价格
     *
     * @param price 单品价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取单品数量
     *
     * @return amount - 单品数量
     */
    public Long getAmount() {
        return amount;
    }

    /**
     * 设置单品数量
     *
     * @param amount 单品数量
     */
    public void setAmount(Long amount) {
        this.amount = amount;
    }

    /**
     * 获取单品logo
     *
     * @return logo - 单品logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * 设置单品logo
     *
     * @param logo 单品logo
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Long getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(Long goodsCount) {
		this.goodsCount = goodsCount;
	}

	public String getSpecificGoodsCode() {
		return specificGoodsCode;
	}

	public void setSpecificGoodsCode(String specificGoodsCode) {
		this.specificGoodsCode = specificGoodsCode;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}  
		
}