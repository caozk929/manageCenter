package com.zjht.manager.entity.base;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_column")
public class BaseColumn implements Serializable {
    /**
     * 栏目标识id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator ="select lpad(nextval('SEQ_COLUMN'),12,'0')")
    private String id;

    /**
     * 栏目标题
     */
    private String title;

    /**
     * 栏目状态（0-停用; 1-启用）
     */
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 栏目排序，数值越小越在前
     */
    private Integer sort;

    /**
     * 栏目备注
     */
    private String remarks;

    /**
     * 栏目编码
     */
    private String code;

    /**
     * 渠道Id
     */
    @Column(name = "channel_id")
    private String channelId;

    private static final long serialVersionUID = 1L;

    /**
     * 获取栏目标识id
     *
     * @return id - 栏目标识id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置栏目标识id
     *
     * @param id 栏目标识id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取栏目标题
     *
     * @return title - 栏目标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置栏目标题
     *
     * @param title 栏目标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取栏目状态（0-停用; 1-启用）
     *
     * @return status - 栏目状态（0-停用; 1-启用）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置栏目状态（0-停用; 1-启用）
     *
     * @param status 栏目状态（0-停用; 1-启用）
     */
    public void setStatus(Integer status) {
        this.status = status;
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

    /**
     * 获取栏目排序，数值越小越在前
     *
     * @return sort - 栏目排序，数值越小越在前
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置栏目排序，数值越小越在前
     *
     * @param sort 栏目排序，数值越小越在前
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取栏目备注
     *
     * @return remarks - 栏目备注
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置栏目备注
     *
     * @param remarks 栏目备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 获取栏目编码
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置栏目编码
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取渠道ID
     * @return
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * 设置渠道ID
     * @param channelId
     */
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}