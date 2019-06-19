package com.jm.controller.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jm.bean.Emp;
import com.jm.dao.EmpDao;
import com.jm.service.EmpService;
import com.jm.web.log.SystemLog;

/**    
 * @Title: UserController
 * @Description:
 * @Company:
 * @author: yinzhixi       
 * @created: 2018年12月26日 上午9:52:23    
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private EmpDao  dao;
	@Autowired
	private EmpService empService;
	
    /**
     * 
     * @discription: app用户登录 
     * @author: yinzhixi       
     * @created: 2018年12月26日 下午4:20:04  
     * @param empName
     * @param passWord
     * @param ishave
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/login")
    public Map<String, Object> login(String empName, String passWord, HttpServletRequest request,
            HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag = false;
        String hint = "";
        //String options = ishave;

        try {
            Emp byEmpName2 = dao.getByEmpName(empName);
            if (byEmpName2 == null) {
                flag = false;
                hint = "用户不存在";
                map.put("hint", hint);
                map.put("flag", flag);
                return map;
            }
            Integer isEmp = byEmpName2.getIsEmp();
            if (isEmp == 0) {
                flag = false;
                hint = "该用户名已停止使用";
                map.put("hint", hint);
                map.put("flag", flag);
                return map;
            }

            boolean byEmpName = empService.getByEmpName(empName, passWord);
            if (byEmpName) {
                flag = true;
                hint = "登陆成功";
                request.getSession().setAttribute("user_session", byEmpName2);
            }
        } catch (ExcessiveAttemptsException e) {
            map.put("hint", "由于您的账号输错密码超过五次，现已被锁,请联系管理员或1分钟后再试");
            return map;

        } catch (IncorrectCredentialsException e) {
            flag = false;
            hint = "密码错误!";
        }
        map.put("hint", hint);
        map.put("flag", flag);
        return map;
    }
    /**
     * 
     * @author: yinzhixi       
     * @created: 2018年12月26日 下午3:38:41  
     * @param jobNum
     * @param newPwd
     * @param oldPwd
     * @return
     */
    @RequestMapping("/updatePwd")
    public Map updatePwd(String jobNum,String newPwd,String oldPwd){
    	
	    Map<String,Object> map = new HashMap<String,Object>();

    	Emp queryByJobNum = dao.queryByJobNum(jobNum);
	    String salt2 = queryByJobNum.getSalt();
	    String passWord2 = queryByJobNum.getPassWord();
	    
	    Md5Hash md5Hash1=new Md5Hash(oldPwd,salt2,5);
	    String string = md5Hash1.toString();
	    if(!passWord2.equals(string)) {
	       map.put("falg",true);
	       map.put("hint", "原密码错误");
	       return map;
	    }
	 
		String salt=UUID.randomUUID().toString().replace("-","").substring(0,4).toUpperCase();
		queryByJobNum.setSalt(salt);
		Md5Hash md5Hash=new Md5Hash(newPwd,salt,5);
		queryByJobNum.setPassWord(md5Hash.toString());					
		int updatep = dao.updatep(queryByJobNum);	
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
    
    /**
     * 
     * @discription: 查询用户信息
     * @author: yinzhixi  
     * @created: 2018年12月26日 下午3:59:07  
     * @param empId
     * @return
     */
    @RequestMapping("/userInfo")
    public Map userInfo(String empId){
    	
	    Map<String,Object> map = new HashMap<String,Object>();
	    Emp emp=dao.selectById2(Integer.parseInt(empId));
	    map.put("userInfo",emp);
	    return map;

    }

}
