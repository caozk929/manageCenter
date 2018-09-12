package com.zjht.manager.entity;

import com.zjht.manager.entity.base.BaseLog;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_log")
public class Log extends BaseLog {

    private static final long serialVersionUID = -2712091717138046548L;
    /**
     * 创建时间查询开始
     */
    @Transient
    private String createTimeBegin;
    /**
     * 创建时间查询结束
     */
    @Transient
    private String createTimeEnd;

    /**
     * 用户名
     */
    @Transient
    private  String username;

    /**
     * 渠道名名
     */
    @Transient
    private  String channelName;

    /**
     * 接口名
     */
    @Transient
    private  String serviceName;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public String getCreateTimeBegin() {
        return createTimeBegin;
    }

    public void setCreateTimeBegin(String createTimeBegin) {
        this.createTimeBegin = createTimeBegin;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}