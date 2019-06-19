package com.jm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jm.bean.*;
import com.jm.dao.UserStationDao;
import com.jm.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jm.dao.ReverseDao;
import com.jm.web.log.SystemLog;

@Controller
@RequestMapping("/flowView")
public class FlowViewController {
    @Autowired
    private StationService stationService;
    @Autowired
    private DictService dictService;
    @Autowired
    private FlowViewService flowViewService;
    @Autowired
    private ReverseDao reverseDao;
    @Autowired
    private SystemSetService systemSetService;
    @Autowired
    private EmpService empService;
    @Autowired
    private UserStationDao userStationDao;

    @SystemLog(module = "视频流", methods = "按时间页面")
    @RequestMapping("/byTime")
    public String viewByTime(ModelMap model) {
        model.put("stations", getUserStation());
        model.put("directions", this.dictService.findByType(DictService.DICT_DIRECTION));
        return "/flowview/viewByTime";
    }
    @RequestMapping("/realTimeData")
    public String realTimeData(ModelMap model) {
        String previewPageSize=systemSetService.getProperty("preview.pageSize");//获取文件存放配置目录
        int pageSize=Integer.parseInt(previewPageSize);
        model.put("pageSize", pageSize);
        model.put("stations", getUserStation());
        model.put("directions", this.dictService.findByType(DictService.DICT_DIRECTION));
        //return "/flowview/viewByTimePro";
        return "/flowview/viewByRealTime";
    }

    @SystemLog(module = "视频流", methods = "按车牌页面")
    @RequestMapping("/byCarNum")
    public String viewByCarNum(ModelMap model) {
        model.put("stations", getUserStation());
        model.put("directions", this.dictService.findByType(DictService.DICT_DIRECTION));
        return "/flowview/viewByCarNum";
    }

    // 逆向数据
    @SystemLog(module = "逆向管理", methods = "逆向数据")
    @RequestMapping("/byCarNum/queryAllReverse")
    @ResponseBody
    public ResultDo<Map<String, Object>> queryAllReverse(String carNum, String stationName,
            @RequestParam(name = "page", required = true, defaultValue = "1") Integer pageNum,
            @RequestParam(name = "limit", required = true, defaultValue = "2") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Reverse> list = reverseDao.queryReverse(stationName, carNum, null);
        PageInfo<Reverse> p = new PageInfo<Reverse>(list);
        ResultDo<Map<String, Object>> res = new ResultDo<Map<String, Object>>();
        res.setCode(0);
        res.setMsg("");
        Map<String, Object> data = new HashMap<>();
        Integer maxId = 0;
        if (list != null && list.size() > 0) {
            maxId = list.get(0).getId();
        }
        data.put("maxId", maxId);
        data.put("list", p.getList());
        res.setData(data);
        res.setCount(p.getTotal());
        return res;
    }

    @SystemLog(module="视频流",methods="按车牌查询")
    @RequestMapping("/byCarNum/query")
    @ResponseBody
    public ResultDo<Map<String, Object>> queryByCarNum(String carNum, String station,
            @RequestParam(name = "page", required = true, defaultValue = "1") Integer pageNum,
            @RequestParam(name = "limit", required = true, defaultValue = "2") Integer pageSize) {
        String[] stationMarks=getUserStationMarks();
        PageHelper.startPage(pageNum, pageSize);
        List<FlowView> list = flowViewService.queryByCarNum(carNum, station,stationMarks);
        PageInfo<FlowView> p = new PageInfo<FlowView>(list);
        ResultDo<Map<String, Object>> res = new ResultDo<Map<String, Object>>();
        res.setCode(0);
        res.setMsg("");
        Map<String, Object> data = new HashMap<>();
        Integer maxId = 0;
        if (list != null && list.size() > 0) {
            maxId = list.get(0).getPreviewId();
        }
        data.put("maxId", maxId);
        data.put("list", p.getList());
        res.setData(data);
        res.setCount(p.getTotal());
        return res;
    }

    @SystemLog(module="视频流",methods="按车牌查询")
    @RequestMapping("/byCarNum/recive")
    @ResponseBody
    public ResultDo<Map<String, Object>> reciveByCarNum(String carNum, String station,
            @RequestParam(name = "page", required = true, defaultValue = "1") Integer pageNum,
            @RequestParam(name = "limit", required = true, defaultValue = "2") Integer pageSize, Integer maxId) {

        PageHelper.startPage(pageNum, pageSize);
        List<FlowView> list = flowViewService.reciveByCarNum(carNum, station, maxId);
        PageInfo<FlowView> p = new PageInfo<FlowView>(list);
        ResultDo<Map<String, Object>> res = new ResultDo<Map<String, Object>>();
        res.setCode(0);
        res.setMsg("");
        Map<String, Object> data = new HashMap<>();
        if (list != null && list.size() > 0) {
            maxId = list.get(0).getPreviewId();
        }
        data.put("maxId", maxId);
        data.put("list", p.getList());
        res.setData(data);
        res.setCount(p.getTotal());
        return res;
    }

    @SystemLog(module="视频流",methods="按时间查询")
    @RequestMapping("/byTime/query")
    @ResponseBody
    public ResultDo<Map<String, Object>> queryByTime(String startDate, String endDate, String station,
            Integer direction, @RequestParam(name = "page", required = true, defaultValue = "1") Integer pageNum,
            @RequestParam(name = "limit", required = true, defaultValue = "2") Integer pageSize) {

        String[] stationMarks=getUserStationMarks();
        PageHelper.startPage(pageNum, pageSize);
        List<FlowView> list = flowViewService.queryByTime(startDate, endDate, station, direction,stationMarks);
        PageInfo<FlowView> p = new PageInfo<FlowView>(list);
        ResultDo<Map<String, Object>> res = new ResultDo<Map<String, Object>>();
        res.setCode(0);
        res.setMsg("");
        Map<String, Object> data = new HashMap<>();
        Integer maxId = 0;
        if (list != null && list.size() > 0) {
            maxId = list.get(0).getPreviewId();
        }
        data.put("maxId", maxId);
        data.put("list", p.getList());
        res.setData(data);
        res.setCount(p.getTotal());
        return res;

    }
    
    @RequestMapping("/byTime/queryOverRagePreview")
    @ResponseBody
    public ResultDo<Map<String, Object>> queryOverRagePreview(@RequestParam(name = "page", required = true, defaultValue = "1") Integer pageNum,
            @RequestParam(name = "limit", required = true, defaultValue = "20") Integer pageSize ,double overRage) {

        String[] stationMarks=getUserStationMarks();
    	String previewPageSize=systemSetService.getProperty("preview.pageSize");//获取文件存放配置目录
    	pageSize=Integer.parseInt(previewPageSize);
        PageHelper.startPage(pageNum, pageSize);
        List<FlowView> list = flowViewService.queryOverRagePreview(overRage,stationMarks);
        PageInfo<FlowView> p = new PageInfo<FlowView>(list);
        ResultDo<Map<String, Object>> res = new ResultDo<Map<String, Object>>();
        res.setCode(0);
        res.setMsg("");
        Map<String, Object> data = new HashMap<>();
        Integer maxId = 0;
        if (list != null && list.size() > 0) {
            maxId = list.get(0).getPreviewId();
        }
        data.put("maxId", maxId);
        data.put("list", p.getList());
        res.setData(data);
        res.setCount(p.getTotal());
        return res;

    }

    @SystemLog(module="视频流",methods="按时间查询")
    @RequestMapping("/byTime/recive")
    @ResponseBody
    public ResultDo<Map<String, Object>> reciveByTime(String startDate, String endDate, String station,
            @RequestParam(name = "page", required = true, defaultValue = "1") Integer pageNum,
            @RequestParam(name = "limit", required = true, defaultValue = "2") Integer pageSize, Integer maxId) {
        PageHelper.startPage(pageNum, pageSize);
        List<FlowView> list = flowViewService.reciveByTime(startDate, endDate, station, maxId);
        PageInfo<FlowView> p = new PageInfo<FlowView>(list);
        ResultDo<Map<String, Object>> res = new ResultDo<Map<String, Object>>();
        res.setCode(0);
        res.setMsg("");
        Map<String, Object> data = new HashMap<>();
        if (list != null && list.size() > 0) {
            maxId = list.get(0).getPreviewId();
        }
        data.put("maxId", maxId);
        data.put("list", p.getList());
        res.setData(data);
        res.setCount(p.getTotal());
        return res;
    }

    //返回登录人员权限的站点名称数组
    public String[] getStationNames(){
        Subject subject = SecurityUtils.getSubject();//获取登录用户id
        Emp emp=new Emp();
        if(subject != null) {
            String empName = (String)subject.getPrincipal();
            emp=empService.getById(empName);
            //previewRecord.setEmpId(emp.getId());
        }
        List<UserStation> userStationslist=userStationDao.getByEmpId(emp.getId());//获取登录员工所有的站点权限

        String[] stationIds=new String[userStationslist.size()];
        for(int j=0;j<userStationslist.size();j++){
            Station station=stationService.getById(userStationslist.get(j).getStationId());
            stationIds[j]=station.getStationName();
        }
        return stationIds;
    }

    //返回登录人员的站点权限数组
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

    //返回登录人员的站点marks权限数组
    public String[] getUserStationMarks(){
        Subject subject = SecurityUtils.getSubject();//获取登录用户id
        Emp emp=new Emp();
        if(subject != null) {
            String empName = (String)subject.getPrincipal();
            emp=empService.getById(empName);
            //previewRecord.setEmpId(emp.getId());
        }
        List<UserStation> userStationslist=userStationDao.getByEmpId(emp.getId());//获取登录员工所有的站点权限

        String[] stationMarks=new String[userStationslist.size()];
        for(int j=0;j<userStationslist.size();j++){
            stationMarks[j]=userStationslist.get(j).getStationCode();
        }
        return stationMarks;
    }
}
