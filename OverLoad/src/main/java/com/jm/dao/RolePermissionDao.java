package com.jm.dao;
import org.apache.ibatis.annotations.Param;
import com.jm.bean.RolePermission;
public interface RolePermissionDao {

    RolePermission get(
            @Param("roleId")String roleId, 
            @Param("permissionId")String permissionId);

    void add(RolePermission rolePermission);
    void del(
            @Param("roleId")String roleId, 
            @Param("permissionId")String permissionId);

	void delPermission(String id);
    
    
   
}
