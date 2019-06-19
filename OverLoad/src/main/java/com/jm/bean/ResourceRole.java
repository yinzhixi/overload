package com.jm.bean;

import java.io.Serializable;

public class ResourceRole implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String roleId;
	private String menuId;
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public ResourceRole(String roleId, String menuId) {
		super();
		this.roleId = roleId;
		this.menuId = menuId;
	}
	public ResourceRole() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
