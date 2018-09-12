package com.zjht.manager.entity.base;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_api_service")
public class BaseApiService implements Serializable {

    private static final long serialVersionUID = 1102127258586492891L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select lpad(nextval('SEQ_API_SERVICE'),12,'0')")
    private String id;

    /**
     * 服务名称
     */
    @Column(name = "service_name")
    private String serviceName;

    /**
     * 服务编码
     */
    @Column(name = "service_code")
    private String serviceCode;

    /**
     * 服务路径
     */
    @Column(name = "service_path")
    private String servicePath;

    /**
     * 备注
     */
    private String remarks;

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
     * 服务状态（1-在用，0-停用）
     */
    private Integer status;

    @Column(name = "user_id")
    private String userId;

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
     * 获取服务名称
     *
     * @return service_name - 服务名称
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * 设置服务名称
     *
     * @param serviceName 服务名称
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * 获取服务编码
     *
     * @return service_code - 服务编码
     */
    public String getServiceCode() {
        return serviceCode;
    }

    /**
     * 设置服务编码
     *
     * @param serviceCode 服务编码
     */
    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    /**
     * 获取服务路径
     *
     * @return service_path - 服务路径
     */
    public String getServicePath() {
        return servicePath;
    }

    /**
     * 设置服务路径
     *
     * @param servicePath 服务路径
     */
    public void setServicePath(String servicePath) {
        this.servicePath = servicePath;
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
     * 获取服务状态（1-在用，0-停用）
     *
     * @return status - 服务状态（1-在用，0-停用）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置服务状态（1-在用，0-停用）
     *
     * @param status 服务状态（1-在用，0-停用）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
}