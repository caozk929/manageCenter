package com.zjht.manager.entity.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class BaseUser implements Serializable {

    private static final long serialVersionUID = 3764508436244930981L;
    /**
     * 用户id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator ="select lpad(nextval('SEQ_USER'),12,'0')")
    private String id;

    /**
     * 昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 用户密码密文形式
     */
    private String password;

    /**
     * 用户手机号
     */
    private String mobile;

    /**
     * 用户姓名
     */
    private String username;

    /**
     * 用户类型1-后台管理用户
     */
    private Integer type;

    /**
     * 用户电子邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 渠道ID
     */
    @Column(name = "channel_id")
    private String channelId;

    /**
     * 获取用户id
     *
     * @return id - 用户id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置用户id
     *
     * @param id 用户id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取昵称
     *
     * @return nick_name - 昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置昵称
     *
     * @param nickName 昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 获取用户密码密文形式
     *
     * @return password - 用户密码密文形式
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置用户密码密文形式
     *
     * @param password 用户密码密文形式
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取用户手机号
     *
     * @return mobile - 用户手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置用户手机号
     *
     * @param mobile 用户手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取用户姓名
     *
     * @return username - 用户姓名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户姓名
     *
     * @param username 用户姓名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取用户类型1-后台管理用户
     *
     * @return type - 用户类型1-后台管理用户
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置用户类型1-后台管理用户
     *
     * @param type 用户类型1-后台管理用户
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取用户电子邮箱
     *
     * @return email - 用户电子邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置用户电子邮箱
     *
     * @param email 用户电子邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
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

    /**
     * 获取渠道ID
     * @return
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * 设置渠道ID
     * @param channelId
     */
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
