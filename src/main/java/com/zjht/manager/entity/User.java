package com.zjht.manager.entity;

import com.zjht.manager.entity.base.BaseUser;

import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "t_user")
public class User extends BaseUser{

    private static final long serialVersionUID = -5328049940610750041L;

    @Transient
    private String[] roleIds;//角色Id

    @Transient
    private String channelName;//扩展字段渠道名称

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String[] roleIds) {
        this.roleIds = roleIds;
    }
}