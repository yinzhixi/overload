package com.jm.bean;

import java.io.Serializable;

public class UserRole implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private String empName;//名称
	private String roleId;
	
	
	public UserRole(String empName2, String roleId2) {
		this.empName=empName2;
		this.roleId=roleId2;
		
	}

	
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	

}
