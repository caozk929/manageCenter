package com.zjht.manager.entity;

import com.zjht.manager.entity.base.BaseAdvertise;

import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "t_advertise")
public class Advertise extends BaseAdvertise {

    /**
     * 录入开始时间
     */
    @Transient
    private String beginDate;

    /**
     * 录入结束时间
     */
    @Transient
    private String endDate;

    /**
     * 广告位置名称
     */
    @Transient
    private String advPositionName;

    /**
     * 所属渠道
     */
    @Transient
    private String channel;

    /**
     * 所属渠道
     */
    @Transient
    private String channelName;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getAdvPositionName() {
        return advPositionName;
    }

    public void setAdvPositionName(String advPositionName) {
        this.advPositionName = advPositionName;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }


}