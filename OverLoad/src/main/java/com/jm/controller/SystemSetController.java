package com.jm.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jm.bean.ResultDo;
import com.jm.bean.SystemSet;
import com.jm.data.base.RoleConst;
import com.jm.service.SystemSetService;
import com.jm.web.log.SystemLog;

@Controller
@RequestMapping("/system/set")
@RequiresRoles(RoleConst.ADMIN)
public class SystemSetController {
    @Autowired
    private SystemSetService systemSetService;
    
    @SystemLog(module = "系统设置", methods = "主页")
    @RequestMapping
    public String index() {
        return "system/set/index";
    }
    
    @SystemLog(module = "系统设置", methods = "查询数据")
    @RequestMapping("/list/data")
    @ResponseBody
    public ResultDo<List<SystemSet>> listData(
            @RequestParam(name = "page", required = true, defaultValue = "1") Integer page,
            @RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,  
            String key,String val){
        PageHelper.startPage(page, limit);
        List<SystemSet> list = this.systemSetService.findList(key,val);
        PageInfo<SystemSet> p = new PageInfo<SystemSet>(list);
        ResultDo<List<SystemSet>> res = new ResultDo<>();
        res.setCode(0);
        res.setMsg("");
        res.setData(p.getList());
        res.setCount(p.getTotal());
        return res;
    }
    
    @SystemLog(module = "系统设置", methods = "修改数据")
    @RequestMapping("/doUpdate")
    @ResponseBody
    public Integer update(@RequestParam(name="id") String id,@RequestParam(name="key") String key,@RequestParam(name="val") String val,
            String comment) {
        this.systemSetService.setProperty(id,key,val,comment);
        return 1;
    }
}
