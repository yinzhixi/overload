package com.jm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jm.bean.LogEntity;
import com.jm.bean.ResultDo;
import com.jm.service.LogService;
import com.jm.web.log.SystemLog;

@Controller
@RequestMapping("/system/log")
public class LogController {
    @Autowired
    private LogService logService;
    
    @SystemLog(module="系统日志",methods="主页")
    @RequestMapping
    public String index() {
        return "system/log/index";
    }

    @SystemLog(module="系统日志",methods="数据查询")
    @RequestMapping("/list/data")
    @ResponseBody
    public ResultDo<List<LogEntity>> list(
            @RequestParam(name = "page", required = true, defaultValue = "1") Integer page,
            @RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,  
            String userAccount,String module,String method){
        PageHelper.startPage(page, limit);
        List<LogEntity> list = this.logService.findList(userAccount,module,method);
        PageInfo<LogEntity> p = new PageInfo<LogEntity>(list);
        ResultDo<List<LogEntity>> res = new ResultDo<>();
        res.setCode(0);
        res.setMsg("");
        res.setData(p.getList());
        res.setCount(p.getTotal());
        return res;
    }
}
