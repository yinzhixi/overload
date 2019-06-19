package com.jm.service;

import java.util.List;

import com.jm.bean.Permission;

public interface PermissionService {
	int updatePermission(Permission permiss);
	
    List<Permission> getAll();

    void add(String id,String name);

    void del(String id);

}
