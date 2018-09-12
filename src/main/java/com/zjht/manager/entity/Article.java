package com.zjht.manager.entity;

import com.zjht.manager.entity.base.BaseArticle;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by vip on 2017/9/13.
 */
@Table(name = "t_article")
public class Article extends BaseArticle {

    @Transient
    private String searchDate;//扩展字段，搜索时间开始和结束日期

    @Transient
    private String columnTitle;//扩展字段，栏目标题

    @Transient
    private String channelName;//扩展字段渠道名称

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }

    public String getColumnTitle() {
        return columnTitle;
    }

    public void setColumnTitle(String columnTitle) {
        this.columnTitle = columnTitle;
    }
}
