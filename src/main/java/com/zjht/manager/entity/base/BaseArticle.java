package com.zjht.manager.entity.base;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_article")
public class BaseArticle implements Serializable {
    /**
     * 文章标识id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator ="select lpad(nextval('SEQ_ARTICLE'),12,'0')")
    private String id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章状态（0-停用; 1-启用）
     */
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 发布时间
     */
    @Column(name = "publish_time")
    private Date publishTime;

    /**
     * 文章排序，数值越小越在前
     */
    private Integer sort;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 栏目Id
     */
    @Column(name = "column_id")
    private String columnId;

    /**
     * 渠道Id
     */
    @Column(name = "channel_id")
    private String channelId;

    /**
     * 简介
     */
    private String synopsis;

    /**
     * 缩略图URL
     */
    @Column(name = "synopsis_url")
    private String synopsisUrl;

    private static final long serialVersionUID = 1L;


    /**
     * 获取栏目Id
     * @return
     */
    public String getColumnId() {
        return columnId;
    }

    /**
     * 设置栏目Id
     * @param columnId
     */
    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    /**
     * 获取文章标识id
     *
     * @return id - 文章标识id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置文章标识id
     *
     * @param id 文章标识id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取文章标题
     *
     * @return title - 文章标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置文章标题
     *
     * @param title 文章标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取文章状态（0-停用; 1-启用）
     *
     * @return status - 文章状态（0-停用; 1-启用）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置文章状态（0-停用; 1-启用）
     *
     * @param status 文章状态（0-停用; 1-启用）
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
     * 获取发布时间
     *
     * @return publish_time - 发布时间
     */
    public Date getPublishTime() {
        return publishTime;
    }

    /**
     * 设置发布时间
     *
     * @param publishTime 发布时间
     */
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    /**
     * 获取文章排序，数值越小越在前
     *
     * @return sort - 文章排序，数值越小越在前
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置文章排序，数值越小越在前
     *
     * @param sort 文章排序，数值越小越在前
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取文章内容
     *
     * @return content - 文章内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置文章内容
     *
     * @param content 文章内容
     */
    public void setContent(String content) {
        this.content = content;
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

    /**
     * 获取简介
     * @return
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * 设置简介
     * @param synopsis
     */
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    /**
     * 获取缩略图URL
     * @return
     */
    public String getSynopsisUrl() {
        return synopsisUrl;
    }

    /**
     * 设置缩略图URL
     * @param synopsisUrl
     */
    public void setSynopsisUrl(String synopsisUrl) {
        this.synopsisUrl = synopsisUrl;
    }
}