//package com.jm.controller;
//
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.jm.bean.*;
//import com.jm.dao.PreviewDao;
//import com.jm.dao.ReviewedPreviewDao;
//import com.jm.dao.UserStationDao;
//import com.jm.data.base.PermissionConst;
//import com.jm.service.*;
//import com.jm.utils.export.ExportExcelUtils;
//import com.jm.utils.export.SheetView;
//import com.jm.utils.export.Table;
//import com.jm.web.log.SystemLog;
//
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.apache.shiro.subject.Subject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//@Controller
//@RequestMapping("/after")
//public class ReviewedPreviewController {
//    @Autowired
//    private ReviewedPreviewService service;
//    @Autowired
//    private StationService stationServcie;
//    @Autowired
//    private ReviewedPreviewDao dao;
//    @Autowired
//    private PreviewDao previewDao;
//    @Autowired
//    private ReviewedPreviewDao reDao;
//    @Autowired
//    private DictService dictService;
//    @Autowired
//    private PreviewService previewService;
//    @Autowired
//    private EmpService empService;
//    @Autowired
//    private UserStationDao userStationDao;
//
//    @SystemLog(module = "精检管理", methods = "删除")
//    @RequiresPermissions(PermissionConst.REVIEWED_DELETE)
//    @RequestMapping("delReviewed")
//    @ResponseBody
//    public Map<String, Object> delReviewed(Integer previewId) {
//        Map<String, Object> map = new HashMap<>();
//        ReviewedPreview byReviewedId = dao.getByReviewedId(previewId);
//        reDao.updateReviewed2(byReviewedId);
//        int delReviewed = service.delReviewed(previewId);
//        String hint = "";
//        boolean falt = false;
//        if (delReviewed > 0) {
//            hint = "删除成功";
//            falt = true;
//        } else {
//            hint = "删除失败";
//            falt = false;
//        }
//        map.put("hint", hint);
//        map.put("falt", falt);
//        return map;
//    }
//
//    @SystemLog(module = "精检管理", methods = "审核")
//    @RequestMapping("addReviewedPreview")
//    @ResponseBody
//    public Map<String, Object> addReviewedPreview(ReviewedPreview reviewed) {
//        Preview preview=previewService.getPreviewById(reviewed.getPreviewId());//查询预检数据
//        Map<String, Object> map = new HashMap<>();
//        String hint = "审核成功";
//        boolean flat = true;
//        Integer previewId = reviewed.getPreviewId();
//        ReviewedPreview byReviewedId = reDao.getByReviewedId(previewId);
//        if (byReviewedId != null) {
//            map.put("hint", "该数据已审核，请审核其它数据！！");
//            map.put("flat", true);
//            map.put("enforcement",reviewed.getEnforcement());
//            map.put("enforcementTwo",reviewed.getEnforcementTwo());
//            return map;
//        }
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = new Date();
//        reviewed.setVenifyTime(format.format(date));
//        reDao.updatePreview2(reviewed);
//        //精检数据的图片添加
//        reviewed.setFrontPic(preview.getFrontPic());
//        reviewed.setBackPic(preview.getBackPic());
//        reviewed.setStationId(Integer.parseInt(preview.getStationId()));
//        int addReviewed = service.addReviewed(reviewed);
//        dao.updateReviewed(reviewed);
//        if (addReviewed > 0) {
//            hint = "审核成功";
//            flat = true;
//        } else {
//            hint = "审核失败";
//            flat = false;
//        }
//        map.put("hint", hint);
//        map.put("flat", flat);
//        map.put("enforcement",reviewed.getEnforcement());
//        map.put("enforcementTwo",reviewed.getEnforcementTwo());
//        return map;
//    }
//
//    /**
//     * 精检
//     * @param pageNum
//     * @param pageSize
//     * @return
//     */
//    @SystemLog(module = "精检管理", methods = "查询")
//    @RequestMapping("/quaryAll")
//    @ResponseBody
//    public ResultDo<List<ReviewedPreview>> quaryAll(
//            String startDate,
//            String endDate,
//            String station,
//            Integer lane,
//            String carNum,
//            Integer axleCnt,
//            Double startWeight,
//            Double endWeight,
//            Integer overRate,
//            Integer recognition,
//            Integer direction,
//            Integer overRate_num,
//            Integer overRate_op,
//            Integer axleCnt_op,
//            @RequestParam(name = "page", required = true, defaultValue = "1") Integer pageNum,
//            @RequestParam(name = "limit", required = true, defaultValue = "2") Integer pageSize) {
//        String[] userStationCodes=getUserStationCodes();
//        startWeight = startWeight == null ? null : startWeight * 1000;
//        endWeight = endWeight == null ? null : endWeight * 1000;
//        PageHelper.startPage(pageNum, pageSize);
//        List<ReviewedPreview> queryAll = service.queryAll(
//                startDate,
//                endDate,
//                station,
//                lane,
//                carNum,
//                axleCnt,
//                axleCnt_op,
//                startWeight,
//                endWeight,
//                overRate,
//                overRate_num,
//                overRate_op,
//                recognition,
//                direction,userStationCodes);
//
//        PageInfo<ReviewedPreview> p = new PageInfo<ReviewedPreview>(queryAll);
//        ResultDo<List<ReviewedPreview>> res = new ResultDo<List<ReviewedPreview>>();
//        res.setCode(0);
//        res.setMsg("");
//        res.setData(p.getList());
//        res.setCount(p.getTotal());
//        return res;
//    }
//
//    @SystemLog(module = "精检管理", methods = "主页")
//    @RequestMapping("getToPreviewPre")
//    public String getToPreviewPre(Model model) {
//        //List<Station> list2 = stationServcie.getAll();
//        model.addAttribute("station", getUserStation());
//        model.addAttribute("directions", this.dictService.findByType(DictService.DICT_DIRECTION));
//        return "/previewPre/previewPre";
//    }
//
//    @RequiresPermissions(PermissionConst.REVIEWED_EXPORT)
//    @SystemLog(module = "精检管理", methods = "导出")
//    @RequestMapping("/export2")
//    public void exportByWeight(String startDate, String endDate, String station, Integer lane, String carNum,
//            Integer axleCnt, Double startWeight, Double endWeight, Integer overRate, Integer recognition,
//            Integer direction,
//            Integer overRate_num,
//            Integer overRate_op,
//            Integer axleCnt_op,
//            HttpServletResponse response, HttpServletRequest request) {
//        String[] userStationCodes=getUserStationCodes();
//        startWeight = startWeight == null ? null : startWeight * 1000;
//        endWeight = endWeight == null ? null : endWeight * 1000;
//
//        List<ReviewedPreview> showAllPre = service.queryAll(
//                startDate,
//                endDate,
//                station,
//                lane,
//                carNum,
//                axleCnt,
//                axleCnt_op,
//                startWeight,
//                endWeight,
//                overRate,
//                overRate_num,
//                overRate_op,
//                recognition,
//                direction,
//                userStationCodes);
//
//        List<Map<String, Object>> data = new ArrayList<>();
//        for (ReviewedPreview preview : showAllPre) {
//            Map<String, Object> item = new HashMap<String, Object>();
//            item.put("previewId", preview.getPreviewId());
//            item.put("lane", preview.getLane());
//            item.put("carNum", preview.getCarNum());
//            item.put("axleCnt", preview.getAxleCnt());
//            item.put("dateTime", preview.getDateTime());
//            item.put("speed", preview.getSpeed());
//            item.put("sumWeight", preview.getSumWeight());
//            item.put("limitWeight", preview.getLimitWeight());
//            item.put("overRage", preview.getOverRage());
//            data.add(item);
//        }
//
//        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
//        String date = sf.format(new Date());
//        String filename = "精检明细统计报表" + date + ".xls";
//        List<SheetView> sheets = new ArrayList<SheetView>();
//        sheets.add(new SheetView("按预检明细统计报表",
//                new Table("按预检明细统计报表", 0, 0,
//                        new Object[][] { { "previewId", "预检序号", 256 * 10, ExportExcelUtils.STRING_CELL },
//                                { "lane", "车道", 256 * 10, ExportExcelUtils.STRING_CELL },
//                                { "carNum", "车牌号", 256 * 15, ExportExcelUtils.STRING_CELL },
//                                { "axleCnt", "轴数", 256 * 15, ExportExcelUtils.STRING_CELL },
//                                { "dateTime", "时间", 256 * 10, ExportExcelUtils.STRING_CELL },
//                                { "speed", "车速", 256 * 10, ExportExcelUtils.STRING_CELL },
//                                { "sumWeight", "总重T", 256 * 15, ExportExcelUtils.STRING_CELL },
//                                { "limitWeight", "限重T", 256 * 15, ExportExcelUtils.STRING_CELL },
//                                { "overRage", "超限T", 256 * 15, ExportExcelUtils.STRING_CELL }, },
//                        data)));
//        ExportExcelUtils.export(response, request, filename, sheets);
//    }
//    //返回全部权限站点
//    public List<Station> getUserStation(){
//        Subject subject = SecurityUtils.getSubject();//获取登录用户id
//        Emp emp=new Emp();
//        if(subject != null) {
//            String empName = (String)subject.getPrincipal();
//            emp=empService.getById(empName);
//        }
//        List<UserStation> userStationslist=userStationDao.getByEmpId(emp.getId());//获取登录员工所有的站点权限
//        List<Integer> listStation=new ArrayList();
//        for(int j=0;j<userStationslist.size();j++){
//            listStation.add(userStationslist.get(j).getStationId());
//        }
//        List<Station> list2 = stationServcie.getAll();//查询全部站点
//        List<Station> relist =new ArrayList<>();//返回用户的站点权限
//        for (int i=0;i<list2.size();i++){
//            Station station=list2.get(i);
//            if (listStation.contains(station.getId())){
//                relist.add(station);
//            }
//        }
//        return relist;
//    }
//
//    //返回登录人员的站点id权限数组
//    public int[] getUserStationIds(){
//        Subject subject = SecurityUtils.getSubject();//获取登录用户id
//        Emp emp=new Emp();
//        if(subject != null) {
//            String empName = (String)subject.getPrincipal();
//            emp=empService.getById(empName);
//            //previewRecord.setEmpId(emp.getId());
//        }
//        List<UserStation> userStationslist=userStationDao.getByEmpId(emp.getId());//获取登录员工所有的站点权限
//
//        int[] stationIds=new int[userStationslist.size()];
//        for(int j=0;j<userStationslist.size();j++){
//            stationIds[j]=userStationslist.get(j).getStationId();
//        }
//        return stationIds;
//    }
//
//    //返回登录人员的站点mark权限数组
//    public String[] getUserStationCodes(){
//        Subject subject = SecurityUtils.getSubject();//获取登录用户id
//        Emp emp=new Emp();
//        if(subject != null) {
//            String empName = (String)subject.getPrincipal();
//            emp=empService.getById(empName);
//            //previewRecord.setEmpId(emp.getId());
//        }
//        List<UserStation> userStationslist=userStationDao.getByEmpId(emp.getId());//获取登录员工所有的站点权限
//
//        String[] stationCodes=new String[userStationslist.size()];
//        for(int j=0;j<userStationslist.size();j++){
//            stationCodes[j]=userStationslist.get(j).getStationCode();
//        }
//        return stationCodes;
//    }
//
//}
