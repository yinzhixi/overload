package com.jm.serviceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.jm.bean.Permission;
import com.jm.bean.Resource;
import com.jm.bean.ResourceRole;
import com.jm.bean.Role;
import com.jm.bean.RolePermission;
import com.jm.dao.ResourceRoleDao;
import com.jm.dao.RoleDao;
import com.jm.dao.RolePermissionDao;
import com.jm.dao.UserRoleDao;
import com.jm.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;
    @Autowired
    private ResourceRoleDao resourceRoleDao;
    
    @Autowired
    private UserRoleDao userRoleDao;
   
  
    //授权    
    @Override
	public JSONObject roleAuth(String roleId, String menuIds) {
    	JSONObject rs = new JSONObject();
		boolean isSuccess = false;
		String msg = "";
		try {
			if(roleId == null || menuIds == null) {
				throw new Exception("the roleId or menuIds must be not null...");
			}
			//首先删除原有角色与菜单的关系
			resourceRoleDao.delResourceRole(roleId);			
			//添加新的角色与菜单的关系
			String[] menus = menuIds.split(",");
			ResourceRole roleMenu = null;			
			for (String mId : menus) {
				roleMenu = new ResourceRole(roleId, mId);
				resourceRoleDao.addResourceRole(roleMenu);				
			}
			isSuccess = true;
			msg = "授权成功！";
		}catch(Exception e) {
			e.printStackTrace();
			msg = "操作失败！" + e.getMessage();
		}
		rs.put("success", isSuccess);
		rs.put("msg", msg);
		return rs;
	}
    
    
    @Override
	public List<Role> listRolesByEmpName(String empName) {
    	List<Role> byEmpName = roleDao.getByEmpName(empName);
		return byEmpName;
	}

	@Override
	public String getRoleIdsByUser(String empName) {
		List<Role> roles = this.listRolesByEmpName(empName);     	
		StringBuffer buffer = new StringBuffer();
		if(roles != null && roles.size()>0){
			for (Role role : roles) {
				buffer.append(role.getId()).append(",");
			}
		}
		String roleId = buffer.toString();
		if(!StringUtils.isEmpty(roleId)){
			roleId = roleId.substring(0, roleId.lastIndexOf(","));
		}
		return roleId;
	}
    
    @Override
    public List<Role> getAll() {
        return this.roleDao.getAll();
    }

    @Override
    public Map<String,Object> add(Role role) {
    	  Map<String,Object> map = new HashMap<>();
    	Role selectByRoleName = roleDao.selectByRoleName(role.getName());
    	if(selectByRoleName != null){
    		 map.put("hint","添加失败，角色名已存在。");
 			map.put("flat", true);
 			return map;
    	}
    
       int add = roleDao.add(role);
       if(add>0){
    	   map.put("hint","添加成功");
			map.put("flat", true);
		}else{
			map.put("hint","添加失败");
			map.put("flat", false);
		}
		return map;       
    }

    
    //删除角色  删除菜单 删除权限 
    @Override
    public void del(String id) {
    	userRoleDao.delUserRole(id);
    	rolePermissionDao.delPermission(id);
    	resourceRoleDao.delResourceRole(id);	
        this.roleDao.del(id);
    }

    @Override
    public List<Permission> findAllPermissions(String id) {
        return this.roleDao.findAllPermissions(id);
    }

    @Override
    public void changeRolePermission(String roleId, String permissionId, boolean isAssigned) {
        Role role = this.get(roleId);
        if(role == null){
            return;
        }
        if(isAssigned){
            RolePermission rolePermission = this.rolePermissionDao.get(roleId,permissionId);
            if(rolePermission == null){
                rolePermission = new RolePermission(role.getId(),permissionId);
                this.rolePermissionDao.add(rolePermission);
            }
        }else{
            this.rolePermissionDao.del(role.getId(), permissionId);
        }
    }
    
    @Override
    public Role get(String id) {
        return this.roleDao.get(id);
    }

    @Override
    public void assignAllPermission(String roleId) {
        this.roleDao.cancleAllPermission(roleId);
        this.roleDao.assignAllPermission(roleId);
    }

    @Override
    public void cancleAllPermission(String roleId) {
        this.roleDao.cancleAllPermission(roleId);
    }
    //角色获取的菜单
	@Override
	public List<Resource> getAllMenu(String id) {
		return roleDao.getAllMenu(id);
	}

	@Override
	public int updateRole(Role role) {
		return roleDao.updateRole(role);
		
	}

	

	
	
}
