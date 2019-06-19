package com.jm.controller;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jm.bean.*;
import com.jm.dao.*;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jm.data.base.PermissionConst;
import com.jm.service.DictService;
import com.jm.service.EmpService;
import com.jm.service.PreviewPreService;
import com.jm.service.PreviewRecordService;
import com.jm.service.PreviewService;
import com.jm.service.StationService;
import com.jm.service.SystemSetService;
import com.jm.serviceImpl.SystemProperties;
import com.jm.web.log.SystemLog;

@Controller
@RequestMapping("/preview")
public class PreviewController {
    @Autowired
    private PreviewService service;
    @Autowired
    private StationService stationServcie;
    @Autowired
    private PreviewPreService servicePre;

    @Autowired
    private CarDao carDao;
    @Autowired
    private PreviewDao previewDao;
    @Autowired
    private PreviewPreDao dao;
    @Autowired
    private PreviewImgDao dao2;
    @Autowired
    private PreviewRecordService previewRecordService;
    @Autowired
    private EmpService empService;
    @Autowired
    private ReverseDao reverseDao;
    // 预检实时数据显示最后五十条数据
    @Autowired
    private UserStationDao userStationDao;
    @Autowired
    private FileidxDao fileidxDao;

    // @RequestParam(name="limit", required = true, defaultValue = "2")
    @Autowired
    private SystemSetService systemSetService;

    @SystemLog(module = "预检实时数据", methods = "按吨位-主页面")
    @RequestMapping("/getFinallyData")
    @ResponseBody
    public ResultDo<List<Preview>> getFinallyData(
            @RequestParam(name = "page", required = true, defaultValue = "1") Integer pageNum, Integer pageSize,
            String station, Integer overLoadRate) {
        PageHelper.startPage(pageNum, pageSize);
        List<Preview> list = previewDao.getFinallyData(pageSize, station, overLoadRate);
        PageInfo<Preview> p = new PageInfo<Preview>(list);
        ResultDo<List<Preview>> res = new ResultDo<List<Preview>>();
        res.setCode(0);
        res.setMsg("");
        res.setData(p.getList());
        res.setCount(p.getTotal());
        return res;

    }

    @SystemLog(module = "逆向", methods = "主页面")
    @RequestMapping("getTo")
    public String getTo(Model map) {
        List<Station> list8 = stationServcie.getAll();
        map.addAttribute("stations", list8);
        return "nixiang/viewByCarNum";
    }
    @SystemLog(module = "逆向", methods = "主页面")
    @RequestMapping("getToReverse")
    public String getToReverse(Model map) {
        List<Station> list8 = stationServcie.getAll();
        map.addAttribute("stations", list8);
        return "nixiang/reverseList";
    }

    @RequestMapping("getReverseList")
    @ResponseBody
    public ResultDo<List<Reverse>> getReverseList(String startDate, String endDate, String stationName, Integer lane,
                                                  String carNum,
                                                  @RequestParam(name = "page", required = true, defaultValue = "1") Integer pageNum,
                                                  @RequestParam(name = "limit", required = true, defaultValue = "2") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Reverse> list = reverseDao.reverseList(stationName,carNum,startDate,endDate);
        PageInfo<Reverse> p = new PageInfo<Reverse>(list);
        ResultDo<List<Reverse>> res = new ResultDo<List<Reverse>>();
        res.setCode(0);
        res.setMsg("");
        res.setData(p.getList());
        res.setCount(p.getTotal());
        return res;
    }

    @RequestMapping("reverseDel")
    public String reverseDel(Integer id){
        int cont=reverseDao.reverseDel(id);
        if(cont>0){
            return "删除成功";
        }
        return "删除失败";
    }
    @RequestMapping("reverDetail")
    public String reverDetail(String id,Model model){
        Reverse reverse=reverseDao.reversById(Integer.parseInt(id));
        model.addAttribute("reverse",reverse);
        return "nixiang/reverDetail";
    }

    // 跳转到照片首页业并打印
    @SystemLog(module = "预检明细", methods = "照片首页业并打印")
    @RequestMapping("showPhoto")
    public String showPhoto(Preview preview, Model model) {
        //model.addAttribute("preview885", service.getPreviewById(preview.getPreviewId()));

        Car queryByCar = carDao.queryByCar(preview.getPlatenum());
        model.addAttribute("car", queryByCar);
        return "preview/index";
    }

    // 照片处理
    @RequestMapping("showPhoto3")
    public String showPhoto3(Integer previewId, Model model) {
        model.addAttribute("pho", service.getPreviewById(previewId));
        return "previewPre/photos1";
    }

    // 照片处理
    @RequestMapping("showPhoto4")
    public String showPhoto4(Integer previewId, Model model) {
        model.addAttribute("pho", service.getPreviewById(previewId));
        return "previewPre/photos2";
    }

    // 照片处理
    @RequestMapping("showPhoto5")
    public String showPhoto5(Integer id, Model model) {
        PreviewImg byId = dao2.getById(id);
        model.addAttribute("img1", byId);
        return "photo/index";
    }

    @Autowired
    private DictService dictService;

    /*@SystemLog(module = "精检管理", methods = "详情-打印")
    @RequestMapping("printPage")
    public String printPage(Preview preview, Model model) {
        String carNum = preview.getCarNum();
        String dateTime = preview.getDateTime();
        List<PreviewImg> allImgs = service.getAllImgs(carNum, dateTime);
        PreviewImg previewImg = null;
        if (allImgs.size() != 0) {
            int index = (int) (Math.random() * allImgs.size());
            previewImg = allImgs.get(index);
            model.addAttribute("imge", previewImg);
        } else {
            model.addAttribute("imge", null);
        }
        Preview pre = service.getPreviewById(preview.getPreviewId());
        model.addAttribute("per", pre);
        PreviewPre previewPre = dao.getByPreviewPreId(preview.getPreviewId());
        if(previewPre != null) {
            Dict dict = dictService.findByCode(Objects.toString(previewPre.getDirection(), "-1"));
            if (dict != null) {
                previewPre.setDirectionDesc(dict.getName());
            }
        }

        model.addAttribute("prev", previewPre);
        Car queryByCar = carDao.queryByCar(preview.getCarNum());
        model.addAttribute("car", queryByCar);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date cur = new Date();
        String curDate = sdf.format(cur);
        model.addAttribute("curDay", curDate+" 00:00:00");
        String printPageHead = systemSetService.getProperty(SystemProperties.PRINT_PAGE_HEAD);
        String printTitle = systemSetService.getProperty(SystemProperties.PRINT_TITLE);
        String printStationLocal = systemSetService.getProperty(SystemProperties.PRINT_STATION_LOCAL);
        String printPart = systemSetService.getProperty(SystemProperties.PRINT_PART);
        String printPicSelect = systemSetService.getProperty(SystemProperties.PRINT_PIC_SELECT);

        model.addAttribute("printPageHead",printPageHead);
        model.addAttribute("printTitle",printTitle);
        model.addAttribute("printStationLocal",printStationLocal);
        model.addAttribute("printPart",printPart);

        String[] picSels = StringUtils.split(printPicSelect, ",");
        if(picSels.length ==1 ) {
            model.addAttribute("imgPath1",this.getImgPathByType(picSels[0], pre, previewImg));
            model.addAttribute("imgCount", picSels.length);
        }
        if(picSels.length ==2 ) {
            model.addAttribute("imgPath1",this.getImgPathByType(picSels[0], pre, previewImg));
            model.addAttribute("imgPath2",this.getImgPathByType(picSels[1], pre, previewImg));

            model.addAttribute("imgCount", picSels.length);
        }

        if(picSels.length ==3 ) {
            model.addAttribute("imgPath1",this.getImgPathByType(picSels[0], pre, previewImg));
            model.addAttribute("imgPath2",this.getImgPathByType(picSels[1], pre, previewImg));
            model.addAttribute("imgPath3",this.getImgPathByType(picSels[2], pre, previewImg));
            model.addAttribute("imgCount", picSels.length);
        }
        String verification=systemSetService.getProperty("verification");//获取文件存放配置目录
        model.addAttribute("verification", verification);

        String printEmpSels = systemSetService.getProperty(SystemProperties.PRINT_EMP_SELS);
        String[] emps = StringUtils.split(printEmpSels, ";");
        List<Map<String,String>> empList = new ArrayList<>();
        for (String emp : emps) {
            String[] ps = StringUtils.split(emp, ":");
            Map<String,String> item = new HashMap<>();
            item.put("name", ps[0]);
            item.put("empCode", ps[1]);
            empList.add(item);
        }
        model.addAttribute("empList",empList);
        model.addAttribute("enforcement",preview.getEnforcement());
        model.addAttribute("enforcementTwo",preview.getEnforcementTwo());
        String value=systemSetService.getProperty("print.view");//获取文件存放配置目录
        if("1".equals(value)){
        	return "previewPre/printPro";
        }
        return "previewPre/print";
    }
*/
    /**
     * #打印显示图片 1前抓拍 2后抓拍 3车身抓拍 4车顶抓拍 5情报板抓拍
     * @param type
     * @return
     */
    /*private String getImgPathByType(String type,Preview preview,PreviewImg previewImg) {
        if(preview == null) {
            return "";
        }else {
            if("1".equals(type)) {
                return preview.getFrontPic();
            }else if("2".equals(type)) {
                return preview.getBackPic();
            }else if("3".equals(type)) {
                return preview.getSidePic();
            }else if("4".equals(type)) {
                return preview.getUpPic();
            }
        }

        if("5".equals(type)) {
            if(previewImg == null) {
                return "";
            }else {
                return previewImg.getImg();
            }
        }else {
            return "";
        }
    }*/

    //预检详细信息 审核页面
    @RequiresPermissions(PermissionConst.PREVIEW_REVIEWED)
    @SystemLog(module = "预检管理", methods = "明细页面")
    @RequestMapping("getAllImgs")
    public String getAllImgs(Preview preview, Model model) {
        Integer previewId = preview.getPreviewId();
        String carNum = preview.getCarNum();
        String dateTime = preview.getDateTime();
        List<PreviewImg> allImgs = service.getAllImgs(carNum, dateTime);
        List<Station> list8 = stationServcie.getAll();
        model.addAttribute("station", list8);
        model.addAttribute("img", allImgs);
        PreviewPre quaryByPreviewId = servicePre.quaryByPreviewId(previewId);
        model.addAttribute("previewId", quaryByPreviewId);

        String printEmpSels = systemSetService.getProperty(SystemProperties.PRINT_EMP_SELS);
        String[] emps = StringUtils.split(printEmpSels, ";");
        List<Map<String,String>> empList = new ArrayList<>();
        for (String emp : emps) {
            String[] ps = StringUtils.split(emp, ":");
            Map<String,String> item = new HashMap<>();
            item.put("name", ps[0]);
            item.put("empCode", ps[1]);
            empList.add(item);
        }
        model.addAttribute("empList",empList);

        return "shenhe/shenhe";
    }

    /**
     * 修改
     */

   /* @SystemLog(module = "预检明细", methods = "修改")
    @RequestMapping("doUpdatePreview")
    @ResponseBody
    public int doUpdatePreview(@RequestParam(name="previewId")Integer previewId,Integer lane,String carNum,
            Integer axleCnt,Integer speed,Double sumWeight,Double limitWeight,String frontPic,String backPic,String sidePic,String upPic,String dateTime) {

    	Preview preview = service.getPreviewById(previewId);
    	PreviewRecord previewRecord=new PreviewRecord();
    	previewRecord=getPreviewRecord(preview);
    	previewRecordService.addPreviewRecord(previewRecord);
        preview.setPreviewId(previewId);
        //preview.setCreateTime(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        preview.setDateTime(dateTime);
        preview.setLane(lane);
        preview.setCarNum(carNum);
        preview.setAxleCnt(axleCnt);
        preview.setSpeed(speed);
        preview.setSumWeight(sumWeight);
        preview.setLimitWeight(limitWeight);
        if(limitWeight == null) {
            limitWeight = 0.0;
        }
        if(sumWeight == null) {
            sumWeight = 0.0;
        }
        double overRage = sumWeight - limitWeight;
        overRage = overRage < 0 ? 0:overRage;
        preview.setOverRage(overRage);
        preview.setOverLoadRate((int)(overRage/limitWeight*100));
        String relCode=preview.getRelCode();
        if(StringUtils.isNotEmpty(relCode)){
            try {
                updateFiledix(relCode,frontPic,backPic,sidePic,upPic);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //int updatePreview = service.updatePreview(previewId,lane,carNum,axleCnt,speed,sumWeight,limitWeight,frontPic);
        int updatePreview = service.updatePreview(preview);
        return updatePreview;
    }*/

    /**
     * 删除明细
     */
    @RequiresPermissions(PermissionConst.PREVIEW_DEL)
    @SystemLog(module = "预检明细", methods = "删除")
    @RequestMapping("delPreview")
    @ResponseBody
    public int delPreview(Model model, Integer previewId) {
        int delPreview = service.delPreview(previewId);
        return delPreview;
    }

    /**
     * 预检明细修改前查询
     */
    @RequiresPermissions(PermissionConst.PREVIEW_UPDATE)
    @SystemLog(module = "预检明细", methods = "修改页面")
    @RequestMapping("updatePreview")
    public String updatePreview(Model model, Integer previewId) {
        Preview preview = service.getPreviewById(previewId);
        model.addAttribute("preview", preview);
        return "/preview/updatePreview";
    }

    /**
     * 车牌识别率 首页显示 负责跳转
     *
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    @SystemLog(module = "车牌识别率", methods = "主页面")
    @RequestMapping("getToByCarUum")
    public String getToByCarUum(Model model) {
        //List<Station> list2 = stationServcie.getAll();
        model.addAttribute("station", getUserStation());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date cur = new Date();
        String curDate = sdf.format(cur);
        String curDate2 = curDate.substring(0, 10) + " 00:00:00";
        String curDate1 = curDate.substring(0, 10) + " 23:59:59";
        model.addAttribute("curDay", curDate2);
        model.addAttribute("nextDay", curDate1);
        return "carnumrate";
    }

    // 车牌识别率首页显示

    /*@SystemLog(module = "车牌识别率", methods = "查询")
    @RequestMapping("getByCarNum")
    @ResponseBody
    public ResultDo<List<Preview>> getByCarNum(String startDate, String endDate, String station,
            @RequestParam(name = "page", required = true, defaultValue = "1") Integer pageNum,
            @RequestParam(name = "limit", required = true, defaultValue = "2") Integer pageSize) {
        String[] userStationCodes=getUserStationCodes();
        PageHelper.startPage(pageNum, pageSize);
        List<Preview> list = service.getByCarNum(startDate, endDate, station,userStationCodes);
        PageInfo<Preview> p = new PageInfo<Preview>(list);
        this.carNum(list);
        ResultDo<List<Preview>> res = new ResultDo<List<Preview>>();
        res.setCode(0);
        res.setMsg("");
        res.setData(p.getList());
        res.setCount(p.getTotal());
        return res;
    }*/

    /*private void carNum(List<Preview> list) {
        // int total = 0;
        for (Preview preview : list) {
            // 总数量
            Integer countByAllDay = preview.getCountByAllDay();
            DecimalFormat df = new DecimalFormat("0.00");
            // 白天识别数量
            Integer countByDay = preview.getCountByDay();
            double distingByday = ((double) countByDay / (double) countByAllDay * 100);
            preview.setDistingByday(df.format(distingByday));
            // 夜晚识数量
            Integer countByNight = preview.getCountByNight();
            double distingByNight = ((double) countByNight / (double) countByAllDay * 100);
            preview.setDistingByNight(df.format(distingByNight));
            // 全天识别率
            String allDisting = preview.getAllDisting();
            allDisting = df.format(distingByday + distingByNight);
            preview.setAllDisting(allDisting);

        }*/

        /*
         * for (OverLoadRate ss : list) { total = total +( ss.getOverNum() == null ? 0 :
         * ss.getOverNum()); } DecimalFormat df=new DecimalFormat("#.00"); for
         * (OverLoadRate previewCalcByWeight : list) { double percent =
         * (double)previewCalcByWeight.getOverNum()/(double)total*100;
         * previewCalcByWeight.setPercent(df.format(percent)); }
         */
    }

    /* 超限率报表导出 *//*
    @SystemLog(module = "超限率报表", methods = "导出")
    @RequestMapping("/exportsss")
    public void exportByWeight(String startDate, String endDate, String station, HttpServletResponse response,
            HttpServletRequest request) {
        String[] userStationCodes=getUserStationCodes();
        List<Preview> byCarNum = service.getByCarNum(startDate, endDate, station,userStationCodes);
        this.carNum(byCarNum);
        // this.calcPercent2(list77);
        List<Map<String, Object>> data = new ArrayList<>();
        for (Preview preview : byCarNum) {
            Map<String, Object> item2 = new HashMap<String, Object>();
            item2.put("lane", preview.getLane());
            item2.put("distingByday", preview.getDistingByday());
            item2.put("distingByNight", preview.getDistingByNight());
            item2.put("allDisting", preview.getAllDisting());
            data.add(item2);
        }

        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sf.format(new Date());
        String filename = "按车牌识别率统计报表" + date + ".xls";
        List<SheetView> sheets = new ArrayList<SheetView>();
        sheets.add(new SheetView("按车牌识别率统计报表",
                new Table("按车牌识别率统计报表", 0, 0,
                        new Object[][] { { "lane", "车道", 256 * 10, ExportExcelUtils.STRING_CELL },
                                { "distingByday", "白天识别率%", 256 * 10, ExportExcelUtils.STRING_CELL },
                                { "distingByNight", "夜晚识别率%", 256 * 15, ExportExcelUtils.STRING_CELL },
                                { "allDisting", "全天识别率%", 256 * 15, ExportExcelUtils.STRING_CELL }, },
                        data)));
        ExportExcelUtils.export(response, request, filename, sheets);
    }
*/
    /**
     * 预检实时数据 首页显示 负责跳转
     *
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    @SystemLog(module = "预检实时数据", methods = "首页")
    @RequestMapping("getAll")
    public String getAll(Model model) {
        //List<Station> list2 = stationServcie.getAll();
        model.addAttribute("station", getUserStation());
        return "previewdata";
    }

    @SystemLog(module = "预检实时数据", methods = "查询")
    @RequestMapping("/showAll")
    @ResponseBody
    public ResultDo<List<Preview>> showAll(
            @RequestParam(name = "page", required = true, defaultValue = "1") Integer pageNum,
            @RequestParam(name = "limit", required = true, defaultValue = "2") Integer pageSize, String station,
            Integer time, String ss) {
        String[] userStationCodes=getUserStationCodes();
        PageHelper.startPage(pageNum, pageSize);
        List<Preview> list = service.getAll(station, time,userStationCodes);
        PageInfo<Preview> p = new PageInfo<Preview>(list);
        ResultDo<List<Preview>> res = new ResultDo<List<Preview>>();
        res.setCode(0);
        res.setMsg("");
        res.setData(p.getList());
        res.setCount(p.getTotal());
        return res;
    }

    /**
     * 预检明细 首页显示 负责跳转
     *
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    @SystemLog(module = "预检明细", methods = "首页")
    @RequestMapping("getToPre")
    public String getToPre(Model model) {
        //List<Station> list2 = stationServcie.getAll();
        model.addAttribute("station", getUserStation());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date cur = new Date();
        String curDate = sdf.format(cur);
        model.addAttribute("curDay", curDate + " 00:00:00");
        model.addAttribute("nextDay", curDate + " 23:59:59");
        model.addAttribute("directions", this.dictService.findByType(DictService.DICT_DIRECTION));

        return "preview";
    }

    /**
     * 预检明细
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    // @RequiresPermissions("preview:view")
    @SystemLog(module = "预检明细", methods = "查询")
    @RequestMapping("/showAllByPre")
    @ResponseBody
    public ResultDo<List<Preview>> showAllByPre(String startDate, String endDate, String station, Byte weightLane,
                                                                   String plateNum, Integer axleCnt, Double startWeight, Double endWeight, Integer overRate, Integer recognition,
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
        List<Preview> list3 = service.showAllPre(startDate, endDate, station, weightLane, plateNum, axleCnt,axleCnt_op, startWeight,
                endWeight, overRate, recognition, direction,previewId,overRate_op,overRate_num,userStationCodes);
        PageInfo<Preview> p = new PageInfo<Preview>(list3);
        
        ResultDo<List<Preview>> res = new ResultDo<List<Preview>>();
        res.setCode(0);
        res.setMsg("");
        res.setData(p.getList());
        res.setCount(p.getTotal());
        return res;

    }

    /* 预检明细 */
    @RequiresPermissions(PermissionConst.REVIEWED_EXPORT)
    @SystemLog(module = "预检明细", methods = "导出")
    @RequestMapping("/export2")
   /* public void exportByWeight(String startDate, String endDate, String station, Integer lane, String carNum,
            Integer axleCnt, Double startWeight, Double endWeight,
            Integer overRate,
            Integer overRate_op,
            Integer axleCnt_op,
            Integer overRate_num,
            Integer recognition,
            Integer direction,Integer previewId, HttpServletResponse response, HttpServletRequest request) {

        startWeight = startWeight == null ? null : startWeight * 1000;
        endWeight = endWeight == null ? null : endWeight * 1000;
        String[] userStationCodes=getUserStationCodes();
        List<Preview> showAllPre = service.showAllPre(startDate, endDate, station, lane, carNum, axleCnt,
                axleCnt_op, startWeight,
                endWeight, overRate, recognition, direction,previewId, overRate_op, overRate_num,userStationCodes);

        List<Map<String, Object>> data = new ArrayList<>();
        for (Preview preview : showAllPre) {
            Map<String, Object> item = new HashMap<String, Object>();
            // item.put("previewId", preview.getPreviewId());
            item.put("lane", preview.getLane());
            item.put("carNum", preview.getCarNum());
            item.put("axleCnt", preview.getAxleCnt());

            item.put("dateTime", preview.getDateTime());
            item.put("speed", preview.getSpeed());
            item.put("sumWeight", preview.getSumWeight());
            item.put("limitWeight", preview.getLimitWeight());
            item.put("overRage", preview.getOverRage());
            item.put("overLoadRate", preview.getOverLoadRate());
            data.add(item);
        }

        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sf.format(new Date());
        String filename = "按预检明细统计报表" + date + ".xls";
        List<SheetView> sheets = new ArrayList<SheetView>();
        sheets.add(new SheetView("按预检明细统计报表", new Table("按预检明细统计报表", 0, 0, new Object[][] {
                // {"previewId","预检序号",256*10, ExportExcelUtils.STRING_CELL},
                { "lane", "车道", 256 * 5, ExportExcelUtils.STRING_CELL },
                { "carNum", "车牌号", 256 * 15, ExportExcelUtils.STRING_CELL },
                { "axleCnt", "轴数", 256 * 7, ExportExcelUtils.STRING_CELL },
                { "dateTime", "时间", 256 * 20, ExportExcelUtils.STRING_CELL },
                { "speed", "车速(km/h)", 256 * 10, ExportExcelUtils.STRING_CELL },
                { "sumWeight", "总重(kg)", 256 * 10, ExportExcelUtils.STRING_CELL },
                { "limitWeight", "限重(kg)", 256 * 10, ExportExcelUtils.STRING_CELL },
                { "overRage", "超限(kg)", 256 * 10, ExportExcelUtils.STRING_CELL },
                { "overLoadRate", "超限率%", 256 * 10, ExportExcelUtils.STRING_CELL }, }, data)));
        ExportExcelUtils.export(response, request, filename, sheets);
    }*/

    @RequestMapping("/testUpload")
    @ResponseBody
    public Map testUpload(
            @RequestParam(value = "files", required = false) CommonsMultipartFile files[],
            HttpSession session) throws Exception {
        System.out.println(files.length);
        HashMap map=new HashMap<>();
    	int tag=1;
        for(int i = 0;i<files.length;i++){
        	String picPath=systemSetService.getProperty("picPath");
        	SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd/HH");//设置日期格式
            //System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        	picPath=picPath+"/"+df.format(new Date());
        	File file =new File(picPath);
        	//如果文件夹不存在则创建
        	if  (!file .exists()  && !file .isDirectory()){
        	    System.out.println("//不存在");
        	    System.out.println(file.mkdirs());
        	}
            if (!files[i].isEmpty()) {
                   //String path = session.getServletContext().getRealPath("/upload/");  //获取本地存储路径
                   String fileName = files[i].getOriginalFilename();
                   String fileType = fileName.substring(fileName.lastIndexOf(".")); //获取后缀名
                   //String newName=new Date().getTime() + fileType;
           		   SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");//设置日期格式
                   String newName=df2.format(new Date())+"_"+tag+ fileType;
                   File file2 = new File(picPath,newName); //新建一个文件
                   files[i].transferTo(file2);
                   map.put("p"+tag, "/"+df.format(new Date())+"/"+newName);
                   tag++;
            }
        }
        return map;
    }

    /*
     * 采用file.Transto 来保存上传的文件
     */
    @RequestMapping("fileUpload")
    @ResponseBody
    public Map  fileUpload2(@RequestParam("file") CommonsMultipartFile file,@RequestParam String tag) throws IOException {
    	HashMap map=new HashMap<>();
     	SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd/HH");//设置日期格式
     	String stime=df.format(new Date());
        //File newFile=new File(path);
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf(".")); //获取后缀名
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");//设置日期格式
        String newName=df2.format(new Date())+"_"+tag+fileType;
        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        File file2 = new File(getFile(stime),newName); //新建一个文件
        file.transferTo(file2);
        map.put("pt","/"+stime+"/"+newName);
        return map;
    }
    //获取文件存储路径
    public String getFile(String stime){
    	File file=null;
    	String picPath=systemSetService.getProperty("picPath");//获取文件存放配置目录
     	SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd/HH");//设置日期格式
     	//String stime=df.format(new Date());
     	picPath=picPath+"/"+stime;
        File filem =new File(picPath);
    	//如果文件夹不存在则创建
    	if  (!filem .exists()  && !filem .isDirectory()){
    	    System.out.println("//不存在");
    	    System.out.println(filem.mkdirs());
    	}
    	return picPath;
    }

   /* public PreviewRecord getPreviewRecord(Preview preview){
    	PreviewRecord previewRecord=new PreviewRecord();
    	previewRecord.setPreviewId(preview.getPreviewId());
    	Subject subject = SecurityUtils.getSubject();//获取登录用户id
        if(subject != null) {
            String empName = (String)subject.getPrincipal();
            Emp emp=empService.getById(empName);
            previewRecord.setEmpId(emp.getId());
        }
        previewRecord.setFrontPic(preview.getFrontPic());
        previewRecord.setBackPic(preview.getBackPic());
        previewRecord.setSidePic(preview.getSidePic());
        previewRecord.setUpPic(preview.getUpPic());
        previewRecord.setLane(preview.getLane());
        previewRecord.setSpeed(preview.getSpeed());
        previewRecord.setVehicleType(preview.getVehicleType());
        previewRecord.setAxleCnt(preview.getAxleCnt());
        previewRecord.setAxleDis(preview.getAxleDis());
        previewRecord.setStation(preview.getStation());
        previewRecord.setDirection(preview.getDirection());
        previewRecord.setCarNum(preview.getCarNum());
        previewRecord.setCheckNo(preview.getCheckNo());
        previewRecord.setOverLoadId(preview.getOverLoadId());
        previewRecord.setOverLoadRate(preview.getOverLoadRate());
        previewRecord.setOverRage(preview.getOverRage());
        previewRecord.setOverRate(preview.getOverRate());
        previewRecord.setDateTime(preview.getDateTime());
        previewRecord.setRelCode(preview.getRelCode());
    	return previewRecord;
    }*/

    /*
     * 预检批量删除
     */
    @RequestMapping("/delBatches")
    @ResponseBody
    public Map  delBatches(@RequestParam("previewIds") String[] previewIds) throws IOException {
        HashMap map=new HashMap<>();
        System.out.println(previewIds);
        for(int i=0;i<previewIds.length;i++){
            service.delPreview(Integer.parseInt(previewIds[i]));
        }
        map.put("success","操作完成");
        return map;
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
        List<Station> list2 = stationServcie.getAll();//查询全部站点
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

    //修改抓拍图片信息
    public void updateFiledix(String relCode,String frontPic,String backPic,String sidePic,String upPic)throws Exception{

        if(StringUtils.isNotEmpty(frontPic)){
            fileidxDao.updateFileidx(relCode,"3",frontPic);//修改前抓拍图片
        }
        if(StringUtils.isNotEmpty(backPic)){
            fileidxDao.updateFileidx(relCode,"6",backPic);//修改后抓拍图片
        }
        if(StringUtils.isNotEmpty(sidePic)){
            fileidxDao.updateFileidx(relCode,"4",sidePic);//修改侧抓拍图片
        }
        if(StringUtils.isNotEmpty(upPic)){
            fileidxDao.updateFileidx(relCode,"5",upPic);//修改顶抓拍图片
        }
    };
}