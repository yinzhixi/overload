package com.jm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jm.bean.PreviewCalcByHour;
import com.jm.bean.PreviewCalcByOverPercent;
import com.jm.bean.PreviewCalcByRecognitionRate;
import com.jm.bean.PreviewCalcByWeight;
import com.jm.bean.Ton;
import com.jm.service.PreviewCalcService;
import com.jm.service.TonRangeService;
import com.jm.web.log.SystemLog;

@Controller
@RequestMapping("/main")
public class HomePageController {
    @Autowired
    private PreviewCalcService previewCalcService;
    @Autowired
    private TonRangeService tonRangeService;

    @RequestMapping("/stc")
    public String stc(Model model){
        return "/stc/view";
    }

    @SystemLog(module="主页",methods="主页面")
    @RequestMapping
    public String index(ModelMap model){
        Date curr = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
        model.put("curr", sf.format(curr));
        return "/main";
    }
    @SystemLog(module="应急",methods="主页面")
    @RequestMapping("/yj")
    public String yj(ModelMap model){
        Date curr = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
        model.put("curr", sf.format(curr));
        return "/yj/main2";
    }

    @SystemLog(module="主页",methods="按吨位汇总")
    @RequestMapping("/calc/byWeight")
    @ResponseBody
    public Map<String,Object> calcByWeight(ModelMap model){
        Map<String,Object> res = new HashMap<String,Object>();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String curDate = sf.format(new Date());
        List<PreviewCalcByWeight> list = this.previewCalcService.previewCalcByTon(curDate, curDate, null);
        List<Ton> tonList = this.tonRangeService.getAll();
        res.put("success", true);
        res.put("list", list);
        res.put("tonRanges", tonList);
        return res;
    }

    @SystemLog(module="主页",methods="超限率统计")
    @RequestMapping("/calc/byOverPercent")
    @ResponseBody
    public Map<String,Object> calcByOverPercent(ModelMap model){
        Map<String,Object> res = new HashMap<String,Object>();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String curDate = sf.format(new Date());
        List<PreviewCalcByOverPercent> list = this.previewCalcService.previewCalcByOverPercent(curDate, curDate, null);
        res.put("success", true);
        res.put("list", list);
        return res;
    }

    @SystemLog(module="主页",methods="识别率统计")
    @RequestMapping("/calc/byRecognitionRate")
    @ResponseBody
    public Map<String,Object> calcByRecognitionRate(ModelMap model){
        Map<String,Object> res = new HashMap<String,Object>(); 
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String curDate = sf.format(new Date());
        List<PreviewCalcByRecognitionRate> list = this.previewCalcService.calcByRecognitionRate(curDate, curDate, null);
        res.put("success", true);
        res.put("list", list);
        return res;
    }

    @SystemLog(module="主页",methods="时间段统计")
    @RequestMapping("/calc/byHour")
    @ResponseBody
    public Map<String,Object> calcByHour(ModelMap model){
        Map<String,Object> res = new HashMap<String,Object>();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String curDate = sf.format(new Date());
        List<PreviewCalcByHour> list = this.previewCalcService.calcByHour(curDate, curDate, null);
        res.put("success", true);
        res.put("list", list);
        return res;
    }
    
}
