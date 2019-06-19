package com.jm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jm.bean.Permission;
import com.jm.dao.PermissionDao;
import com.jm.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService{
    @Autowired
    private PermissionDao permissionDao;
    
    @Override
    public List<Permission> getAll() {
        return this.permissionDao.getAll();
    }

    @Override
    public void add(String id,String name) {
        Permission permission = new Permission();
        permission.setId(id);
        permission.setName(name);
        this.permissionDao.add(permission);
    }

    @Override
    public void del(String id) {
        this.permissionDao.del(id);
    }

	@Override
	public int updatePermission(Permission permiss) {
		return permissionDao.updatePermission(permiss);
	
	}

}
