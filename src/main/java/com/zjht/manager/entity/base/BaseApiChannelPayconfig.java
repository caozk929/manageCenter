package com.zjht.manager.entity.base;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_api_channel_payconfig")
public class BaseApiChannelPayconfig implements Serializable {
    /**
     * id编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select lpad(nextval('SEQ_API_CHANNEL_PAYCONFIG'),12,'0')")
    private String id;

    /**
     * 渠道id
     */
    @Column(name = "channel_id")
    private String channelId;

    /**
     * 配置名称
     */
    @Column(name = "config_name")
    private String configName;

    /**
     * 配置代码
     */
    @Column(name = "config_code")
    private String configCode;

    /**
     * 配置描述
     */
    @Column(name = "config_remarks")
    private String configRemarks;

    /**
     * 版本
     */
    private String version;

    /**
     * 字符编码
     */
    private String charset;

    /**
     * 货币类型
     */
    private String currency;

    /**
     * 合作商代码
     */
    @Column(name = "partner_code")
    private String partnerCode;

    /**
     * 支付帐号
     */
    @Column(name = "pay_account_name")
    private String payAccountName;

    /**
     * 私钥密码
     */
    @Column(name = "private_key_pwd")
    private String privateKeyPwd;

    /**
     * 私钥文件名
     */
    @Column(name = "private_key_file_name")
    private String privateKeyFileName;

    /**
     * 公钥文件名
     */
    @Column(name = "public_key_file_name")
    private String publicKeyFileName;

    /**
     * 签名类型
     */
    @Column(name = "sign_type")
    private String signType;

    /**
     * 支付网关地址
     */
    @Column(name = "pay_url")
    private String payUrl;

    /**
     * 前台回调URL
     */
    @Column(name = "return_url")
    private String returnUrl;

    /**
     * 后台回调URL
     */
    @Column(name = "notify_url")
    private String notifyUrl;

    /**
     * 扩展字段一
     */
    private String extend1;

    /**
     * 扩展字段二
     */
    private String extend2;

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
     * 操作管理员
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 渠道状态1：可用，2停用
     */
    private int status;

    private static final long serialVersionUID = 1L;

    /**
     * 获取渠道状态
     * @return
     */
    public int getStatus() {
        return status;
    }

    /**
     * 设置渠道状态
     * @param status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 获取id编号
     *
     * @return id - id编号
     */
    public String getId() {
        return id;
    }

    /**
     * 设置id编号
     *
     * @param id id编号
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
     * 获取配置名称
     *
     * @return config_name - 配置名称
     */
    public String getConfigName() {
        return configName;
    }

    /**
     * 设置配置名称
     *
     * @param configName 配置名称
     */
    public void setConfigName(String configName) {
        this.configName = configName;
    }

    /**
     * 获取配置代码
     *
     * @return config_code - 配置代码
     */
    public String getConfigCode() {
        return configCode;
    }

    /**
     * 设置配置代码
     *
     * @param configCode 配置代码
     */
    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }

    /**
     * 获取配置描述
     *
     * @return config_remarks - 配置描述
     */
    public String getConfigRemarks() {
        return configRemarks;
    }

    /**
     * 设置配置描述
     *
     * @param configRemarks 配置描述
     */
    public void setConfigRemarks(String configRemarks) {
        this.configRemarks = configRemarks;
    }

    /**
     * 获取版本
     *
     * @return version - 版本
     */
    public String getVersion() {
        return version;
    }

    /**
     * 设置版本
     *
     * @param version 版本
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * 获取字符编码
     *
     * @return charset - 字符编码
     */
    public String getCharset() {
        return charset;
    }

    /**
     * 设置字符编码
     *
     * @param charset 字符编码
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }

    /**
     * 获取货币类型
     *
     * @return currency - 货币类型
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 设置货币类型
     *
     * @param currency 货币类型
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * 获取合作商代码
     *
     * @return partner_code - 合作商代码
     */
    public String getPartnerCode() {
        return partnerCode;
    }

    /**
     * 设置合作商代码
     *
     * @param partnerCode 合作商代码
     */
    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    /**
     * 获取支付帐号
     *
     * @return pay_account_name - 支付帐号
     */
    public String getPayAccountName() {
        return payAccountName;
    }

    /**
     * 设置支付帐号
     *
     * @param payAccountName 支付帐号
     */
    public void setPayAccountName(String payAccountName) {
        this.payAccountName = payAccountName;
    }

    /**
     * 获取私钥密码
     *
     * @return private_key_pwd - 私钥密码
     */
    public String getPrivateKeyPwd() {
        return privateKeyPwd;
    }

    /**
     * 设置私钥密码
     *
     * @param privateKeyPwd 私钥密码
     */
    public void setPrivateKeyPwd(String privateKeyPwd) {
        this.privateKeyPwd = privateKeyPwd;
    }

    /**
     * 获取私钥文件名
     *
     * @return private_key_file_name - 私钥文件名
     */
    public String getPrivateKeyFileName() {
        return privateKeyFileName;
    }

    /**
     * 设置私钥文件名
     *
     * @param privateKeyFileName 私钥文件名
     */
    public void setPrivateKeyFileName(String privateKeyFileName) {
        this.privateKeyFileName = privateKeyFileName;
    }

    /**
     * 获取公钥文件名
     *
     * @return public_key_file_name - 公钥文件名
     */
    public String getPublicKeyFileName() {
        return publicKeyFileName;
    }

    /**
     * 设置公钥文件名
     *
     * @param publicKeyFileName 公钥文件名
     */
    public void setPublicKeyFileName(String publicKeyFileName) {
        this.publicKeyFileName = publicKeyFileName;
    }

    /**
     * 获取签名类型
     *
     * @return sign_type - 签名类型
     */
    public String getSignType() {
        return signType;
    }

    /**
     * 设置签名类型
     *
     * @param signType 签名类型
     */
    public void setSignType(String signType) {
        this.signType = signType;
    }

    /**
     * 获取支付网关地址
     *
     * @return pay_url - 支付网关地址
     */
    public String getPayUrl() {
        return payUrl;
    }

    /**
     * 设置支付网关地址
     *
     * @param payUrl 支付网关地址
     */
    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    /**
     * 获取前台回调URL
     *
     * @return return_url - 前台回调URL
     */
    public String getReturnUrl() {
        return returnUrl;
    }

    /**
     * 设置前台回调URL
     *
     * @param returnUrl 前台回调URL
     */
    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    /**
     * 获取后台回调URL
     *
     * @return notify_url - 后台回调URL
     */
    public String getNotifyUrl() {
        return notifyUrl;
    }

    /**
     * 设置后台回调URL
     *
     * @param notifyUrl 后台回调URL
     */
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    /**
     * 获取扩展字段一
     *
     * @return extend1 - 扩展字段一
     */
    public String getExtend1() {
        return extend1;
    }

    /**
     * 设置扩展字段一
     *
     * @param extend1 扩展字段一
     */
    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    /**
     * 获取扩展字段二
     *
     * @return extend2 - 扩展字段二
     */
    public String getExtend2() {
        return extend2;
    }

    /**
     * 设置扩展字段二
     *
     * @param extend2 扩展字段二
     */
    public void setExtend2(String extend2) {
        this.extend2 = extend2;
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
     * 获取操作管理员
     *
     * @return user_id - 操作管理员
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置操作管理员
     *
     * @param userId 操作管理员
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}