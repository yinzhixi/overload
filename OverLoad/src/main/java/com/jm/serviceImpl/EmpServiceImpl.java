package com.jm.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.jm.bean.*;
import com.jm.dao.UserNodeDao;
import com.jm.dao.UserStationDao;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jm.dao.EmpDao;
import com.jm.dao.UserRoleDao;
import com.jm.service.EmpService;
@Service
public class EmpServiceImpl implements EmpService {

	@Autowired
	private EmpDao empDao;
	
	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private UserStationDao userStationDao;
	@Autowired
    private UserNodeDao userNodeDao;

	//用户登录
		@Override
		public boolean getByEmpName(String empName,String passWord) {
			// TODO Auto-generated method stub
			Subject subject = SecurityUtils.getSubject();
			subject.login(new UsernamePasswordToken(empName,passWord));
			return true;
			/*try{
			
			}catch(Exception e){
				throw new RuntimeException("账号或密码错误");
			}*/
	    }
		
	
	//用户退出
	@Override
	public void logout() {
		// TODO Auto-generated method stub
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
	}

	//新增用户
	@Override
	public Map<String,Object> addEmp(Emp emp) {		
		Map<String,Object> map = new HashMap<>();
		Emp queryByJobNum = empDao.queryByJobNum(emp.getJobNum());
		if(queryByJobNum != null){
			map.put("hint","该用户已存在，请修改工号。");
			map.put("flat", true);
			return map;
		}
		
		String salt=UUID.randomUUID().toString().replace("-","").substring(0,4).toUpperCase();
		emp.setSalt(salt);
		Md5Hash md5Hash=new Md5Hash(emp.getPassWord(),salt,5);
		emp.setPassWord(md5Hash.toString());		
		int add = empDao.addEmp(emp);
		if(add>0){			
			map.put("hint","添加成功");
			map.put("flat", true);			
		}else{
			map.put("hint","添加失败");
			map.put("flat", false);
		}
		return map;
	}
	
	//新增站点
	@Override
	public boolean addStation(Station station) {
		// TODO Auto-generated method stub
		int add = empDao.addStation(station);
		if(add>0){
			return true;
		}else{
			return false;
		}
	}

     
	//修改用户信息
	@Override
	public boolean updateEmp(Emp emp) {		
				
		int update = empDao.updateEmp(emp);
		if(update>0){
			return true;
		}else{
			return false;
		}
	}


	//删除用户信息
	@Override
	public boolean deleteEmp(int id) {
		// TODO Auto-generated method stub
		int delete = empDao.deleteEmp(id);
		if(delete>0){
			return true;
		}else{
		    return false;
		}
	}

	
	//修改前查询
	@Override
	public List<Emp> loadEmp(int id) {
		// TODO Auto-generated method stub
		List<Emp> empl = empDao.queryEmp(id);
		return empl;
	}

	//修改密码
	@Override
	public Map<String,Object> updatePwd(Emp emp,String oldPassWord) {
	    Map<String,Object> map = new HashMap<String,Object>();
	    
	    String jobNum = emp.getJobNum();
	    Emp queryByJobNum = empDao.queryByJobNum(jobNum);
	    String salt2 = queryByJobNum.getSalt();
	    String passWord2 = queryByJobNum.getPassWord();
	    
	    Md5Hash md5Hash1=new Md5Hash(oldPassWord,salt2,5);
	    String string = md5Hash1.toString();
	    if(!passWord2.equals(string)) {
	       map.put("falg",true);
	       map.put("hint", "原密码错误");
	       return map;
	    }
	 
		String salt=UUID.randomUUID().toString().replace("-","").substring(0,4).toUpperCase();
		emp.setSalt(salt);
		String passWord = emp.getPassWord();
		Md5Hash md5Hash=new Md5Hash(passWord,salt,5);
		emp.setPassWord(md5Hash.toString());					
		int updatep = empDao.updatep(emp);	
		if(updatep>0){
		    map.put("falg",true);
	           map.put("hint", "修改成功");
	           return map;
	         
		}else{
		       map.put("falg",false);
	           map.put("hint", "修改失敗");
	           return  map;
	       	}
	
	}

	
	//查询所有用户信息
	@Override
	public List<Emp> queryEmp() {
		// TODO Auto-generated method stub
		List<Emp> empL = empDao.queryAll();
		return empL;
	}


	//多条件查找
	@Override
	public List<Emp> queryE(String jobNum, String empName, String isEmp,
			String stationName) {	
		List<Emp> empl = empDao.queryE(jobNum, empName, isEmp, stationName);		
		return empl;
	}


	@Override
	public Emp getById(String empName) {
		return empDao.selectById(empName);
	}

	//根据用户id查询所有的角色
	@Override
	public List<Role> findAllRole(String empName) {
		return empDao.findAllRole(empName);		
	}


	@Override
	public void changeUserRole(String empName, String roleId,
			boolean isAssigned) {			
		Emp emp = empDao.selectById(empName);
		if(emp==null){
			return;
		}		
		 if(isAssigned){
			 UserRole get2 = userRoleDao.get2(empName, roleId);           
	            if(get2 == null){	               
	            	get2 = new UserRole(emp.getEmpName(),roleId);
	            	userRoleDao.add2(get2);         
	            }
	        }else{
	        	userRoleDao.del2(emp.getEmpName(), roleId);
	        }		
	}

	@Override
	public void assignAllRole(String empName) {

        this.userRoleDao.cancleAllRole(empName);
        this.userRoleDao.assignAllRole(empName);
	}


	@Override
	public void cancleAllRole(String empName) {
        this.userRoleDao.cancleAllRole(empName);
		
	}


	@Override
	public Emp getById2(Integer id) {
		return empDao.selectById2(id);
		
	}


	
	public static void main(String[] args){
		
		String salt=UUID.randomUUID().toString().replace("-","").substring(0,4).toUpperCase();
		//emp.setSalt(salt);
		System.out.println(salt);
		//String passWord = emp.getPassWord();
		Md5Hash md5Hash=new Md5Hash("123456",salt,5);
        System.out.println(md5Hash.toString());
	}

	@Override
	public String  changeUserStation(String userId, String stationId,String stationCode, boolean isAssigned) {

		Emp emp = empDao.selectById2(Integer.parseInt(userId));
		if(emp==null){
			return "该员工不存在";
		}
		if(isAssigned){
			int mou = userStationDao.userStationCount(emp.getId(), Integer.parseInt(stationId));
			if(mou <= 0){
				UserStation userStation = new UserStation(emp.getId(),Integer.parseInt(stationId),stationCode);
				int mu=userStationDao.add(userStation);
				if(mu<=0){
					return "操作失败";
				}
			}
		}else{
			int mu=userStationDao.del(emp.getId(), Integer.parseInt(stationId));
			if(mu<=0){
				return "操作失败";
			}
		}
		return "操作成功";
	}

    /**
     * 功能描述: 用户权限设置
     * @param userId
     * @param nodeId
     * @param isAssigned
     * @return: void
     * @Author: yinzhixi
     * @Date: 2019/2/25 10:35
     */
    @Override
    public void changeUserNode(String userId, String nodeId, boolean isAssigned) {
        Emp emp = empDao.selectById(userId);
        if(emp==null){
            return;
        }
        if(isAssigned){
            int mou = userNodeDao.userNodeCount(userId, nodeId);
            if(mou <= 0){
                UserNode userNode = new UserNode(userId,nodeId);
                userNodeDao.add(userNode);
            }
        }else{
            userNodeDao.del(userId, nodeId);
        }
    }

    /**
     * 功能描述: 用户全部关联设备
     * @param userId
     * @return: void
     * @Author: yinzhixi
     * @Date: 2019/2/25 11:59
     */
    @Override
    public void assignAllNode(String userId) {
        userNodeDao.assignAllNode(userId);
    }

    /**
     * 功能描述: 用户全部解除设备关联
     * @param userId
     * @return: void
     * @Author: yinzhixi
     * @Date: 2019/2/25 12:00
     */
    @Override
    public void cancleAllNode(String userId) {
        userNodeDao.cancleAllNode(userId);
    }
}
