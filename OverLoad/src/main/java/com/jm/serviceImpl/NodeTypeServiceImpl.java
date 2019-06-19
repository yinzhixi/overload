package com.jm.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jm.bean.NodeType;
import com.jm.dao.NodeDao;
import com.jm.dao.NodeTypeDao;
import com.jm.service.NodeTypeService;
@Service
public class NodeTypeServiceImpl implements NodeTypeService {
	@Autowired
	private NodeTypeDao TypeDao;
	@Autowired
	private NodeDao nodeDao;
	@Override
	public List<NodeType> queryAll(String startDate,String endDate,String name) {
		List<NodeType> queryAll = TypeDao.queryAll(startDate,endDate,name);
		return queryAll;
	}
	@Override
	public Map<String,Object> addNodeType(NodeType nodeType) {
		Map<String,Object> map = new HashMap<>();				
		String getnId2 = nodeType.getnId();
		NodeType queryById = TypeDao.queryById(getnId2);		
			 if(queryById != null){
					map.put("hint","类别型号已存在，请添加其他型号！");
					map.put("flat", true);
					return map;
			}		
		Subject subject = SecurityUtils.getSubject();
    	Object principal = subject.getPrincipal();    	
    	nodeType.setCreateUser(principal.toString());				
		nodeType.setCreateTime(new Date());
		int addNodeType = TypeDao.addNodeType(nodeType);
		if(addNodeType>0){
			map.put("hint","添加成功");
			map.put("flat", true);
		}else{
			map.put("hint","添加失败");
			map.put("flat", false);
		}
		return map;
		
	}
	@Override
	public Map<String, Object> delNodeType(String id) {
		Map<String,Object> map = new HashMap<>();
		int delNodeType = TypeDao.delNodeType(id);
		nodeDao.delAllNodeById(id);
		if(delNodeType>0){
			map.put("hint","删除成功");
			map.put("flat", true);
		}else{
			map.put("hint","删除失败");
			map.put("flat", false);
		}
		return map;
	}
	@Override
	public Map<String, Object> editNodeType(NodeType nodeType) {
		Map<String,Object> map = new HashMap<>();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();		
		nodeType.setUpdateTime(format.format(date));		
		int editNodeType = TypeDao.editNodeType(nodeType);
		if(editNodeType>0){
			map.put("hint","修改成功");
			map.put("flat", true);
		}else{
			map.put("hint","修改失败");
			map.put("flat", false);
		}
		return map;
	}
	
	
}
