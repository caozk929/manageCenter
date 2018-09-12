package com.zjht.manager.entity.channelProduct;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "t_channel_product_details")
public class ChannelProductDetails implements Serializable {

	private static final long serialVersionUID = -4832878617784559811L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select lpad(nextval('SEQ_CHANNELPRODUCTDETAILS'),12,'0')")
    private String id;

    /**
     * 渠道商品编码
     */
    @Column(name = "channel_product_code")
    private String channelProductCode;

    /**
     * 渠道商品详情
     */
    private String details;

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
     * 获取渠道商品详情
     *
     * @return details - 渠道商品详情
     */
    public String getDetails() {
        return details;
    }

    /**
     * 设置渠道商品详情
     *
     * @param details 渠道商品详情
     */
    public void setDetails(String details) {
        this.details = details;
    }
}