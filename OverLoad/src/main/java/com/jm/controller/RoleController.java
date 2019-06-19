package com.jm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jm.bean.Emp;
import com.jm.bean.Permission;
import com.jm.bean.Resource;
import com.jm.bean.ResultDo;
import com.jm.bean.Role;
import com.jm.dao.EmpDao;
import com.jm.dao.RoleDao;
import com.jm.data.base.PermissionConst;
import com.jm.data.base.RoleConst;
import com.jm.service.RoleService;
import com.jm.web.log.SystemLog;

@Controller
@RequestMapping("/role")
@RequiresRoles(RoleConst.ADMIN)
public class RoleController {//123456
    @Autowired
    private RoleService roleService;
    @Autowired
    private EmpDao empDao;
    @Autowired
    private RoleDao roleDao;

    // 修改角色
    @RequiresPermissions(PermissionConst.ROLE_UPDATE)
    @SystemLog(module = "角色管理", methods = "修改")
    @RequestMapping("updateRole")
    @ResponseBody
    public Map<String, String> updateRole(Role role) {
        Map<String, String> map = new HashMap<>();
        String hint = "";
        int updateRole = roleService.updateRole(role);
        if (updateRole > 0) {
            hint = "修改成功";
        } else {
            hint = "修改失败";
        }
        map.put("hint", hint);
        return map;
    }

    @SystemLog(module = "角色管理", methods = "主页")
    @RequestMapping
    public String index(ModelMap model) {
        return "/role/index";
    }

    @SystemLog(module = "角色管理", methods = "查询")
    @RequestMapping("/list")
    @ResponseBody
    public ResultDo<List<Role>> list(ModelMap model) {
        List<Role> list = this.roleService.getAll();
        ResultDo<List<Role>> res = new ResultDo<>();
        res.setCode(0);
        res.setMsg("");
        res.setData(list);
        res.setCount(list.size());
        return res;
    }

    @RequiresPermissions(PermissionConst.ROLE_ADD)
    @SystemLog(module = "角色管理", methods = "增加")
    @RequestMapping("/save")
    @ResponseBody
    public Map<String, Object> save(Role role) {
        Map<String, Object> add = roleService.add(role);
        return add;
    }

    @RequiresPermissions(PermissionConst.ROLE_DEL)
    @SystemLog(module = "角色管理", methods = "删除")
    @RequestMapping("/del")
    @ResponseBody
    public Map<String, Object> del(@RequestParam(name = "id", required = true) String id) {
        Map<String, Object> res = new HashMap<>();
        this.roleService.del(id);
        res.put("success", true);
        return res;
    }

    @RequiresPermissions(PermissionConst.ROLE_EDITPERMISSION)
    @SystemLog(module = "权限管理", methods = "删除")
    @RequestMapping("/permissions/page")
    public String rolePermissions(ModelMap model, @RequestParam(name = "id") String id) {
        model.put("id", id);
        model.put("role", this.roleService.get(id));
        return "/role/permissions";
    }

    // 角色信息和菜单的关系
    @SystemLog(module = "角色管理", methods = "角色菜单页面")
    @RequestMapping("/menu")
    public String menu(ModelMap model, @RequestParam(name = "id") String id) {
        model.put("id", id);
        model.put("role", this.roleService.get(id));
        return "/role/menuRole";
    }

    // 角色拿到所有的菜单
    @SystemLog(module = "角色管理", methods = "角色菜单数据")
    @RequestMapping("/menu/data")
    @ResponseBody
    public JSONObject userMenu(@RequestParam(name = "id") String id) {

        Subject subject = SecurityUtils.getSubject();
        Object principal = subject.getPrincipal();
        Emp emp = empDao.selectById(principal.toString());

        List<Resource> allRoles = null;
        if (emp.getId() == 1) {
            allRoles = roleDao.queryAll();
        } else {

            String roleIdsByUser = roleService.getRoleIdsByUser(principal.toString());
            allRoles = roleDao.queryAllMenuByRolrId(roleIdsByUser);
        }

        JSONArray user_arr = new JSONArray();
        JSONObject user_json = null;
        for (Resource resource : allRoles) {
            user_json = new JSONObject();
            user_json.put("id", resource.getMenuId());
            user_json.put("name", resource.getTitle());
            user_json.put("pId", resource.getParentId());
            user_arr.add(user_json);
        }

        JSONObject rs = new JSONObject();
        List<Resource> queryAllMenuByRolrId = roleDao.queryAllMenuByRolrId(id);
        JSONArray role_arr = new JSONArray();
        JSONObject role_json = null;
        for (Resource resource : queryAllMenuByRolrId) {
            role_json = new JSONObject();
            role_json.put("id", resource.getMenuId());
            role_json.put("name", resource.getTitle());
            role_json.put("pId", resource.getParentId());
            role_arr.add(role_json);
        }
        rs.put("data", user_arr);
        rs.put("cur_role_data", role_arr);
        return rs;
    }

    @SystemLog(module = "权限管理", methods = "角色权限数据")
    @RequestMapping("/permissions/data")
    @ResponseBody
    public ResultDo<List<Permission>> rolePermissionsData(ModelMap model, @RequestParam(name = "id") String id) {
        List<Permission> list = this.roleService.findAllPermissions(id);
        ResultDo<List<Permission>> res = new ResultDo<>();
        res.setCode(0);
        res.setMsg("");
        res.setData(list);
        res.setCount(list.size());
        return res;
    }
    
    @RequiresPermissions(PermissionConst.ROLE_EDITPERMISSION)
    @SystemLog(module = "权限管理", methods = "修改角色权限")
    @RequestMapping("/permissions/change")
    @ResponseBody
    public Map<String, Object> rolePermissionsChange(ModelMap model, String roleId, String id,
            @RequestParam(name = "type") String type, @RequestParam(name = "isAssigned") boolean isAssigned) {
        Map<String, Object> res = new HashMap<>();
        if ("one".equals(type)) {
            this.roleService.changeRolePermission(roleId, id, isAssigned);
        }
        if ("all".equals(type)) {
            if (isAssigned) {
                this.roleService.assignAllPermission(roleId);
            } else {
                this.roleService.cancleAllPermission(roleId);
            }
        }

        res.put("success", true);
        return res;
    }

    @SystemLog(module = "角色管理", methods = "授权菜单")
    @RequestMapping("/roleMenu/change")
    @ResponseBody
    public JSONObject roleAuth(String roleId, String menuIds) {
        return roleService.roleAuth(roleId, menuIds);
    }
}
