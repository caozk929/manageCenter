package com.zjht.manager.entity;

import com.zjht.manager.entity.base.BaseMenu;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Table(name = "t_menu")
public class Menu extends BaseMenu{

    private static final long serialVersionUID = -6868041012977379288L;

    @Transient
    private List<Menu> children;//扩展字段，存放下级菜单

    public List<Menu> getChildren() {
        return children;
    }

    /**
     * 获取中文状态
     * @return
     */
    public String getStatusStr() {
        Integer status = getStatus();
        if (status == 1) {//启用
            return "启用";
        } else {//停用
            return "停用";
        }
    }
    public void setChildren(List<Menu> children) {
        this.children = children;
    }
}