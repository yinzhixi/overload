package com.jm.dao;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.jm.bean.Permission;
import com.jm.bean.Resource;
import com.jm.bean.Role;

public interface RoleDao {
	
	//修改角色
	 int updateRole(Role role);
	
		List<Resource> queryAll();
		List<Resource> queryAllMenuByRolrId(String id);
		
	 //通过角色id拿到所有的菜单目录
    List<Resource> getAllMenu(String id);
    
    void assignAllResource(@Param("roleId")String roleId);

    void cancleAllResource(@Param("roleId")String roleId);
	
	
	
	
    List<Role> getAll();

    int add(Role role);

    void del(@Param("id")String id);

    List<Permission> findAllPermissions(@Param("id")String id);
    
    /**
     * 通过角色名查询角色信息
     */
    Role selectByRoleName(String name);
  
    /**
   	 * 根据用户id查询当前用户角色
   	 */
       List<Role> getByEmpName(String empName);
       
    Role get(String id);

    void assignAllPermission(@Param("roleId")String roleId);

    void cancleAllPermission(@Param("roleId")String roleId);


}
