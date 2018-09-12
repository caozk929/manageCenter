package com.zjht.manager.entity;

import com.zjht.manager.entity.base.BaseApiService;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_api_service")
public class ApiService extends BaseApiService {

    private static final long serialVersionUID = -4883279290542784803L;

    @Transient
    private  String orderByClause ;

    /**
     * 状态
     */
    public static enum Status {

        DISABLE(0,"停用"),
        NORMAL(1,"正常"),
        DELETED(2,"已删除");

        private Status(int status, String name) {
            this.status = status;
            this.name = name;
        }

        private String name;
        private int status;
        public static String getName(int status) {
            for (ApiService.Status c : ApiService.Status.values()) {
                if (c.getStatus() == status) {
                    return c.name;
                }
            }
            return null;
        }
        public int getStatus() {
            return status;
        }
        public void setStatus(int status) {
            this.status = status;
        }
    }


    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }
}