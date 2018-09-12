package com.zjht.manager.entity.channelProduct;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "t_channel_tag")
public class ChannelTag implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8328638554778211704L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select lpad(nextval('SEQ_CHANNELTAG'),12,'0')")
    private String id;

    /**
     * 标签名称
     */
    @Column(name = "tag_name")
    private String tagName;

    /**
     * 标签logo
     */
    @Column(name = "tag_logo")
    private String tagLogo;

    /**
     * 渠道编码
     */
    @Column(name = "channel_code")
    private String channelCode;

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
     * 获取标签名称
     *
     * @return tag_name - 标签名称
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * 设置标签名称
     *
     * @param tagName 标签名称
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    /**
     * 获取标签logo
     *
     * @return tag_logo - 标签logo
     */
    public String getTagLogo() {
        return tagLogo;
    }

    /**
     * 设置标签logo
     *
     * @param tagLogo 标签logo
     */
    public void setTagLogo(String tagLogo) {
        this.tagLogo = tagLogo;
    }

    /**
     * 获取渠道编码
     *
     * @return channel_code - 渠道编码
     */
    public String getChannelCode() {
        return channelCode;
    }

    /**
     * 设置渠道编码
     *
     * @param channelCode 渠道编码
     */
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }
}