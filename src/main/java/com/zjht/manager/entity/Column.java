package com.zjht.manager.entity;

import com.zjht.manager.entity.base.BaseColumn;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by vip on 2017/9/15.
 */
@Table(name = "t_column")
public class Column extends BaseColumn {

    @Transient
    private String channelName;//扩展字段渠道名称

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
