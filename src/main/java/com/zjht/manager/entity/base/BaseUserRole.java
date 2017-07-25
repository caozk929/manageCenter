package com.zjht.manager.entity.base;

import com.zjht.manager.entity.Role;
import com.zjht.manager.entity.User;


public class BaseUserRole implements java.io.Serializable {
    
    private static final long serialVersionUID = -4719599002873956162L;

    private Long              id;

    private User            user;

    private Role            role;
    
    public BaseUserRole() {}

    public BaseUserRole(Long id) {
        this.id = id;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
