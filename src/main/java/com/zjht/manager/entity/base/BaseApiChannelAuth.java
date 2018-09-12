package com.zjht.manager.entity.base;

import javax.persistence.*;
import java.io.Serializable;

public class BaseApiChannelAuth implements Serializable {

    private static final long serialVersionUID = 7055163662331532383L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select lpad(nextval('SEQ_API_CHANNEL_AUTH'),12,'0')")
    private String id;

    /**
     * 渠道id
     */
    @Column(name = "channel_id")
    private String channelId;

    /**
     * 服务id
     */
    @Column(name = "service_id")
    private String serviceId;


    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取渠道id
     *
     * @return channel_id - 渠道id
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * 设置渠道id
     *
     * @param channelId 渠道id
     */
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    /**
     * 获取服务id
     *
     * @return service_id - 服务id
     */
    public String getServiceId() {
        return serviceId;
    }

    /**
     * 设置服务id
     *
     * @param serviceId 服务id
     */
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}