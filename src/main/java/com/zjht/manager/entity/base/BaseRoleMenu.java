package com.zjht.manager.entity.base;

import com.zjht.manager.entity.Role;
import com.zjht.manager.entity.SysMenu;

/**
 * RoleMenu entity. @author MyEclipse Persistence Tools
 */

public class BaseRoleMenu implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6461468335728392712L;
	private Long id;
	private Role role;
	private SysMenu menu;

	public BaseRoleMenu() {
	}
	
	public BaseRoleMenu(Long id){
		this.id=id;
	}
	
	public BaseRoleMenu(Long id, Role role, SysMenu menu) {
		super();
		this.id = id;
		this.role = role;
		this.menu = menu;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public SysMenu getMenu() {
		return menu;
	}

	public void setMenu(SysMenu menu) {
		this.menu = menu;
	}

}