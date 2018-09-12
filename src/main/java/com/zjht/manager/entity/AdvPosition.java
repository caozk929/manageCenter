package com.zjht.manager.entity;

import com.zjht.manager.entity.base.BaseAdvPosition;

import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "t_adv_position")
public class AdvPosition extends BaseAdvPosition {

    /**
     * 录入开始时间
     */
    @Transient
    private  String beginDate;

    /**
     * 录入结束时间
     */
    @Transient
    private  String endDate;

    /**
     * 渠道名称
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

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

}