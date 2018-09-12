package com.zjht.manager.entity.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 〈广告图片信息管理〉
 *
 * @author wangpeng
 * @create 2017/9/4
 * @since 1.0.0
 */
public class BaseAdvertise implements Serializable {

    private static final long serialVersionUID = 9157723859310162516L;


    /**
     * 标识编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select lpad(nextval('SEQ_ADVERTISE'),12,'0')")
    private String advId;

    /**
     * 广告摆放位置标识编号
     */
    @Column(name = "adv_position_id")
    private String advPositionId;

    /**
     * 名称
     */
    @Column(name = "adv_name")
    private String advName;

    /**
     * 图片地址
     */
    @Column(name = "adv_img")
    private String advImg;

    /**
     * 链接地址
     */
    @Column(name = "adv_link")
    private String advLink;

    /**
     * 链接提示
     */
    @Column(name = "adv_tip")
    private String advTip;

    /**
     * 链接目标(_self,_blank..)
     */
    @Column(name = "adv_target")
    private String advTarget;

    /**
     * 点击次数
     */
    @Column(name = "adv_click")
    private Integer advClick;

    /**
     * 数值越大，排越前
     */
    @Column(name = "adv_sort")
    private Integer advSort;

    /**
     * 状态：0表示不可用，1表示在用,2表示已删除
     */
    @Column(name = "adv_status")
    private Integer advStatus;

    /**
     * 添加时间
     */
    @Column(name = "adv_create_time")
    private Date advCreateTime;

    /**
     * 修改时间
     */
    @Column(name = "adv_update_time")
    private Date advUpdateTime;

    /**
     * 管理员标识编号
     */
    @Column(name = "user_id")
    private Integer userId;



    /**
     * 获取标识编号
     *
     * @return adv_id - 标识编号
     */
    public String getAdvId() {
        return advId;
    }

    /**
     * 设置标识编号
     *
     * @param advId 标识编号
     */
    public void setAdvId(String advId) {
        this.advId = advId;
    }

    /**
     * 获取广告摆放位置标识编号
     *
     * @return adv_position_id - 广告摆放位置标识编号
     */
    public String getAdvPositionId() {
        return advPositionId;
    }

    /**
     * 设置广告摆放位置标识编号
     *
     * @param advPositionId 广告摆放位置标识编号
     */
    public void setAdvPositionId(String advPositionId) {
        this.advPositionId = advPositionId;
    }

    /**
     * 获取名称
     *
     * @return adv_name - 名称
     */
    public String getAdvName() {
        return advName;
    }

    /**
     * 设置名称
     *
     * @param advName 名称
     */
    public void setAdvName(String advName) {
        this.advName = advName;
    }

    /**
     * 获取图片地址
     *
     * @return adv_img - 图片地址
     */
    public String getAdvImg() {
        return advImg;
    }

    /**
     * 设置图片地址
     *
     * @param advImg 图片地址
     */
    public void setAdvImg(String advImg) {
        this.advImg = advImg;
    }

    /**
     * 获取链接地址
     *
     * @return adv_link - 链接地址
     */
    public String getAdvLink() {
        return advLink;
    }

    /**
     * 设置链接地址
     *
     * @param advLink 链接地址
     */
    public void setAdvLink(String advLink) {
        this.advLink = advLink;
    }

    /**
     * 获取链接提示
     *
     * @return adv_tip - 链接提示
     */
    public String getAdvTip() {
        return advTip;
    }

    /**
     * 设置链接提示
     *
     * @param advTip 链接提示
     */
    public void setAdvTip(String advTip) {
        this.advTip = advTip;
    }

    /**
     * 获取链接目标(_self,_blank..)
     *
     * @return adv_target - 链接目标(_self,_blank..)
     */
    public String getAdvTarget() {
        return advTarget;
    }

    /**
     * 设置链接目标(_self,_blank..)
     *
     * @param advTarget 链接目标(_self,_blank..)
     */
    public void setAdvTarget(String advTarget) {
        this.advTarget = advTarget;
    }

    /**
     * 获取点击次数
     *
     * @return adv_click - 点击次数
     */
    public Integer getAdvClick() {
        return advClick;
    }

    /**
     * 设置点击次数
     *
     * @param advClick 点击次数
     */
    public void setAdvClick(Integer advClick) {
        this.advClick = advClick;
    }

    /**
     * 获取数值越大，排越前
     *
     * @return adv_sort - 数值越大，排越前
     */
    public Integer getAdvSort() {
        return advSort;
    }

    /**
     * 设置数值越大，排越前
     *
     * @param advSort 数值越大，排越前
     */
    public void setAdvSort(Integer advSort) {
        this.advSort = advSort;
    }

    /**
     * 获取状态：0表示不可用，1表示在用,2表示已删除
     *
     * @return adv_status - 状态：0表示不可用，1表示在用,2表示已删除
     */
    public Integer getAdvStatus() {
        return advStatus;
    }

    /**
     * 设置状态：0表示不可用，1表示在用,2表示已删除
     *
     * @param advStatus 状态：0表示不可用，1表示在用,2表示已删除
     */
    public void setAdvStatus(Integer advStatus) {
        this.advStatus = advStatus;
    }

    /**
     * 获取添加时间
     *
     * @return adv_create_time - 添加时间
     */
    public Date getAdvCreateTime() {
        return advCreateTime;
    }

    /**
     * 设置添加时间
     *
     * @param advCreateTime 添加时间
     */
    public void setAdvCreateTime(Date advCreateTime) {
        this.advCreateTime = advCreateTime;
    }

    /**
     * 获取修改时间
     *
     * @return adv_update_time - 修改时间
     */
    public Date getAdvUpdateTime() {
        return advUpdateTime;
    }

    /**
     * 设置修改时间
     *
     * @param advUpdateTime 修改时间
     */
    public void setAdvUpdateTime(Date advUpdateTime) {
        this.advUpdateTime = advUpdateTime;
    }

    /**
     * 获取管理员标识编号
     *
     * @return user_id - 管理员标识编号
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置管理员标识编号
     *
     * @param userId 管理员标识编号
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }


}