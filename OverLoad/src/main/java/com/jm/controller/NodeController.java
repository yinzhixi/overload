package com.jm.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jm.bean.*;
import com.jm.dao.EmpDao;
import com.jm.dao.UserStationDao;
import com.jm.service.EmpService;
import com.jm.service.StationService;
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
import com.jm.dao.NodeDao;
import com.jm.dao.NodeTypeDao;
import com.jm.service.NodeService;
import com.jm.utils.export.ExportExcelUtils;
import com.jm.utils.export.SheetView;
import com.jm.utils.export.Table;
import com.jm.web.log.SystemLog;

@Controller
@RequestMapping("node")
public class NodeController {
    @Autowired
    private NodeService nodeService;
    @Autowired
    private NodeTypeDao nodeTypeDao;
    @Autowired
    private NodeDao nodeDao;
    @Autowired
    private StationService stationServcie;
    @Autowired
    private EmpDao empDao;
    @Autowired
    private UserStationDao userStationDao;

    // 跳转到设备页面
    @SystemLog(module = "设备", methods = "主页面")
    @RequestMapping("getToNode")
    public String getToNodeType() {
        return "/node/NodeIndex";
    }

    // 跳转到设备页面
    @SystemLog(module = "设备", methods = "编辑页面")
    @RequestMapping("getToEditNode")
    public String getToEditNode(String id, Model model) {
        model.addAttribute("node", nodeDao.getById(Integer.parseInt(id)));
        List<NodeType> all = nodeTypeDao.getAll();
        model.addAttribute("type", all);
        List<Station> list8 = stationServcie.getAll();
        model.addAttribute("stations", list8);
        return "/node/editNode";
    }

    // 跳转到设备页面
    @SystemLog(module = "设备", methods = "添加页面")
    @RequestMapping("getToAddNode")
    public String getToAddNode(Model model) {
        List<NodeType> all = nodeTypeDao.getAll();
        model.addAttribute("type", all);
        List<Station> list8 = stationServcie.getAll();
        model.addAttribute("stations", list8);
        return "/node/addNode";
    }

    @SystemLog(module = "设备", methods = "添加设备")
    @RequestMapping("addNode")
    @ResponseBody
    public Map<String, Object> addNode(Node node) {
        Map<String, Object> addNode = nodeService.addNode(node);
        return addNode;
    }

    @SystemLog(module = "设备", methods = "删除设备")
    @RequestMapping("delNode")
    @ResponseBody
    public Map<String, Object> delNode(String id) {
        Map<String, Object> delNode = nodeService.delNode(id);
        return delNode;
    }

    @SystemLog(module = "设备", methods = "编辑页面")
    @RequestMapping("editNode")
    @ResponseBody
    public Map<String, Object> editNode(Node node) {
        Map<String, Object> editNode = nodeService.editNode(node);
        return editNode;
    }


    @SystemLog(module = "设备", methods = "查询")
    @RequestMapping("queryAll")
    @ResponseBody
    public ResultDo<List<Node>> queryAll(String startDate, String endDate, String nodeName,
            @RequestParam(name = "page", required = true, defaultValue = "1") Integer pageNum,
            @RequestParam(name = "limit", required = true, defaultValue = "10") Integer pageSize) {
        String[] StationCodes=getUserStationCodes();
        PageHelper.startPage(pageNum, pageSize);
        List<Node> list = nodeService.queryAll(startDate, endDate, nodeName,StationCodes);
        PageInfo<Node> p = new PageInfo<Node>(list);
        ResultDo<List<Node>> res = new ResultDo<List<Node>>();
        res.setCode(0);
        res.setMsg("");
        res.setData(p.getList());
        res.setCount(p.getTotal());
        return res;
    }

    // 设备首页
    @SystemLog(module = "设备", methods = "用户设备权限查询")
    @RequestMapping("queryAllByUser")
    @ResponseBody
    public ResultDo<List<Node>> queryAllByUser(String startDate, String endDate, String nodeName,
                                               @RequestParam(name = "page", required = true, defaultValue = "1") Integer pageNum,
                                               @RequestParam(name = "limit", required = true, defaultValue = "10") Integer pageSize,String createUser) {
        PageHelper.startPage(pageNum, pageSize);
        List<Node> list = nodeService.queryAllByUser(startDate, endDate, nodeName,createUser);
        PageInfo<Node> p = new PageInfo<Node>(list);
        ResultDo<List<Node>> res = new ResultDo<List<Node>>();
        res.setCode(0);
        res.setMsg("");
        res.setData(p.getList());
        res.setCount(p.getTotal());
        return res;
    }
    @SystemLog(module = "设备", methods = "导出")
    @RequestMapping("/export")
    public void export(String startDate, String endDate, String nodeName, HttpServletResponse response,
            HttpServletRequest request) {
        String[] stationCodes=getUserStationCodes();
        List<Node> list = nodeService.queryAll(startDate, endDate, nodeName,stationCodes);

        List<Map<String, Object>> data = new ArrayList<>();
        for (Node node : list) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("id", node.getId());
            item.put("nodeName", node.getNodeName());
            item.put("pinyin", node.getPinyin());
            item.put("online", node.getOnline());
            item.put("lat", node.getLat());
            item.put("lon", node.getLon());
            item.put("key", node.getKey());
            item.put("createUser", node.getCreateUser());
            item.put("createTime", node.getCreateTime());
            item.put("name", node.getName());
            data.add(item);

        }

        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sf.format(new Date());
        String filename = "Node" + date + ".xls";
        List<SheetView> sheets = new ArrayList<SheetView>();
        sheets.add(new SheetView("设备类型",
                new Table("设备类型", 0, 0,
                        new Object[][] { { "id", "设备型号", 256 * 10, ExportExcelUtils.STRING_CELL },
                                { "name", "设备名称", 256 * 10, ExportExcelUtils.STRING_CELL },
                                { "pinyin", "英文名", 256 * 15, ExportExcelUtils.STRING_CELL },
                                { "online", "是否在线", 256 * 15, ExportExcelUtils.STRING_CELL },
                                { "lat", "经度", 256 * 10, ExportExcelUtils.STRING_CELL },
                                { "lon", "纬度", 256 * 10, ExportExcelUtils.STRING_CELL },
                                { "key", "秘钥", 256 * 15, ExportExcelUtils.STRING_CELL },
                                { "createUser", "创建者", 256 * 15, ExportExcelUtils.STRING_CELL },
                                { "createTime", "创建时间", 256 * 15, ExportExcelUtils.STRING_CELL },
                                { "name", "所属类别", 256 * 15, ExportExcelUtils.STRING_CELL }, },
                        data)));
        ExportExcelUtils.export(response, request, filename, sheets);
    }

    //返回登录人员的站点权限编码数组
    public String[] getUserStationCodes(){
        Subject subject = SecurityUtils.getSubject();//获取登录用户id
        Emp emp=new Emp();
        if(subject != null) {
            String empName = (String)subject.getPrincipal();
            emp=empDao.selectById(empName);
            //previewRecord.setEmpId(emp.getId());
        }
        List<UserStation> userStationslist=userStationDao.getByEmpId(emp.getId());//获取登录员工所有的站点权限

        String[] StationCodes=new String[userStationslist.size()];
        for(int j=0;j<userStationslist.size();j++){
            StationCodes[j]=userStationslist.get(j).getStationCode();
        }
        return StationCodes;
    }
}
