package com.jm.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.jm.bean.Emp;
import com.jm.bean.ResultDo;
import com.jm.bean.Role;
import com.jm.bean.Station;
import com.jm.dao.EmpDao;
import com.jm.dao.RoleDao;
import com.jm.data.base.PermissionConst;
import com.jm.data.base.RoleConst;
import com.jm.service.EmpService;
import com.jm.service.StationService;
import com.jm.web.log.SystemLog;

@Controller
@RequestMapping("/emp")
public class EmpController {

    @Autowired
    private EmpService empService;

    @Autowired
    private EmpDao dao;
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private StationService stationService;

    @Autowired
    HttpServletRequest request; // 这里可以获取到request

    @Autowired
    HttpServletResponse response; // 这里可以获取到response

    @SystemLog(module = "用户管理", methods = "用户主页")
    @RequestMapping("getToIntex")
    @RequiresRoles(RoleConst.ADMIN)
    public String getToIntex(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        Object principal = subject.getPrincipal();
        Emp byEmpName = dao.getByEmpName(principal.toString());
        SecurityUtils.getSubject().getSession().setTimeout(-1000l);
        request.setAttribute("user", byEmpName);
        return "index";
    }

    @SystemLog(module = "用户管理", methods = "查询所有角色")
    @RequestMapping("showAllRoles")
    @RequiresRoles(RoleConst.ADMIN)
    public String showAllRoles(String empName, Model model) {
        List<Object> roleList = new ArrayList<>();
        if (StringUtil.isNotEmpty(empName)) {
            List<Role> findAllRole = roleDao.getByEmpName(empName);
            for (Role role : findAllRole) {
                String name = role.getName();
                roleList.add(name);
            }
        } else {
            return null;
        }
        model.addAttribute("roles", roleList);
        return "/admin/showAllRoles";
    }

    @SystemLog(module = "用户管理", methods = "修改角色")
    @RequestMapping("/user/change")
    @RequiresRoles(RoleConst.ADMIN)
    @ResponseBody
    public Map<String, Object> rolePermissionsChange(ModelMap model, @RequestParam(name = "empName") String empName,
            String roleId, @RequestParam(name = "isAssigned") boolean isAssigned,
            @RequestParam(name = "type") String type) {
        Map<String, Object> res = new HashMap<>();
        if ("one".equals(type)) {
            empService.changeUserRole(empName, roleId, isAssigned);
        }
        if ("all".equals(type)) {
            if (isAssigned) {
                this.empService.assignAllRole(empName);
            } else {
                this.empService.cancleAllRole(empName);
            }
        }
        res.put("success", true);
        return res;
    }

    @SystemLog(module = "用户管理", methods = "用户角色首页")
    @RequestMapping("/user/role")
    @RequiresRoles(RoleConst.ADMIN)
    public String userRole(ModelMap model, @RequestParam(name = "empName") String empName) {
        model.put("id", empName);
        model.put("user", this.empService.getById(empName));
        return "/admin/role";
    }

    @SystemLog(module = "用户设备", methods = "用户设备首页")
    @RequestMapping("/user/node")
    @RequiresRoles(RoleConst.ADMIN)
    public String userNode(ModelMap model, @RequestParam(name = "userId") String userId) {
        model.put("userId", userId);
        model.put("user", this.empService.getById(userId));
        return "/admin/empNode";
    }
    @SystemLog(module = "用户站点", methods = "用户站点首页")
    @RequestMapping("/user/station")
    @RequiresRoles(RoleConst.ADMIN)
    public String userStation(ModelMap model, @RequestParam(name = "userId") String userId) {
        model.put("userId", userId);
        model.put("user", this.empService.getById(userId));
        return "/admin/empStation";
    }
    @SystemLog(module = "用户管理", methods = "修改设备")
    @RequestMapping("/user/changeNode")
    @RequiresRoles(RoleConst.ADMIN)
    @ResponseBody
    public Map<String, Object> nodePermissionsChange(ModelMap model, @RequestParam(name = "userId") String userId,
                                                     String nodeId, @RequestParam(name = "isAssigned") boolean isAssigned,
                                                     @RequestParam(name = "type") String type) {
        Map<String, Object> res = new HashMap<>();
        if ("one".equals(type)) {
            empService.changeUserNode(userId, nodeId, isAssigned);
        }
        if ("all".equals(type)) {
            if (isAssigned) {
                this.empService.assignAllNode(userId);
            } else {
                this.empService.cancleAllNode(userId);
            }
        }
        res.put("success", true);
        return res;
    }
    @SystemLog(module = "用户管理", methods = "修改站点")
    @RequestMapping("/user/changeStation")
    @RequiresRoles(RoleConst.ADMIN)
    @ResponseBody
    public Map<String, Object> changeStation(ModelMap model, @RequestParam(name = "userId") String userId,
                                             String stationId,String stationCode, @RequestParam(name = "isAssigned") boolean isAssigned,
                                             @RequestParam(name = "type") String type) {
        Map<String, Object> res = new HashMap<>();
        String tag="";
        if ("one".equals(type)) {
            tag=empService.changeUserStation(userId, stationId,stationCode,isAssigned);
        }
//        if ("all".equals(type)) {
////            if (isAssigned) {
////                this.userStationDao.assignAllStation(userId);
////            } else {
////                this.userStationDao.cancleAllStation(userId);
////            }
////        }
        res.put("success", tag);
        return res;
    }
    @SystemLog(module = "用户管理", methods = "查询用户角色")
    @RequestMapping("/role/data")
    @RequiresRoles(RoleConst.ADMIN)
    @ResponseBody
    public ResultDo<List<Role>> rolePermissionsData(ModelMap model, @RequestParam(name = "empName") String empName) {
        List<Role> findAllRole = empService.findAllRole(empName);
        ResultDo<List<Role>> res = new ResultDo<>();
        res.setCode(0);
        res.setMsg("");
        res.setData(findAllRole);
        res.setCount(findAllRole.size());
        return res;
    }

    // 用户登录
    @SystemLog(module = "用户管理", methods = "登录")
    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> login(String empName, String passWord, String ishave, HttpServletRequest request,
            HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag = false;
        String hint = "";
        String options = ishave;

        // 把用户名和密码保存在Cookie对象里
        try {
            String name = URLEncoder.encode(request.getParameter("empName"), "utf-8");
            if ("remember".equals(options)) {

                Cookie cookie = new Cookie("empName", name);
                cookie.setMaxAge(Integer.MAX_VALUE);

                Cookie cookie1 = new Cookie("remember", "checked='checked'");
                cookie1.setMaxAge(Integer.MAX_VALUE);
                response.addCookie(cookie);
                response.addCookie(cookie1);

            } else {// 没有"记住用户名"
                    // 清除掉Cookie信息
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie ck : cookies) {
                        ck.setMaxAge(0);
                        response.addCookie(ck);
                    }
                }
            }
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch bloc
            e1.printStackTrace();
        }
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

    //用户退出登陆
    @SystemLog(module = "用户管理", methods = "退出")
    @RequestMapping("logout")
    public String logout() {
        empService.logout();
        return "login";
    }

    // 跳转到员工展示页面

    @SystemLog(module = "用户管理", methods = "员工页面")
    @RequestMapping("mod")
    @RequiresRoles(RoleConst.ADMIN)
    public String modelEmp(Model model) {
        List<Station> lis = stationService.getAll();
        // empService.getById(id);
        model.addAttribute("lis", lis);
        return "model";
    }

    // 新增员工信息
    @SystemLog(module = "用户管理", methods = "添加用户")
    @RequestMapping("/add")
    @RequiresPermissions(PermissionConst.USER_ADD)
    @ResponseBody
    public Map<String, Object> addEmp(Emp emp) {
        Map<String, Object> addEmp = empService.addEmp(emp);
        return addEmp;
    }

    // 删除员工信息
    @SystemLog(module = "用户管理", methods = "删除用户")
    @RequestMapping("/delete")
    @RequiresPermissions(PermissionConst.USER_DEL)
    @ResponseBody
    public boolean delete(int id) {
        boolean success = empService.deleteEmp(id);
        return success;

    }

    // 修改员工前查询
    @SystemLog(module = "用户管理", methods = "用户信息查询")
    @RequestMapping("/query")
    @RequiresRoles(RoleConst.ADMIN)
    public String loadInfo(int id, Model model) {
        Emp byId = empService.getById2(id);
        model.addAttribute("type", byId);
        List<Station> lis = stationService.getAll();
        model.addAttribute("lis", lis);
        List<Emp> empl = empService.loadEmp(id);
        model.addAttribute("infoL", empl);
        model.addAttribute("empl", empl);
        return "changeEmp";
    }

    // 修改员工信息
    @SystemLog(module="用户管理",methods="修改用户信息")
    @RequestMapping("/updateE")
    @RequiresPermissions(PermissionConst.USER_UPDATE)
    @ResponseBody
    public boolean updateEmp(Emp emp) {
        boolean success = empService.updateEmp(emp);
        return success;
    }

    // 跳转到修改密码页面
    @SystemLog(module="用户管理",methods="修改密码页面")
    @RequestMapping("modpwd")
    public String modelPwd(Model model, HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        Object principal = subject.getPrincipal();
        Emp byId = empService.getById(principal.toString());
        request.setAttribute("emp", byId);
        return "changePwd";
    }

    // 修改密码
    @RequestMapping("/updateP")
    @SystemLog(module="用户管理",methods="修改密码")
    @ResponseBody
    public Map<String, Object> updatePwd(Emp emp, String oldPassWord) {
        Map<String, Object> updatePwd = empService.updatePwd(emp, oldPassWord);
        return updatePwd;
    }

    // 展示所有信息
    @SystemLog(module="用户管理",methods="查询所有用户")
    @RequestMapping("/empl")
    @RequiresPermissions(PermissionConst.USER_VIEWALL)
    @ResponseBody
    public Map<String, Object> all(@RequestParam(name = "page", required = true, defaultValue = "1") Integer pageNum,
            @RequestParam(name = "limit", required = true, defaultValue = "2") Integer pageSize,
            HttpServletRequest request, Model model) {
        PageHelper.startPage(pageNum, pageSize);
        List<Emp> list = empService.queryEmp();
        PageInfo<Emp> p = new PageInfo<Emp>(list);
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "ok");
        map.put("count", p.getTotal());
        map.put("data", list);
        return map;
    }

    @SystemLog(module="用户管理",methods="添加用户页面")
    @RequestMapping("add2")
    @RequiresRoles(RoleConst.ADMIN)
    public String getStation(Model model) {
        List<Station> lis = stationService.getAll();
        model.addAttribute("lis", lis);
        return "addEmp";
    }

    // 多条件查找
    @SystemLog(module="用户管理",methods="查询用户")
    @RequestMapping("/queryE")
    @RequiresRoles(RoleConst.ADMIN)
    @ResponseBody
    public Map<String, Object> al(@RequestParam(name = "page", required = true, defaultValue = "0") Integer pageNum,
            @RequestParam(name = "limit", required = true, defaultValue = "10") Integer pageSize,
            HttpServletRequest request, Model model, String jobNum, String empName, String isEmp, String stationName) {
        PageHelper.startPage(pageNum, pageSize);
        List<Emp> list = empService.queryE(jobNum, empName, isEmp, stationName);
        PageInfo<Emp> p = new PageInfo<Emp>(list);
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "ok");
        map.put("count", p.getTotal());
        map.put("data", list);
        return map;
    }

}
