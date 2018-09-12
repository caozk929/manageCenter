package com.zjht.manager.entity;

import com.zjht.manager.entity.base.BaseApiChannelPayconfig;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by vip on 2017/9/19.
 */
@Table(name = "t_api_channel_payconfig")
public class ApiChannelPayconfig extends BaseApiChannelPayconfig {

    @Transient
    private String channelName;//扩展字段渠道名称

    /**
     * 获取渠道名称
     * @return
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * 设置渠道名称
     * @param channelName
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
