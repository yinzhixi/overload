package com.jm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jm.bean.Permission;

public interface PermissionDao {
	//修改权限
	int updatePermission(Permission permiss);
	
	//根据角色id拿到所有的权限
	List<Permission> getByRoleId(String id);

    List<Permission> getAll();

    void add(Permission permission);

    void del(@Param("id")String id);

}
