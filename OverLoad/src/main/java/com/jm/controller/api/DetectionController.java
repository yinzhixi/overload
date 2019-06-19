//package com.jm.controller.api;
//
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.jm.bean.*;
//import com.jm.dao.UserStationDao;
//import com.jm.service.*;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.subject.Subject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.alibaba.druid.util.StringUtils;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.jm.dao.PreviewDao;
//import com.jm.dao.PreviewRecordDao;
//import com.jm.web.log.SystemLog;
//
///**
// * @Title: DetectionController
// * @Description:
// * @Company:
// * @author: yinzhixi
// * @created: 2018年12月26日 上午9:53:38
// */
//@RestController
//@RequestMapping("/api/detection")
//public class DetectionController {
//
//	@Autowired
//	private PreviewRecordService previewRecordService;
//	@Autowired
//	private PreviewRecordDao previewRecordDao;
//	@Autowired
//	private PreviewDao previewDao;
//	@Autowired
//	private PreviewCalcService previewCalcService;
//	@Autowired
//	private OverLoadRateService service;
//	@Autowired
//    private PreviewService previewService;
//	@Autowired
//    private FlowViewService flowViewService;
//	@Autowired
//    private EmpService empService;
//	@Autowired
//    private UserStationDao userStationDao;
//
//	/**
//     *
//     * @discription:
//     * @author: yinzhixi
//     * @created: 2018年12月27日 下午4:50:30
//     * @param pageNum
//     * @param pageSize
//     * @param station
//     * @param overLoadRate
//     * @param startTime
//     * @param endTime
//     * @return
//	 */
//	@RequestMapping("/getFinallyData")
//    public ResultDo<List<PreviewRecord>> getFinallyData(
//            @RequestParam(name = "pageNum", required = true, defaultValue = "1") Integer pageNum, Integer pageSize,
//            String station, Integer overLoadRate,String startTime,String endTime) {
//        PageHelper.startPage(pageNum, pageSize);
//        Map map=new HashMap<>();
//        map.put("pageSize", pageSize);
//        map.put("station", station);
//        map.put("overLoadRate", overLoadRate);
//        map.put("startTime", startTime);
//        map.put("endTime", endTime);
//        List<PreviewRecord> list = previewRecordDao.getFinallyData(map);
//        PageInfo<PreviewRecord> p = new PageInfo<PreviewRecord>(list);
//        ResultDo<List<PreviewRecord>> res = new ResultDo<List<PreviewRecord>>();
//        res.setCode(0);
//        res.setMsg("");
//        res.setData(p.getList());
//        res.setCount(p.getTotal());
//        return res;
//
//    }
//
//	/**
//     *
//     * @discription:预检明细
//     * @author: yinzhixi
//     * @created: 2018年12月27日 下午3:00:54
//     * @param pageNum
//     * @param pageSize
//     * @param station
//     * @param overLoadRate
//     * @param startTime
//     * @param endTime
//     * @return
//     */
//	@RequestMapping("/getPreviewData")
//    public ResultDo<List<Preview>> getPreviewData(
//            @RequestParam(name = "pageNum", required = true, defaultValue = "1") Integer pageNum, Integer pageSize,
//            String station, Integer overLoadRate,String startTime,String endTime) {
//        PageHelper.startPage(pageNum, pageSize);
//        Map map=new HashMap<>();
//        map.put("pageSize", pageSize);
//        map.put("station", station);
//        map.put("overLoadRate", overLoadRate);
//        map.put("startTime", startTime);
//        map.put("endTime", endTime);
//        List<Preview> list = previewDao.getPreviewData(map);
//        PageInfo<Preview> p = new PageInfo<Preview>(list);
//        ResultDo<List<Preview>> res = new ResultDo<List<Preview>>();
//        res.setCode(0);
//        res.setMsg("");
//        res.setData(p.getList());
//        res.setCount(p.getTotal());
//        return res;
//
//    }
//
//	/**
//	 *
//     * @discription: 按吨位分类表
//     * @author: yinzhixi
//     * @created: 2018年12月28日 上午10:14:27
//     * @param pageNum
//     * @param pageSize
//     * @param station
//     * @param startDate
//     * @param endDate
//     * @return
//	 */
//	@RequestMapping("/getPreviewCalcByWeight")
//	public ResultDo<List<PreviewCalcByWeight>> getPreviewCalcByWeight(
//			@RequestParam(name = "pageNum", required = true, defaultValue = "1")Integer pageNum,
//			@RequestParam(name="pageSize", required = true, defaultValue = "2")Integer pageSize,
//			String station,String startDate,String endDate){
//
//		if(pageNum==null){
//			pageNum=1;
//		}
//
//		if(pageSize==null){
//			pageSize=10;
//		}
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date cur = new Date();
//        String curDate = sdf.format(cur);
//        if (StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)) {
//            startDate = curDate;
//            endDate = curDate;
//        }
//        PageHelper.startPage(pageNum, pageSize);
//        List<PreviewCalcByWeight> list = previewCalcService.previewCalcByWeight(startDate, endDate, station);
//        PageInfo<PreviewCalcByWeight> p = new PageInfo<PreviewCalcByWeight>(list);
//        ResultDo<List<PreviewCalcByWeight>> res = new ResultDo<List<PreviewCalcByWeight>>();
//        res.setCode(0);
//        res.setMsg("");
//        res.setData(p.getList());
//        res.setCount(p.getTotal());
//        return res;
//	}
//
//
//	/**
//	 *
//     * @discription: 超限率分类报表
//     * @author: yinzhixi
//     * @created: 2018年12月28日 下午2:08:15
//     * @param startDate
//     * @param endDate
//     * @param station
//     * @param direction
//     * @param pageNum
//     * @param pageSize
//     * @return
//	 */
//	@RequestMapping("getAllByOverRate")
//    public ResultDo<List<OverLoadRate>> getAllByOverRate(String startDate,String endDate,String station,Integer direction,
//    		@RequestParam(name="pageNum",required = true, defaultValue = "1") Integer pageNum,
//			 @RequestParam(name="pageSize", required = true, defaultValue = "2") Integer pageSize){
//	    PageHelper.startPage(pageNum, pageSize);
//	    List<OverLoadRate> list = service.getByOverLoad(startDate, endDate, station,direction);
//	    PageInfo<OverLoadRate> p = new PageInfo<OverLoadRate>(list);
//        //this.calcPercent(list);
//        ResultDo<List<OverLoadRate>> res = new ResultDo<List<OverLoadRate>>();
//        res.setCode(0);
//        res.setMsg("");
//        res.setData(p.getList());
//        res.setCount(p.getTotal());
//        return res;
//    }
//
//	private void calcPercent(List<OverLoadRate> list){
//        int total = 0;
//        for (OverLoadRate ss : list) {
//            total = total +( ss.getOverNum() == null ? 0 : ss.getOverNum());
//
//
//        }
//        DecimalFormat df=new DecimalFormat("0.00");
//        for (OverLoadRate previewCalcByWeight : list) {
//            double percent = (double)previewCalcByWeight.getOverNum()/(double)total*100;
//            previewCalcByWeight.setPercent(df.format(percent));
//        }
//        OverLoadRate ss = new OverLoadRate();
//        ss.setOverLoadId(6);
//        ss.setOverLoadRateRegion("合计");
//        ss.setOverNum(total);
//        ss.setPercent("100.00");
//        list.add(ss);
//    }
//
//
//	/**
//     *
//     * @discription: 车牌识别率首页显示
//     * @author: yinzhixi
//     * @created: 2018年12月28日 下午2:23:00
//     * @param startDate
//     * @param endDate
//     * @param station
//     * @param pageNum
//     * @param pageSize
//     * @return
//     */
//    @RequestMapping("getByCarNum")
//    public ResultDo<List<Preview>> getByCarNum(String startDate, String endDate, String station,
//            @RequestParam(name = "pageNum", required = true, defaultValue = "1") Integer pageNum,
//            @RequestParam(name = "pageSize", required = true, defaultValue = "2") Integer pageSize) {
//
////    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////        Date cur = new Date();
////        String curDate = sdf.format(cur);
////        String curDate2 = curDate.substring(0, 10) + " 00:00:00";
////        String curDate1 = curDate.substring(0, 10) + " 23:59:59";
////        if(StringUtils.isEmpty(startDate)){
////        	startDate=curDate2;
////        }
////        if(StringUtils.isEmpty(endDate)){
////        	endDate=curDate1;
////        }
//        PageHelper.startPage(pageNum, pageSize);
//        List<Preview> list = previewService.getByCarNum(startDate, endDate, station);
//        PageInfo<Preview> p = new PageInfo<Preview>(list);
//        this.carNum(list);
//        ResultDo<List<Preview>> res = new ResultDo<List<Preview>>();
//        res.setCode(0);
//        res.setMsg("");
//        res.setData(p.getList());
//        res.setCount(p.getTotal());
//        return res;
//    }
//
//    /**
//     *
//     * @discription:高速预检速度报表
//     * @author: yinzhixi
//     * @created: 2019年1月2日 上午9:24:15
//     * @param startDate
//     * @param endDate
//     * @param station
//     * @param pageNum
//     * @param pageSize
//     * @return
//     */
//    @RequestMapping("/query")
//    public ResultDo<List<PreviewCalc>> calcQuery(String startDate, String endDate, String station,
//            @RequestParam(name = "pageNum", required = true, defaultValue = "1") Integer pageNum,
//            @RequestParam(name = "pageSize", required = true, defaultValue = "2") Integer pageSize) {
//        PageHelper.startPage(pageNum, pageSize);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date cur = new Date();
//        String curDate = sdf.format(cur);
//        if (StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)) {
//            startDate = curDate;
//            endDate = curDate;
//        }
//        List<PreviewCalc> list = previewCalcService.previewCalc(startDate, endDate, station);
//        PageInfo<PreviewCalc> p = new PageInfo<PreviewCalc>(list);
//        ResultDo<List<PreviewCalc>> res = new ResultDo<List<PreviewCalc>>();
//        res.setCode(0);
//        res.setMsg("");
//        res.setData(p.getList());
//        res.setCount(p.getTotal());
//        return res;
//    }
//
//
//    /**
//     *
//     * @discription: 预检实时数据
//     * @author: yinzhixi
//     * @created: 2019年1月2日 上午9:54:37
//     * @param pageNum
//     * @param pageSize
//     * @param station
//     * @param time
//     * @param ss
//     * @return
//     */
//    @RequestMapping("/getAll")
//    public ResultDo<List<Preview>> getAll(
//            @RequestParam(name = "pageNum", required = true, defaultValue = "1") Integer pageNum,
//            @RequestParam(name = "pageSize", required = true, defaultValue = "2") Integer pageSize, String station,
//            Integer time, String tag) {
//        PageHelper.startPage(pageNum, pageSize);
//        List<Preview> list = previewService.getAll(station, time);
//        PageInfo<Preview> p = new PageInfo<Preview>(list);
//        ResultDo<List<Preview>> res = new ResultDo<List<Preview>>();
//        res.setCode(0);
//        res.setMsg("");
//        res.setData(p.getList());
//        res.setCount(p.getTotal());
//        return res;
//    }
//
//
//
//
//    /**
//     *
//     * @discription: 按时间查询视频
//     * @author: yinzhixi
//     * @created: 2019年1月2日 下午2:38:46
//     * @param startDate
//     * @param endDate
//     * @param station
//     * @param direction
//     * @param pageNum
//     * @param pageSize
//     * @return
//     */
//    @RequestMapping("/byTimeGetVideo")
//    public ResultDo<Map<String, Object>> queryByTime(String startDate, String endDate, String station,
//    		@RequestParam(name = "pageNum", required = true, defaultValue = "1") Integer pageNum,
//            @RequestParam(name = "pageSize", required = true, defaultValue = "2") Integer pageSize) {
//
//        PageHelper.startPage(pageNum, pageSize);
//        List<FlowView> list = flowViewService.apiQueryByTime(startDate, endDate, station);
//        PageInfo<FlowView> p = new PageInfo<FlowView>(list);
//        ResultDo<Map<String, Object>> res = new ResultDo<Map<String, Object>>();
//        res.setCode(0);
//        res.setMsg("");
//        Map<String, Object> data = new HashMap<>();
//        Integer maxId = 0;
//        if (list != null && list.size() > 0) {
//            maxId = list.get(0).getPreviewId();
//        }
//        data.put("maxId", maxId);
//        data.put("list", p.getList());
//        res.setData(data);
//        res.setCount(p.getTotal());
//        return res;
//
//    }
//
//
//    /**
//     * @discription: 按车牌查询
//     * @author: yinzhixi
//     * @created: 2019年1月2日 下午2:41:10
//     * @param carNum
//     * @param station
//     * @param pageNum
//     * @param pageSize
//     * @return
//     */
//    @RequestMapping("/byCarNumGetVideo")
//    public ResultDo<Map<String, Object>> queryByCarNum(String carNum, String station,
//            @RequestParam(name = "pageNum", required = true, defaultValue = "1") Integer pageNum,
//            @RequestParam(name = "pageSize", required = true, defaultValue = "2") Integer pageSize) {
//        int[] userStationIds=getUserStationIds();
//        PageHelper.startPage(pageNum, pageSize);
//        List<FlowView> list = flowViewService.queryByCarNum(carNum, station,userStationIds);
//        PageInfo<FlowView> p = new PageInfo<FlowView>(list);
//        ResultDo<Map<String, Object>> res = new ResultDo<Map<String, Object>>();
//        res.setCode(0);
//        res.setMsg("");
//        Map<String, Object> data = new HashMap<>();
//        Integer maxId = 0;
//        if (list != null && list.size() > 0) {
//            maxId = list.get(0).getPreviewId();
//        }
//        data.put("maxId", maxId);
//        data.put("list", p.getList());
//        res.setData(data);
//        res.setCount(p.getTotal());
//        return res;
//    }
//    private void carNum(List<Preview> list) {
//        // int total = 0;
//        for (Preview preview : list) {
//            // 总数量
//            Integer countByAllDay = preview.getCountByAllDay();
//            DecimalFormat df = new DecimalFormat("0.00");
//            // 白天识别数量
//            Integer countByDay = preview.getCountByDay();
//            double distingByday = ((double) countByDay / (double) countByAllDay * 100);
//            preview.setDistingByday(df.format(distingByday));
//            // 夜晚识数量
//            Integer countByNight = preview.getCountByNight();
//            double distingByNight = ((double) countByNight / (double) countByAllDay * 100);
//            preview.setDistingByNight(df.format(distingByNight));
//            // 全天识别率
//            String allDisting = preview.getAllDisting();
//            allDisting = df.format(distingByday + distingByNight);
//            preview.setAllDisting(allDisting);
//
//        }
//
//    }
//    //返回登录人员的站点权限数组
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
//
//}
