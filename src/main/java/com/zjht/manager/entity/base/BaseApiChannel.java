package com.zjht.manager.entity.base;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

public class BaseApiChannel implements Serializable {


    private static final long serialVersionUID = -3679146902259986337L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select lpad(nextval('SEQ_API_CHANNEL'),12,'0')")
    private String id;

    /**
     * 渠道名称
     */
    @Column(name = "channel_name")
    private String channelName;

    /**
     * 渠道编码
     */
    @Column(name = "channel_code")
    private String channelCode;

    /**
     * 渠道访问我们系统的账号
     */
    @Column(name = "channel_account")
    private String channelAccount;

    /**
     * 渠道秘钥
     */
    @Column(name = "channel_key")
    private String channelKey;

    /**
     * 渠道状态（1-正常，0-停用，2-已删除）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

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
     * 获取渠道名称
     *
     * @return channel_name - 渠道名称
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * 设置渠道名称
     *
     * @param channelName 渠道名称
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
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

    /**
     * 获取渠道访问我们系统的账号
     *
     * @return channel_account - 渠道访问我们系统的账号
     */
    public String getChannelAccount() {
        return channelAccount;
    }

    /**
     * 设置渠道访问我们系统的账号
     *
     * @param channelAccount 渠道访问我们系统的账号
     */
    public void setChannelAccount(String channelAccount) {
        this.channelAccount = channelAccount;
    }

    /**
     * 获取渠道秘钥
     *
     * @return channel_key - 渠道秘钥
     */
    public String getChannelKey() {
        return channelKey;
    }

    /**
     * 设置渠道秘钥
     *
     * @param channelKey 渠道秘钥
     */
    public void setChannelKey(String channelKey) {
        this.channelKey = channelKey;
    }

    /**
     * 获取渠道状态（1-正常，0-停用）
     *
     * @return status - 渠道状态（1-正常，0-停用）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置渠道状态（1-正常，0-停用）
     *
     * @param status 渠道状态（1-正常，0-停用）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取备注
     *
     * @return remarks - 备注
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置备注
     *
     * @param remarks 备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }




}