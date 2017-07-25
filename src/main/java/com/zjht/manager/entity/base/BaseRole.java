package com.zjht.manager.entity.base;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.zjht.manager.entity.SysMenu;
import com.zjht.manager.entity.User;

public class BaseRole implements java.io.Serializable {

    private static final long serialVersionUID = 597694949424142173L;

    private Long              id;

    private String            roleName;

    private Integer           roleType;

    private Integer           status;

    private Date              createTime;

    private Date              updateTime;

    private List<User>        userList         = Lists.newArrayList(); // 拥有用户列表

    private List<SysMenu>     menuList         = Lists.newArrayList(); // 拥有菜单列表


    public BaseRole() {}

    public BaseRole(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<SysMenu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<SysMenu> menuList) {
        this.menuList = menuList;
    }

}
