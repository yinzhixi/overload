package com.jm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jm.bean.Dict;
import com.jm.bean.PreviewCalc;
import com.jm.bean.ResultDo;
import com.jm.data.base.RoleConst;
import com.jm.service.DictService;
import com.jm.web.log.SystemLog;

/**    
 * @Title: DictController
 * @Description: 字典表
 * @Company:
 * @author: yinzhixi       
 * @created: 2019年1月7日 上午10:34:45    
 */
@Controller
@RequestMapping("/dict")
@RequiresRoles(RoleConst.ADMIN)
public class DictController {
	
	@Autowired
	private DictService DictService;
	
    @SystemLog(module = "字典管理", methods = "主页面")
    @RequestMapping("/list")
    public String calcIndexPage(ModelMap model) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date cur = new Date();
        String curDate = sdf.format(cur);
        model.addAttribute("curDay", curDate + " 00:00:00");
        model.addAttribute("nextDay", curDate + " 23:59:59");
        return "/dict/dictList";
    }
    
    @RequestMapping("/query")
    @ResponseBody
    public ResultDo<List<Dict>> calcQuery(String startDate, String endDate, String type,
            @RequestParam(name = "page", required = true, defaultValue = "1") Integer pageNum,
            @RequestParam(name = "limit", required = true, defaultValue = "2") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date cur = new Date();
        String curDate = sdf.format(cur);
        if (StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)) {
            startDate = curDate;
            endDate = curDate;
        }
        List<Dict> list = DictService.findByType(type);
        PageInfo<Dict> p = new PageInfo<Dict>(list);
        ResultDo<List<Dict>> res = new ResultDo<List<Dict>>();
        res.setCode(0);
        res.setMsg("");
        res.setData(p.getList());
        res.setCount(p.getTotal());
        return res;
    }

}
