package com.jm.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jm.bean.NodeType;
import com.jm.bean.ResultDo;
import com.jm.bean.Station;
import com.jm.dao.StationDao;
import com.jm.service.StationService;
import com.jm.web.log.SystemLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/station")
public class StationController {

    @Autowired
    private StationService stationService;
    @Autowired
    private StationDao stationDao;



    // 跳转到照片首页业并打印
    @RequestMapping("showStation")
    public String showStation( Model model) {
        return "station/stationList";
    }
    // 新增页面跳转
    @RequestMapping("getToAddStation")
    public String getToAddStation( Model model) {
        return "station/addStation";
    }
    @RequestMapping("/listStations")
    @ResponseBody
    public ResultDo<List<Station>> listStations(String stationName, String stationCode,
                                                @RequestParam(name = "page", required = true, defaultValue = "1") Integer page,
                                                @RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit){

        PageHelper.startPage(page, limit);
        List<Station> list = stationDao.getAllBy(stationName,stationCode);
        PageInfo<Station> p = new PageInfo<Station>(list);
        ResultDo<List<Station>> res = new ResultDo<List<Station>>();
        res.setCode(0);
        res.setMsg("");
        res.setData(p.getList());
        res.setCount(p.getTotal());
        return res;
    }

    //新增站点
    @RequestMapping("/addStation")
    @ResponseBody
    public Map addStation(Station station){
        Map map=new HashMap();
        map.put("flat","添加成功");
        int count=stationDao.getStationConut(station);
        if(count>0){
            map.put("flat","站点编码已存在");
            return map;
        }
        int num=stationDao.addStation(station);
        if(num<=0){
            map.put("flat","添加失败");
        }
        return map;
    }

    // 跳转到设备页面
    @SystemLog(module = "站点", methods = "编辑页面")
    @RequestMapping("toEditStation")
    public String toEditStation(String id, Model model) {
        model.addAttribute("station", stationDao.getById(Integer.parseInt(id)));
        return "/station/editStation";
    }
    @RequestMapping("editStation")
    @ResponseBody
    public Map<String, Object> editStation(Station station){
        Map<String,Object> map = new HashMap<>();
        int count = stationDao.editStation(station);
        if(count>0){
            map.put("hint","修改成功");
            map.put("flat", true);
        }else{
            map.put("hint","修改失败");
            map.put("flat", false);
        }
        return map;
    }
    //删除站点
    @RequestMapping("/del")
    @ResponseBody
    public Map<String, Object> del(String id) {
        Map<String,Object> map = new HashMap<>();
        int count = stationDao.delStation(id);;
        if(count>0){
            map.put("hint","删除成功");
            map.put("flat", true);
        }else{
            map.put("hint","删除失败");
            map.put("flat", false);
        }
        return map;
    }

    // 设备首页
    @SystemLog(module = "站点", methods = "用户站点权限查询")
    @RequestMapping("queryAllByUser")
    @ResponseBody
    public ResultDo<List<Station>> queryAllByUser(String id, String stationName,
                                                  @RequestParam(name = "page", required = true, defaultValue = "1") Integer pageNum,
                                                  @RequestParam(name = "limit", required = true, defaultValue = "10") Integer pageSize, String createUser) {
        PageHelper.startPage(pageNum, pageSize);
        List<Station> list = stationService.getAllByUser(stationName,id,createUser);
        PageInfo<Station> p = new PageInfo<Station>(list);
        ResultDo<List<Station>> res = new ResultDo<List<Station>>();
        res.setCode(0);
        res.setMsg("");
        res.setData(p.getList());
        res.setCount(p.getTotal());
        return res;
    }
}
