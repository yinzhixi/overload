package com.jm.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jm.bean.PhysicaPath;
import com.jm.bean.ResultDo;
import com.jm.bean.SystemSet;
import com.jm.data.base.RoleConst;
import com.jm.service.PhysicaPathService;
import com.jm.web.log.SystemLog;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/system/path")
@RequiresRoles(RoleConst.ADMIN)
public class PhysicaPathController {

    @Autowired
    private PhysicaPathService physicaPathService;

    @RequestMapping
    public String index() {
        return "system/path/index";
    }

    @SystemLog(module = "系统路径设置", methods = "查询数据")
    @RequestMapping("/list/data")
    @ResponseBody
    public ResultDo<List<PhysicaPath>> listData(
            @RequestParam(name = "page", required = true, defaultValue = "1") Integer page,
            @RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,
            String pathname,String pathCode){
        PageHelper.startPage(page, limit);
        List<PhysicaPath> list = this.physicaPathService.findList(pathname,pathCode);
        PageInfo<PhysicaPath> p = new PageInfo<PhysicaPath>(list);
        ResultDo<List<PhysicaPath>> res = new ResultDo<>();
        res.setCode(0);
        res.setMsg("");
        res.setData(p.getList());
        res.setCount(p.getTotal());
        return res;
    }

    @SystemLog(module = "系统路径设置", methods = "修改数据")
    @RequestMapping("/doUpdate")
    @ResponseBody
    public Integer update(@RequestParam(name="id") String id,@RequestParam(name="pathname") String pathname,@RequestParam(name="pathCode") String pathCode,
                          @RequestParam(name="status") String status,String path) {
        int conut=this.physicaPathService.updatePath(id,pathname,pathCode,status,path);
        return conut;
    }
}
