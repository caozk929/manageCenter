package com.zjht.manager.entity.base;

import com.zjht.manager.entity.User;
import org.apache.ibatis.annotations.One;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

public class BaseLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator ="select lpad(nextval('SEQ_LOG'),12,'0')")
    private String id;

    /**
     * 操作业务编码
     */
    @Column(name = "business_code")
    private String businessCode;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 关联对象id
     */
    @Column(name = "subject_id")
    private String subjectId;

    /**
     * 日志内容
     */
    private String content;

    /**
     * 日志类型
     */
    private Integer type;

    @Transient
    private User user;

    private static final long serialVersionUID = 1L;

    public BaseLog() {
    }

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
     * 获取操作业务编码
     *
     * @return business_code - 操作业务编码
     */
    public String getBusinessCode() {
        return businessCode;
    }

    /**
     * 设置操作业务编码
     *
     * @param businessCode 操作业务编码
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
     * 获取关联对象id
     *
     * @return subject_id - 关联对象id
     */
    public String getSubjectId() {
        return subjectId;
    }

    /**
     * 设置关联对象id
     *
     * @param subjectId 关联对象id
     */
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * 获取日志内容
     *
     * @return content - 日志内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置日志内容
     *
     * @param content 日志内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}