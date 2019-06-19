package com.jm.controller;

import com.jm.bean.StaticWeight;
import com.jm.service.StaticWeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/staticWeight")
@Controller
public class staticWeightController {

    @Autowired
    private StaticWeightService staticWeightService;

    @RequestMapping("/edit")
    @ResponseBody
    public Map edit(StaticWeight staticWeight){
        Map map=new HashMap();
        map.put("code",false);
        int code=staticWeightService.editStaticWeight(staticWeight);
        if(code>=1){
            map.put("code",true);
        }
        return map;
    }
}
