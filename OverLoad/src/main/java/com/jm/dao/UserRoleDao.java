package com.jm.dao;

import org.apache.ibatis.annotations.Param;

import com.jm.bean.UserRole;

public interface UserRoleDao {
	 UserRole get2(@Param("empName")String empName, @Param("roleId")String roleId);
	 
	  void add2(UserRole userRole);
	 
	  void del2(@Param("empName")String empName,@Param("roleId")String roleId);
	  
	  
	  

	void cancleAllRole(@Param("empName")String empName);

	void assignAllRole(@Param("empName")String empName); 
	
	
	//通过角色id删除所属用户	
	void delUserRole(@Param("roleId")String roleId);
	
}
