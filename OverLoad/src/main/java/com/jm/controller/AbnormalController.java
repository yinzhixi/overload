package com.jm.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jm.bean.*;
import com.jm.dao.UserStationDao;
import com.jm.data.base.PermissionConst;
import com.jm.service.AbnormalService;
import com.jm.service.DictService;
import com.jm.service.EmpService;
import com.jm.service.StationService;
import com.jm.serviceImpl.SystemProperties;
import com.jm.web.log.SystemLog;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("abnormal")
@Controller
public class AbnormalController {

    @Autowired
    private AbnormalService abnormalService;

    @Autowired
    private StationService stationService;

    @Autowired
    private DictService dictService;

    @Autowired
    private UserStationDao userStationDao;

    @Autowired
    private EmpService empService;
    /**
     * 异常数据回收站 首页显示 负责跳转
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    @SystemLog(module = "异常数据", methods = "首页")
    @RequestMapping("getToAbnormal")
    public String getToAbnormal(Model model) {
        model.addAttribute("station", getUserStation());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date cur = new Date();
        String curDate = sdf.format(cur);
        model.addAttribute("curDay", curDate + " 00:00:00");
        model.addAttribute("nextDay", curDate + " 23:59:59");
        model.addAttribute("directions", this.dictService.findByType(DictService.DICT_DIRECTION));

        return "abnormal/abnormalList";
    }

    /**
     * 预检明细
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @SystemLog(module = "车辆异常数据明细", methods = "查询")
    @RequestMapping("/abnormalList")
    @ResponseBody
    public ResultDo<List<Abnormal>> abnormalList(String startDate, String endDate, String station, Integer lane,
                                                String carNum, Integer axleCnt, Double startWeight, Double endWeight, Integer overRate, Integer recognition,
                                                Integer direction,
                                                Integer previewId,
                                                Integer overRate_op,
                                                Integer axleCnt_op,
                                                Integer overRate_num,
                                                @RequestParam(name = "page", required = true, defaultValue = "1") Integer pageNum,
                                                @RequestParam(name = "limit", required = true, defaultValue = "2") Integer pageSize) {
        String[] userStationCodes=getUserStationCodes();
        PageHelper.startPage(pageNum, pageSize);
        startWeight = startWeight == null ? null : startWeight * 1000;
        endWeight = endWeight == null ? null : endWeight * 1000;
        List<Abnormal> abnormalslist = abnormalService.getAlls(startDate, endDate, station, lane, carNum, axleCnt,axleCnt_op, startWeight,
                endWeight, overRate, recognition, direction,previewId,overRate_op,overRate_num,userStationCodes);
        PageInfo<Abnormal> p = new PageInfo<Abnormal>(abnormalslist);
        ResultDo<List<Abnormal>> res = new ResultDo<List<Abnormal>>();
        res.setCode(0);
        res.setMsg("");
        res.setData(p.getList());
        res.setCount(p.getTotal());
        return res;

    }

    /**
     * 删除异常车辆
     */
    @SystemLog(module = "异常车辆明细", methods = "删除")
    @RequestMapping("delAbnormal")
    @ResponseBody
    public int delAbnormal(Model model, Integer abnormalid) {
        int delPreview = abnormalService.delAbnormal(abnormalid);
        return delPreview;
    }


    //异常车辆详情
    @SystemLog(module = "异常管理", methods = "明细页面")
    @RequestMapping("getAllImgs")
    public String getAllImgs(Abnormal abnormal, Model model) {
        Integer id = abnormal.getAbnormalid();
        Abnormal ab = abnormalService.getAbnormalById(id);
        model.addAttribute("station", getUserStation());
        model.addAttribute("abnormal", ab);

//        String printEmpSels = systemSetService.getProperty(SystemProperties.PRINT_EMP_SELS);
//        String[] emps = StringUtils.split(printEmpSels, ";");
//        List<Map<String,String>> empList = new ArrayList<>();
//        for (String emp : emps) {
//            String[] ps = StringUtils.split(emp, ":");
//            Map<String,String> item = new HashMap<>();
//            item.put("name", ps[0]);
//            item.put("empCode", ps[1]);
//            empList.add(item);
//        }
//        model.addAttribute("empList",empList);
        return "abnormal/detail";
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
    public String[] getUserStationCodes(){
        Subject subject = SecurityUtils.getSubject();//获取登录用户id
        Emp emp=new Emp();
        if(subject != null) {
            String empName = (String)subject.getPrincipal();
            emp=empService.getById(empName);
            //previewRecord.setEmpId(emp.getId());
        }
        List<UserStation> userStationslist=userStationDao.getByEmpId(emp.getId());//获取登录员工所有的站点权限

        String[] stationCodes=new String[userStationslist.size()];
        for(int j=0;j<userStationslist.size();j++){
            stationCodes[j]=userStationslist.get(j).getStationCode();
        }
        return stationCodes;
    }
}
