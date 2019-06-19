package com.jm.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jm.bean.Resource;
import com.jm.bean.Role;
import com.jm.dao.PermissionDao;
import com.jm.dao.ResourceDao;
import com.jm.dao.RoleDao;
import com.jm.service.ResourceService;

@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private RoleDao roleDao;   
    @Autowired
    private PermissionDao permissionDao;
 
    //首页菜单展示
    public List<Resource> getAll() {   	
    	
    /*	List<Resource> all = resourceDao.getAll();
    	return all;*/
    	Subject subject = SecurityUtils.getSubject();    	
		List<Role> allRole = roleDao.getAll();//获得全部角色信息		
		List<String> roles = new ArrayList<String>();		
		for (Role role : allRole) {					
			boolean hasRole = subject.hasRole(role.getId());
			if(hasRole){
				roles.add(role.getId());
			}
		}	
	 List<Resource> allResource = resourceDao.getAllRoles(roles);//查询方法资源
	/* //根据所有的角色id查出所有的权限
	 List<Permission> allRoles = permissionDao.getAllRoles(roles);	
			 Map<String,Resource> map = new HashMap<>();
						 
			 //遍历当前有权限的菜单
			 for (Resource resource : allResource) {
				 List<Resource> children = resource.getChildren();
				 for (Resource resource2 : children) {
					 map.put(resource2.getTitle(), resource2);					 
				}		
			}
			 List<Resource> res = new ArrayList<>();
			 //明细删除
			 if(subject.isPermitted("preview:del")){
				 res.add(map.get("预检明细"));
			 }	 */
		return allResource;
		
		
    	
    }
}
