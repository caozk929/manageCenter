package com.zjht.manager.entity.channelProduct;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "t_channel_goods_tag")
public class ChannelGoodsTag implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2429533903613394423L;

	/**
     * id
     */
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select lpad(nextval('SEQ_CHANNELGOODSTAG'),12,'0')")
    private String id;

    /**
     * 渠道单品编码
     */
    @Column(name = "channel_goods_code")
    private String channelGoodsCode;

    /**
     * 标签id
     */
    @Column(name = "tag_id")
    private String tagId;

    /**
     * 获取id
     *
     * @return id - id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
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
     * 获取标签id
     *
     * @return tag_id - 标签id
     */
    public String getTagId() {
        return tagId;
    }

    /**
     * 设置标签id
     *
     * @param tagId 标签id
     */
    public void setTagId(String tagId) {
        this.tagId = tagId;
    }
}