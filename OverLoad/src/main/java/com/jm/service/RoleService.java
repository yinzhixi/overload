package com.jm.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jm.bean.Permission;
import com.jm.bean.Resource;
import com.jm.bean.Role;

public interface RoleService {
	
	List<Role> listRolesByEmpName(String empName);
	String getRoleIdsByUser(String empName);
		
	int updateRole(Role role);
	
	//角色id得到的菜单
	List<Resource> getAllMenu(String id);

    List<Role> getAll();

    Map<String,Object> add(Role role);

    void del(String id);

    List<Permission> findAllPermissions(String id);

    void changeRolePermission(String roleId, String id,boolean isAssigned);
    
    Role get(String id);

    void assignAllPermission(String roleId);

    void cancleAllPermission(String roleId);
    
	JSONObject roleAuth(String roleId, String menuIds);
    
    
    
    
    
    
}
