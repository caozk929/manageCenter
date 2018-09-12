package com.zjht.manager.api.dto;

import com.alibaba.fastjson.JSONObject;

/**
 * 事件数据传输对象
 * 用于消息传输中数据封装
 * @author caozhaokui
 * @create 2017-10-13 14:55
 */
public class EventData<T> {
    /**
     * 数据操作类型，insert、delete、update
     */
    private String opertType;
    /**
     * 数据value
     */
    private T value;

    public EventData(String opertType, T value) {
        this.opertType = opertType;
        this.value = value;
    }

    public enum OperatType{
        insert(),
        delete(),
        update();
    }
    public String getOpertType() {
        return opertType;
    }

    public void setOpertType(String opertType) {
        this.opertType = opertType;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
