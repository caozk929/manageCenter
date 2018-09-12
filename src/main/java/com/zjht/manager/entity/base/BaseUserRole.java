package com.zjht.manager.entity.base;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by vip on 2017/9/7.
 */
@Table(name = "t_user_role")
public class BaseUserRole implements Serializable {

    /**
     * 主键id标识
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator ="select lpad(nextval('SEQ_USER_ROLE'),12,'0')")
    private String id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private String roleId;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键id标识
     *
     * @return id - 主键id标识
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键id标识
     *
     * @param id 主键id标识
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取角色id
     *
     * @return role_id - 角色id
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * 设置角色id
     *
     * @param roleId 角色id
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
