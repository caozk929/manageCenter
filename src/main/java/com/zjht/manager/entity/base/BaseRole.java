package com.zjht.manager.entity.base;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_role")
public class BaseRole implements java.io.Serializable{

    private static final long serialVersionUID = -4369258816974132761L;
    /**
     * 角色标识id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator ="select lpad(nextval('SEQ_ROLE'),12,'0')")
    private String id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 角色状态（0-失效; 1-正常）
     */
    private Integer status;

    /**
     * 角色描述
     */
    private String remarks;

    /**
     * 获取角色标识id
     *
     * @return id - 角色标识id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置角色标识id
     *
     * @param id 角色标识id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取角色名
     *
     * @return name - 角色名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置角色名
     *
     * @param name 角色名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取角色状态（0-失效; 1-正常）
     *
     * @return status - 角色状态（0-失效; 1-正常）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置角色状态（0-失效; 1-正常）
     *
     * @param status 角色状态（0-失效; 1-正常）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取角色描述
     *
     * @return remarks - 角色描述
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置角色描述
     *
     * @param remarks 角色描述
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}