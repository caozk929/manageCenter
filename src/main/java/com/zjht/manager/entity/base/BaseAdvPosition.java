package com.zjht.manager.entity.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 〈广告位置信息管理〉
 *
 * @author wangpeng
 * @create 2017/9/4
 * @since 1.0.0
 */
public class BaseAdvPosition implements Serializable {

    private static final long serialVersionUID = 2926160124317778052L;

    /**
     * 自增标识编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select lpad(nextval('SEQ_ADV_POSITION'),12,'0')")
    private String id;

    /**
     * 位置名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 所属渠道
     */
    @Column(name = "channel")
    private String channel;

    /**
     * 录入时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 操作员
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 状态(0表示禁用，1表示在用,2表示已删除)
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 广告位置编码
     */
    @Column(name = "position_code")
    private String positionCode;

    /**
     * 备注
     */
    @Column(name = "remarks")
    private String remarks;


    /**
     * 获取自增标识编号
     *
     * @return id - 自增标识编号
     */
    public String getId() {
        return id;
    }

    /**
     * 设置自增标识编号
     *
     * @param id 自增标识编号
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取位置名称
     *
     * @return name - 位置名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置位置名称
     *
     * @param name 位置名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取所属渠道
     *
     * @return channel - 所属渠道
     */
    public String getChannel() {
        return channel;
    }

    /**
     * 设置所属渠道
     *
     * @param channel 所属渠道
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * 获取录入时间
     *
     * @return create_time - 录入时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置录入时间
     *
     * @param createTime 录入时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取操作员
     *
     * @return user_id - 操作员
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置操作员
     *
     * @param userId 操作员
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取状态(0表示禁用，1表示在用)
     *
     * @return status - 状态(0表示禁用，1表示在用)
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态(0表示禁用，1表示在用,2表示已删除)
     *
     * @param status 状态(0表示禁用，1表示在用,2表示已删除)
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取广告位置编码
     *
     * @return position_code - 广告位置编码
     */
    public String getPositionCode() {
        return positionCode;
    }

    /**
     * 设置广告位置编码
     *
     * @param positionCode 广告位置编码
     */
    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    /**
     * 获取备注
     *
     * @return remarks - 备注
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置备注
     *
     * @param remarks 备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


}