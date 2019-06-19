package com.jm.service;

import java.util.List;
import java.util.Map;

import com.jm.bean.Emp;
import com.jm.bean.Role;
import com.jm.bean.Station;

public interface EmpService {
	
	
	/**
	 * 改变用户  角色中间表的状态
	 * @param userId
	 * @param roleId
	 * @param isAssigned
	 */
	 void changeUserRole(String empName,String roleId,boolean isAssigned);
	/**
	 * 通过id查询用户对象
	 * @return
	 */	
	Emp getById(String empName); 
	
	Emp getById2(Integer id); 
	
	/**
	 * 根据用户id查询所有的角色
	 * @return
	 */
	
	List<Role> findAllRole(String empName);
	
    
	//查询所有用户信息
	public List<Emp> queryEmp();
	
	//多条件查找
	public List<Emp> queryE(String jobNum,String empName,String isEmp,String stationName);
	
	//用户登录
	public boolean getByEmpName(String empName,String passWord);
	
	
	   
	//用户退出
	public void logout();
	
	//新增员工
	public 	Map<String,Object> addEmp(Emp emp);
	
    //新增站点
	public boolean addStation(Station station);
	
	//修改员工信息
	public boolean updateEmp(Emp emp);
		
	//删除员工信息
	public boolean deleteEmp(int id);
	
	//修改前查询
	public List<Emp> loadEmp(int id);
	
	//修改密码
	public Map<String,Object> updatePwd(Emp emp,String oldPassWord);
	
	void assignAllRole(String empName);
	
	void cancleAllRole(String empName);

	//修改用户设备权限
	void changeUserNode(String userId, String nodeId, boolean isAssigned);

	void assignAllNode(String userId);

	void cancleAllNode(String userId);

	//修改用户站点权限
	public String changeUserStation(String userId, String stationId,String stationCode, boolean isAssigned);
	
}
