package com.zjht.manager.entity;

import com.zjht.manager.entity.base.BaseApiChannel;

import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "t_api_channel")
public class ApiChannel extends BaseApiChannel {
    //渠道的主题，用于增删改查时的消息发布
    public static final String TOPIC = "manager.api.channel";
    @Transient
    private  String orderByClause ;

    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * 状态
     */
    public static enum Status {

        DISABLE(0,"停用"),
        NORMAL(1,"正常"),
        DELETED(2,"已删除");

        private Status(int status, String name) {
            this.status = status;
            this.name = name;
        }

        private String name;
        private int status;
        public static String getName(int status) {
            for (Status c : Status.values()) {
                if (c.getStatus() == status) {
                    return c.name;
                }
            }
            return null;
        }
        public int getStatus() {
            return status;
        }
        public void setStatus(int status) {
            this.status = status;
        }
    }
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }
}