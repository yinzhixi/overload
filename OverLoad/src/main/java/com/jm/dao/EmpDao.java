package com.jm.dao;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.jm.bean.Emp;
import com.jm.bean.Role;
import com.jm.bean.Station;
public interface EmpDao {
	
	
	List<Emp> getAllEmpByRoleId(String id);
	
	Emp queryByJobNum(String jobNum);

	/**
	 * 通过用户名查找用户信息
	 * @return
	 */
	public Emp selectById(String empName);
	
	public Emp selectById2(Integer id);
	/**
	 * 通过用户id查找所有的角色
	 * @param id
	 * @return
	 */
	 List<Role> findAllRole(@Param("empName")String empName);
	
	
     
	//查询所有用户信息
	public List<Emp> queryAll();
	
	//查询用户信息
	public Emp getByEmpName(String empName);
	
	//多条件查找
	public List<Emp> queryE(@Param("jobNum") String jobNum,@Param("empName") String empName,@Param("isEmp") String isEmp,@Param("stationName") String stationName);

	//新增员工信息
	public int addEmp(Emp emp);
	
	//新增站点
	public int addStation(Station station);
	
	//修改员工信息
	public int updateEmp(Emp emp);
	
	//删除员工信息
	public int deleteEmp(int id);
	
	//修改前查询
	public List<Emp> queryEmp(int id);
	
	//修改密码
	public int updatep(Emp emp);
}


