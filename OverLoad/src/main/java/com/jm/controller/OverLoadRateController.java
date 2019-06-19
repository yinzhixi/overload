package com.jm.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jm.bean.*;
import com.jm.dao.UserStationDao;
import com.jm.service.EmpService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jm.service.DictService;
import com.jm.service.OverLoadRateService;
import com.jm.service.StationService;
import com.jm.utils.export.ExportExcelUtils;
import com.jm.utils.export.SheetView;
import com.jm.utils.export.Table;
import com.jm.web.log.SystemLog;

@Controller
@RequestMapping("/overRate")
public class OverLoadRateController {
	@Autowired
	private OverLoadRateService service;
	
	@Autowired
	private StationService stationServcie;
	
    @Autowired
    private DictService dictService;
    @Autowired
	private EmpService empService;
    @Autowired
	private UserStationDao userStationDao;

    @SystemLog(module="超限率分类",methods="主页面")
	  @RequestMapping("getToOverRate")
	    public String getToOverRate(Model model){
	        model.addAttribute("stations", getUserStation());
	       /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        Date cur = new Date();
	        String curDate = sdf.format(cur);
	        model.addAttribute("curDay", curDate);
	        model.addAttribute("nextDay", curDate);s*/	              
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date cur = new Date();
            String curDate = sdf.format(cur);
            String curDate2 = curDate.substring(0,10)+" 00:00:00";
            String curDate1 = curDate.substring(0,10)+" 23:59:59";
            model.addAttribute("curDay", curDate2);
            model.addAttribute("nextDay", curDate1);
            model.addAttribute("directions", this.dictService.findByType(DictService.DICT_DIRECTION));
	        return "overloadrate";
	    }

        @SystemLog(module="超限率分类",methods="查询")
	    @RequestMapping("getAllByOverRate")
	    @ResponseBody
	    public ResultDo<List<OverLoadRate>> getAllByOverRate(String startDate,String endDate,String station,Integer direction,
	    		@RequestParam(name="page",required = true, defaultValue = "1") Integer pageNum,
				 @RequestParam(name="limit", required = true, defaultValue = "2") Integer pageSize){
    	int[]userStationIds=getUserStationIds();
		   PageHelper.startPage(pageNum, pageSize);
	    List<OverLoadRate> list = service.getByOverLoad(startDate, endDate, station,direction,userStationIds);
	    PageInfo<OverLoadRate> p = new PageInfo<OverLoadRate>(list);
	        this.calcPercent(list);
	        ResultDo<List<OverLoadRate>> res = new ResultDo<List<OverLoadRate>>();
	        res.setCode(0);
	        res.setMsg("");
	        res.setData(p.getList());
	        res.setCount(p.getTotal());
	        return res;
	    }
	    
	    private void calcPercent(List<OverLoadRate> list){
	        int total = 0;
	        for (OverLoadRate ss : list) {
	            total = total +( ss.getOverNum() == null ? 0 : ss.getOverNum());
	            
	            
	        }
	        DecimalFormat df=new DecimalFormat("0.00"); 
	        for (OverLoadRate previewCalcByWeight : list) {
	            double percent = (double)previewCalcByWeight.getOverNum()/(double)total*100;
	            previewCalcByWeight.setPercent(df.format(percent));
	        }
	        OverLoadRate ss = new OverLoadRate();
	        ss.setOverLoadId(6);
	        ss.setOverLoadRateRegion("合计");
	        ss.setOverNum(total);
	        ss.setPercent("100.00");
	        list.add(ss);
	    }
	/*  超限率报表导出*/
	    @SystemLog(module="超限率分类",methods="导出")
	    @RequestMapping("/exports")
	    public void exportByWeight(String startDate,String endDate,String station,Integer direction,HttpServletResponse response,HttpServletRequest request){
	    	 int[]userStationIds=getUserStationIds();
	    	  List<OverLoadRate> list = service.getByOverLoad(startDate, endDate, station,direction,userStationIds);
	    	
	        this.calcPercent(list);
	        List<Map<String,Object>> data = new ArrayList<>();
	        for (OverLoadRate overLoadRate : list) {
		    	 Map<String,Object> item = new HashMap<String,Object>();
		         item.put("overLoadId", overLoadRate.getOverLoadId());
		         item.put("overLoadRateRegion", overLoadRate.getOverLoadRateRegion());
		         item.put("overNum", overLoadRate.getOverNum());
		         item.put("percent", overLoadRate.getPercent());
		         data.add(item);
	        }

	        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
	        String date = sf.format(new Date());
	        String filename = "按超限率统计报表"+date+".xls";
	        List<SheetView> sheets = new ArrayList<SheetView>();
	        sheets.add(new SheetView("按超限率统计报表",new Table("按超限率统计报表", 0, 0, new Object[][]{
	            {"overLoadId","车道",256*10, ExportExcelUtils.STRING_CELL},
	            {"overLoadRateRegion","吨位",256*10, ExportExcelUtils.STRING_CELL},
	            {"overNum","数量（辆）",256*15, ExportExcelUtils.STRING_CELL},
	            {"percent","比例%",256*15, ExportExcelUtils.STRING_CELL},
	        }, data)));
	        ExportExcelUtils.export(response, request, filename, sheets);
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
