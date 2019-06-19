package com.jm.controller;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jm.bean.*;
import com.jm.dao.UserStationDao;
import com.jm.service.EmpService;
import com.jm.service.PreviewCalcService;
import com.jm.service.StationService;
import com.jm.utils.export.ExportExcelUtils;
import com.jm.utils.export.SheetView;
import com.jm.utils.export.Table;
import com.jm.web.log.SystemLog;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/preview/calc")
public class PreviewCalcController {
    @Autowired
    private PreviewCalcService previewCalcService;
    @Autowired
    private StationService stationService;
    @Autowired
    private EmpService empService;
    @Autowired
    private UserStationDao userStationDao;

    @SystemLog(module = "预检统计", methods = "主页面")
    @RequestMapping
    public String calcIndexPage(ModelMap model) {
        model.put("stations", getUserStation());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date cur = new Date();
        String curDate = sdf.format(cur);
        model.addAttribute("curDay", curDate + " 00:00:00");
        model.addAttribute("nextDay", curDate + " 23:59:59");
        return "/calc/previewCalc";
    }

    @SystemLog(module = "预检统计", methods = "查询")
    @RequestMapping("/query")
    @ResponseBody
    public ResultDo<List<PreviewCalc>> calcQuery(String startDate, String endDate, String station,
            @RequestParam(name = "page", required = true, defaultValue = "1") Integer pageNum,
            @RequestParam(name = "limit", required = true, defaultValue = "2") Integer pageSize) {
        int[] userStationIds=getUserStationIds();
        PageHelper.startPage(pageNum, pageSize);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date cur = new Date();
        String curDate = sdf.format(cur);
        if (StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)) {
            startDate = curDate;
            endDate = curDate;
        }
        List<PreviewCalc> list = previewCalcService.previewCalc(startDate, endDate, station,userStationIds);
        PageInfo<PreviewCalc> p = new PageInfo<PreviewCalc>(list);
        ResultDo<List<PreviewCalc>> res = new ResultDo<List<PreviewCalc>>();
        res.setCode(0);
        res.setMsg("");
        res.setData(p.getList());
        res.setCount(p.getTotal());
        return res;
    }

    @SystemLog(module = "预检统计", methods = "导出")
    @RequestMapping("/query/export")
    public void export(String startDate, String endDate, String station, HttpServletResponse response,

            HttpServletRequest request) {
        int[] userStationIds=getUserStationIds();
        List<PreviewCalc> list = previewCalcService.previewCalc(startDate, endDate, station,userStationIds);
        List<Map<String, Object>> data = new ArrayList<>();
        for (PreviewCalc previewCalc : list) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("date", previewCalc.getDate());
            item.put("vehicleTypeName", previewCalc.getVehicleTypeName());
            item.put("totalNum", previewCalc.getTotalNum());
            item.put("overNum", previewCalc.getOverNum());
            data.add(item);
        }

        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sf.format(new Date());
        String filename = "PreviewCalc" + date + ".xls";
        List<SheetView> sheets = new ArrayList<SheetView>();
        sheets.add(new SheetView("高速预检统计报表",
                new Table("高速预检统计报表", 0, 0,
                        new Object[][] { { "date", "日期", 256 * 10, ExportExcelUtils.STRING_CELL },
                                { "vehicleTypeName", "车型", 256 * 10, ExportExcelUtils.STRING_CELL },
                                { "totalNum", "车辆总数（辆）", 256 * 15, ExportExcelUtils.STRING_CELL },
                                { "overNum", "超限总数（辆）", 256 * 15, ExportExcelUtils.STRING_CELL }, },
                        data)));
        ExportExcelUtils.export(response, request, filename, sheets);
    }

    @SystemLog(module = "预检统计", methods = "按吨位-主页面")
    @RequestMapping("/byWeight")
    public String calcByWeightIndex(ModelMap model) {
        model.put("stations",getUserStation());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date cur = new Date();
        String curDate = sdf.format(cur);
        model.addAttribute("curDay", curDate + " 00:00:00");
        model.addAttribute("nextDay", curDate + " 23:59:59");
        return "/calc/previewCalcByWeight";
    }

    @SystemLog(module = "预检统计", methods = "按吨位-查询")
    @RequestMapping("/query/byWeight")
    @ResponseBody
    public ResultDo<List<PreviewCalcByWeight>> calcQueryByWeight(String startDate, String endDate, String station) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date cur = new Date();
        String curDate = sdf.format(cur);
        if (StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)) {
            startDate = curDate;
            endDate = curDate;
        }
        List<PreviewCalcByWeight> list = previewCalcService.previewCalcByWeight(startDate, endDate, station,getUserStationIds());
        this.calcPercent(list);

        ResultDo<List<PreviewCalcByWeight>> res = new ResultDo<List<PreviewCalcByWeight>>();
        res.setCode(0);
        res.setMsg("");
        res.setData(list);
        return res;
    }

    @SystemLog(module = "预检统计", methods = "按吨位-导出")
    @RequestMapping("/query/byWeight/export")
    public void exportByWeight(String startDate, String endDate, String station, HttpServletResponse response,
            HttpServletRequest request) {
        List<PreviewCalcByWeight> list = previewCalcService.previewCalcByWeight(startDate, endDate, station,getUserStationIds());
        this.calcPercent(list);
        List<Map<String, Object>> data = new ArrayList<>();
        int i = 0;
        for (PreviewCalcByWeight previewCalcByWeight : list) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("lane", ++i);
            item.put("ton", previewCalcByWeight.getTon());
            item.put("tonNum", previewCalcByWeight.getTonNum());
            item.put("percent", previewCalcByWeight.getPercent());
            data.add(item);
        }

        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sf.format(new Date());
        String filename = "按吨位统计报表" + date + ".xls";
        List<SheetView> sheets = new ArrayList<SheetView>();
        sheets.add(new SheetView("按吨位统计报表",
                new Table("按吨位统计报表", 0, 0,
                        new Object[][] { { "lane", "序号", 256 * 10, ExportExcelUtils.STRING_CELL },
                                { "ton", "吨位", 256 * 10, ExportExcelUtils.STRING_CELL },
                                { "tonNum", "数量（辆）", 256 * 15, ExportExcelUtils.STRING_CELL },
                                { "percent", "比例%", 256 * 15, ExportExcelUtils.STRING_CELL }, },
                        data)));
        ExportExcelUtils.export(response, request, filename, sheets);
    }

    private void calcPercent(List<PreviewCalcByWeight> list) {
        int total = 0;
        for (PreviewCalcByWeight previewCalcByWeight : list) {
            total = total + (previewCalcByWeight.getTonNum() == null ? 0 : previewCalcByWeight.getTonNum());
        }
        DecimalFormat df = new DecimalFormat("0.00");
        double total2 = 0;
        for (PreviewCalcByWeight previewCalcByWeight : list) {
            double percent = (double) previewCalcByWeight.getTonNum() / (double) total * 100;
            previewCalcByWeight.setPercent(df.format(percent));
            total2 += percent;
        }
        PreviewCalcByWeight sum = new PreviewCalcByWeight();
        sum.setTon("合计");
        sum.setTonNum(total);
        sum.setPercent(df.format(total2));
        list.add(sum);
    }

    //返回全部权限站点
    public List<Station> getUserStation(){
        Subject subject = SecurityUtils.getSubject();//获取登录用户id
        Emp emp=new Emp();
        if(subject != null) {
            String empName = (String)subject.getPrincipal();
            emp=empService.getById(empName);
        }
        List<UserStation> userStationslist=userStationDao.getByEmpId(emp.getId());//获取登录员工所有的站点权限
        List<Integer> listStation=new ArrayList();
        for(int j=0;j<userStationslist.size();j++){
            listStation.add(userStationslist.get(j).getStationId());
        }
        List<Station> list2 = stationService.getAll();//查询全部站点
        List<Station> relist =new ArrayList<>();//返回用户的站点权限
        for (int i=0;i<list2.size();i++){
            Station station=list2.get(i);
            if (listStation.contains(station.getId())){
                relist.add(station);
            }
        }
        return relist;
    }

    //返回登录人员的站点id权限数组
    public int[] getUserStationIds(){
        Subject subject = SecurityUtils.getSubject();//获取登录用户id
        Emp emp=new Emp();
        if(subject != null) {
            String empName = (String)subject.getPrincipal();
            emp=empService.getById(empName);
            //previewRecord.setEmpId(emp.getId());
        }
        List<UserStation> userStationslist=userStationDao.getByEmpId(emp.getId());//获取登录员工所有的站点权限

        int[] stationIds=new int[userStationslist.size()];
        for(int j=0;j<userStationslist.size();j++){
            stationIds[j]=userStationslist.get(j).getStationId();
        }
        return stationIds;
    }
}
