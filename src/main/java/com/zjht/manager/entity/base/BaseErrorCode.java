package com.zjht.manager.entity.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 错误码
 * @author caozk
 * @create 2017-09-02 10:50
 */
public class BaseErrorCode implements Serializable {


    private static final long serialVersionUID = 102278916681960578L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select lpad(nextval('SEQ_ERROR_CODE'),12,'0')")
    private String id;

    /**
     * 错误码
     */
    @Column(name = "error_code")
    private String errorCode;

    /**
     * 错误消息
     */
    @Column(name = "error_msg")
    private String errorMsg;

    /**
     * 所属业务编码
     */
    @Column(name = "business_code")
    private String businessCode;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 操作用户id
     */
    @Column(name = "user_id")
    private Integer userId;

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
     * 获取错误码
     *
     * @return error_code - 错误码
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * 设置错误码
     *
     * @param errorCode 错误码
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 获取错误消息
     *
     * @return error_msg - 错误消息
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * 设置错误消息
     *
     * @param errorMsg 错误消息
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * 获取所属业务编码
     *
     * @return business_code - 所属业务编码
     */
    public String getBusinessCode() {
        return businessCode;
    }

    /**
     * 设置所属业务编码
     *
     * @param businessCode 所属业务编码
     */
    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
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
     * 获取操作用户id
     *
     * @return user_id - 操作用户id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置操作用户id
     *
     * @param userId 操作用户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
