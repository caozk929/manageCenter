package com.zjht.manager.entity.channelProduct;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_channel_product_tag")
public class ChannelProductTag implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2379146898125690087L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select lpad(nextval('SEQ_CHANNELPRODUCTTAG'),12,'0')")
    private String id;

    /**
     * 渠道商品编码
     */
    @Column(name = "channel_product_code")
    private String channelProductCode;

    /**
     * 标签主键
     */
    @Column(name = "tag_id")
    private String tagId;

    /**
     * 标签顺序
     */
    @Column(name = "tag_order")
    private Byte tagOrder;

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
     * 获取标签主键
     *
     * @return tag_id - 标签主键
     */
    public String getTagId() {
        return tagId;
    }

    /**
     * 设置标签主键
     *
     * @param tagId 标签主键
     */
    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    /**
     * 获取标签顺序
     *
     * @return tag_order - 标签顺序
     */
    public Byte getTagOrder() {
        return tagOrder;
    }

    /**
     * 设置标签顺序
     *
     * @param tagOrder 标签顺序
     */
    public void setTagOrder(Byte tagOrder) {
        this.tagOrder = tagOrder;
    }
}