package com.jm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jm.bean.Permission;
import com.jm.bean.ResultDo;
import com.jm.data.base.PermissionConst;
import com.jm.data.base.RoleConst;
import com.jm.service.PermissionService;
import com.jm.web.log.SystemLog;

@Controller
@RequestMapping("/permission")
@RequiresRoles(RoleConst.ADMIN)
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    
    //修改权限信息
    @RequiresPermissions(PermissionConst.PERMISSION_UPDATE)
    @SystemLog(module="角色管理",methods="修改")
    @RequestMapping("updatePermission")
    @ResponseBody
    public Map<String,String> updatePermission(Permission permiss){
    	Map<String,String> map = new HashMap<>();
    	String hint = "";
    	int updatePermission = permissionService.updatePermission(permiss);
    	if(updatePermission>0){
    		hint = "修改成功";
    	}else{
    		hint="修改失败";
    	}
    	map.put("hint", hint);
    	return map;
    }
    

    @SystemLog(module="角色管理",methods="主页面")
    @RequestMapping
    public String index(ModelMap model){
        return "/permission/index";
    }

    @SystemLog(module="角色管理",methods="查询")
    @RequestMapping("/list")
    @ResponseBody
    public ResultDo<List<Permission>> list(ModelMap model,@RequestParam(name="page",required = true, defaultValue = "1") Integer pageNum,
    		@RequestParam(name="limit",required = true, defaultValue = "2") Integer pageSize){
    	PageHelper.startPage(pageNum, pageSize);
        List<Permission> list = this.permissionService.getAll();
        PageInfo<Permission> p = new PageInfo<Permission>(list);
        ResultDo<List<Permission>> res = new ResultDo<>();
        res.setCode(0);
        res.setMsg("");
        res.setData(p.getList());
        res.setCount(p.getTotal());
        return res;
    }
    
    @RequiresPermissions(PermissionConst.PERMISSION_ADD)
    @SystemLog(module="角色管理",methods="添加")
    @RequestMapping("/save")
    @ResponseBody
    public Map<String,Object> save(@RequestParam(name="id",required=true)String id,@RequestParam(name="name",required=true)String name){
        Map<String,Object> res  = new HashMap<>();
        this.permissionService.add(id,name);
        res.put("success", true);
        return res;
    }
    
    @RequiresPermissions(PermissionConst.PERMISSION_DEL)
    @SystemLog(module="角色管理",methods="删除")
    @RequestMapping("/del")
    @ResponseBody
    public Map<String,Object> del(@RequestParam(name="id",required=true)String id){
        Map<String,Object> res  = new HashMap<>();
        this.permissionService.del(id);
        res.put("success", true);
        return res;
    }
}
