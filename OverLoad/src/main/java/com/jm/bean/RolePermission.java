package com.jm.bean;

import java.io.Serializable;

public class RolePermission implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String roleId;
    private String permissionId;
    private String domain;
    
    public RolePermission(String id, String permissionId2) {
        this.roleId = id;
        this.permissionId = permissionId2;
    }
    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public String getPermissionId() {
        return permissionId;
    }
    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }
    public String getDomain() {
        return domain;
    }
    public void setDomain(String domain) {
        this.domain = domain;
    }
    
}
