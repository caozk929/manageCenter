package com.zjht.manager.entity.base;

import java.util.Date;

import com.zjht.manager.entity.SysMenu;
import com.zjht.manager.entity.User;

/**
 * SysMenu entity. @author MyEclipse Persistence Tools
 */

public class BaseSysMenu implements java.io.Serializable {

    // Fields

    /**
	 * 
	 */
    private static final long serialVersionUID = -3841218841038749621L;

    private Integer           id;

    private String            name;                                     //菜单名称

    private String            parentIds;                                //上级菜单集合(示例：0,1,2,)

    private String            path;                                     //访问路径

    private String            target;                                   //打开方式

    private String            icon;                                     //图标

    private Integer           sort;                                     //排序(数值越大排序越靠前)

    private Integer           viewStatus;                               //显示状态（0表示不显示，1表示显示）

    private String            permission;                               //权限

    private Integer           delStatus;                                //删除状态（0表示在用，1表示删除）

    private Date              createTime;                               //录入时间

    private Date              updateTime;                               //更新时间

    private SysMenu           parent;                                   //上级菜单

    private User              user;                                     //操作管理员


    // Constructors

    /** default constructor */
    public BaseSysMenu() {}

    public BaseSysMenu(Integer id) {
        this.id = id;
    }

    /** full constructor */
    public BaseSysMenu(String name, String parentIds, String path, String target, String icon, Integer sort, Integer viewStatus, String permission, Integer delStatus, Date createTime, Date updateTime) {
        this.name = name;
        this.parentIds = parentIds;
        this.path = path;
        this.target = target;
        this.icon = icon;
        this.sort = sort;
        this.viewStatus = viewStatus;
        this.permission = permission;
        this.delStatus = delStatus;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentIds() {
        return this.parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTarget() {
        return this.target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSort() {
        return this.sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getViewStatus() {
        return this.viewStatus;
    }

    public void setViewStatus(Integer viewStatus) {
        this.viewStatus = viewStatus;
    }

    public String getPermission() {
        return this.permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Integer getDelStatus() {
        return this.delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public SysMenu getParent() {
        return parent;
    }

    public void setParent(SysMenu parent) {
        this.parent = parent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}