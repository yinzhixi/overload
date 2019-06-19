package com.jm.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jm.bean.Resource;
import com.jm.service.ResourceService;
import com.jm.web.log.SystemLog;
@Controller
@RequestMapping("/resource")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;
    
    
    /**
     * 首页展示
     * @return
     */
    
    //@RequiresPermissions("user:view")    
    @SystemLog(module = "导航", methods = "菜单")
    @RequestMapping("/getAll")
    @ResponseBody
    public List<Resource> getAll(){
         List<Resource> all = resourceService.getAll();
         return all;
    }
}

