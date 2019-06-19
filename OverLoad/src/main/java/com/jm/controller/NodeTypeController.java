package com.jm.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jm.bean.NodeType;
import com.jm.bean.ResultDo;
import com.jm.service.NodeTypeService;
import com.jm.utils.export.ExportExcelUtils;
import com.jm.utils.export.SheetView;
import com.jm.utils.export.Table;
import com.jm.web.log.SystemLog;

@Controller
@RequestMapping("/type")
public class NodeTypeController {
    @Autowired
    private NodeTypeService TypeService;

    // 跳转到类型页面
    @SystemLog(module = "设备类型", methods = "主页面")
    @RequestMapping("getToNodeType")
    public String getToNodeType() {
        return "/nodeType/NodeTypeIndex";
    }

    // 型号类别首页
    @SystemLog(module = "设备类型", methods = "查询")
    @RequestMapping("queryAll")
    @ResponseBody
    public ResultDo<List<NodeType>> queryAll(String startDate, String endDate, String name,
            @RequestParam(name = "page", required = true, defaultValue = "1") Integer pageNum,
            @RequestParam(name = "limit", required = true, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<NodeType> list = TypeService.queryAll(startDate, endDate, name);
        PageInfo<NodeType> p = new PageInfo<NodeType>(list);
        ResultDo<List<NodeType>> res = new ResultDo<List<NodeType>>();
        res.setCode(0);
        res.setMsg("");
        res.setData(p.getList());
        res.setCount(p.getTotal());
        return res;
    }

    @SystemLog(module = "设备类型", methods = "添加")
    @RequestMapping("addNodeType")
    @ResponseBody
    public Map<String, Object> addNodeType(NodeType nodeType) {
        Map<String, Object> addNodeType = TypeService.addNodeType(nodeType);
        return addNodeType;
    }

    @SystemLog(module = "设备类型", methods = "删除")
    @RequestMapping("delNodeType")
    @ResponseBody
    public Map<String, Object> delNodeType(String nId) {
        Map<String, Object> delNodeType = TypeService.delNodeType(nId);
        return delNodeType;
    }

    @SystemLog(module = "设备类型", methods = "编辑")
    @RequestMapping("editNodeType")
    @ResponseBody
    public Map<String, Object> editNodeType(NodeType nodeType) {
        Map<String, Object> editNodeType = TypeService.editNodeType(nodeType);
        return editNodeType;
    }

    @SystemLog(module = "设备类型", methods = "导出")
    @RequestMapping("/export")
    public void export(String startDate, String endDate, String name, HttpServletResponse response,
            HttpServletRequest request) {
        List<NodeType> list = TypeService.queryAll(startDate, endDate, name);

        List<Map<String, Object>> data = new ArrayList<>();
        for (NodeType nodeType : list) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("nId", nodeType.getnId());
            item.put("name", nodeType.getName());
            item.put("pinyin", nodeType.getPinyin());
            item.put("desp", nodeType.getDesp());
            item.put("version", nodeType.getVersion());
            item.put("createUser", nodeType.getCreateUser());
            item.put("createTime", nodeType.getCreateTime());
            item.put("updateTime", nodeType.getUpdateTime());
            data.add(item);
        }

        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sf.format(new Date());
        String filename = "NodeType" + date + ".xls";
        List<SheetView> sheets = new ArrayList<SheetView>();
        sheets.add(new SheetView("设备类型",
                new Table("设备类型", 0, 0,
                        new Object[][] { { "nId", "设备型号", 256 * 10, ExportExcelUtils.STRING_CELL },
                                { "name", "设备名称", 256 * 10, ExportExcelUtils.STRING_CELL },
                                { "pinyin", "英文名", 256 * 15, ExportExcelUtils.STRING_CELL },
                                { "desp", "设备描述", 256 * 15, ExportExcelUtils.STRING_CELL },
                                { "version", "版本号", 256 * 10, ExportExcelUtils.STRING_CELL },
                                { "createUser", "创建者", 256 * 10, ExportExcelUtils.STRING_CELL },
                                { "createTime", "创建时间", 256 * 15, ExportExcelUtils.STRING_CELL },
                                { "updateTime", "修改时间", 256 * 15, ExportExcelUtils.STRING_CELL }, },
                        data)));
        ExportExcelUtils.export(response, request, filename, sheets);
    }
}
