package com.zjht.manager.entity;

import com.zjht.manager.entity.base.BaseRole;

import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "t_role")
public class Role extends BaseRole {

    private static final long serialVersionUID = 2637406684031907138L;

    @Transient
    private String[] menuIds;//封装菜单Id

    public String[] getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(String[] menuIds) {
        this.menuIds = menuIds;
    }

    /**
     * 获取状态中文
     * @return
     */
    public String getStatusStr() {
        if (getStatus() == 1) {
            return "正常";
        } else {
            return "失效";
        }
    }

}