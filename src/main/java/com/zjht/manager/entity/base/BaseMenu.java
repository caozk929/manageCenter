package com.zjht.manager.entity.base;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_menu")
public class BaseMenu implements Serializable {
    /**
     * 菜单标识id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator ="select lpad(nextval('SEQ_MENU'),12,'0')")
    private String id;

    /**
     * 菜单编码，一级菜单为m01，二级为m0101，三级为m010101。现在的三级菜单可以理解为.do请求
     */
    private String code;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 父菜单id
     */
    @Column(name = "parent_id")
    private String parentId;

    /**
     * 所有上级id串，以英文逗号隔开
     */
    @Column(name = "parent_ids")
    private String parentIds;

    /**
     * 菜单路径
     */
    private String path;

    /**
     * 菜单排序，数值越小越在前
     */
    private Integer sort;

    /**
     * 菜单状态（1-在用，0-停用）
     */
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 操作用户id
     */
    @Column(name = "user_id")
    private String userId;

    private static final long serialVersionUID = 1L;

    /**
     * 获取菜单标识id
     *
     * @return id - 菜单标识id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置菜单标识id
     *
     * @param id 菜单标识id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取菜单编码，一级菜单为m01，二级为m0101，三级为m010101。现在的三级菜单可以理解为.do请求
     *
     * @return code - 菜单编码，一级菜单为m01，二级为m0101，三级为m010101。现在的三级菜单可以理解为.do请求
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置菜单编码，一级菜单为m01，二级为m0101，三级为m010101。现在的三级菜单可以理解为.do请求
     *
     * @param code 菜单编码，一级菜单为m01，二级为m0101，三级为m010101。现在的三级菜单可以理解为.do请求
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取菜单名
     *
     * @return name - 菜单名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置菜单名
     *
     * @param name 菜单名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取父菜单id
     *
     * @return parent_id - 父菜单id
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置父菜单id
     *
     * @param parentId 父菜单id
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取所有上级id串，以英文逗号隔开
     *
     * @return parent_ids - 所有上级id串，以英文逗号隔开
     */
    public String getParentIds() {
        return parentIds;
    }

    /**
     * 设置所有上级id串，以英文逗号隔开
     *
     * @param parentIds 所有上级id串，以英文逗号隔开
     */
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    /**
     * 获取菜单路径
     *
     * @return path - 菜单路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置菜单路径
     *
     * @param path 菜单路径
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取菜单排序，数值越小越在前
     *
     * @return sort - 菜单排序，数值越小越在前
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置菜单排序，数值越小越在前
     *
     * @param sort 菜单排序，数值越小越在前
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取菜单状态（1-在用，0-停用）
     *
     * @return status - 菜单状态（1-在用，0-停用）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置菜单状态（1-在用，0-停用）
     *
     * @param status 菜单状态（1-在用，0-停用）
     */
    public void setStatus(Integer status) {
        this.status = status;
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
    public String getUserId() {
        return userId;
    }

    /**
     * 设置操作用户id
     *
     * @param userId 操作用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
}