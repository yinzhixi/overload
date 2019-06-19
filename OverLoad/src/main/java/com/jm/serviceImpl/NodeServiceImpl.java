package com.jm.serviceImpl;

import java.util.*;

import com.jm.bean.*;
import com.jm.dao.*;
import com.jm.service.EmpService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jm.service.NodeService;

@Service
public class NodeServiceImpl implements NodeService {
	@Autowired	
	private NodeDao nodeDao;
	@Autowired
	private NodeTypeDao nodeTypeDao;
	@Autowired
	private UserNodeDao userNodeDao;
	@Autowired
	private EmpService empService;
	@Autowired
	private StationDao stationDao;
	@Autowired
	private EmpDao empDao;
	@Autowired
	private UserStationDao userStationDao;
	@Override
	public List<Node> queryAll(String startDate, String endDate, String nodeName,String[] stationCodes) {
//		Subject subject = SecurityUtils.getSubject();//获取登录用户id
//		String empName=null;
//		if(subject != null) {
//			empName = (String)subject.getPrincipal();
//			Emp emp=empService.getById(empName);
//		}
		List<Node> list = nodeDao.queryAll(startDate, endDate, nodeName,stationCodes);
		for (Node node : list) {
			NodeType nodeType = nodeTypeDao.queryById(node.getnId());
			node.setName(nodeType.getName());
		}
		return list;
	}

	@Override
	public Map<String, Object> addNode(Node node) {
		Map<String,Object> map = new HashMap<>();
			Node byId = nodeDao.getById(node.getId());
			 if(byId != null){
					map.put("hint","设备型号已存在，请添加其他设备！");
					map.put("flat", true);
					return map;
			}	
		
		Subject subject = SecurityUtils.getSubject();
    	Object principal = subject.getPrincipal();     	
    	node.setCreateUser(principal.toString());				
		node.setCreateTime(new Date());
		int addNode = nodeDao.addNode(node);
		if(addNode>0){
			map.put("hint","添加成功");
			map.put("flat", true);
		}else{
			map.put("hint","添加失败");
			map.put("flat", false);
		}
		return map;
	}

	@Override
	public Map<String, Object> delNode(String id) {	
		Map<String,Object> map = new HashMap<>();	
		int delNode = nodeDao.delNode(id);
		if(delNode>0){
			map.put("hint","删除成功");
			map.put("flat", true);
		}else{
			map.put("hint","删除失败");
			map.put("flat", false);
		}
		return map;
	}

	@Override
	public Map<String, Object> editNode(Node node) {
		Map<String,Object> map = new HashMap<>();	
	 int editNode = nodeDao.editNode(node);
		if(editNode>0){
			map.put("hint","删除成功");
			map.put("flat", true);
		}else{
			map.put("hint","删除失败");
			map.put("flat", false);
		}
		return map;
	}

	@Override
	public List<Node> queryAllByUser(String startDate, String endDate, String nodeName, String createUser) {
		List<UserNode> userNodelist=userNodeDao.getByEmpId(createUser);//该员工所有的设备权限
		List<String> listNode=new ArrayList();
		for(int j=0;j<userNodelist.size();j++){
			listNode.add(userNodelist.get(j).getNodeCode());
		}
		Subject subject = SecurityUtils.getSubject();//获取登录用户id
		String empName=null;
		Emp emp= new Emp();
		if(subject != null) {
			empName = (String)subject.getPrincipal();
			emp=empService.getById(empName);
		}
		Integer stationId=null;
		Station station=stationDao.getByStationName(emp.getStationName());
		if(station!=null){
			stationId=station.getId();
		}
		List<Node> list = nodeDao.queryAllByUser(startDate, endDate, nodeName,stationId);
		for (Node node : list) {
			NodeType nodeType = nodeTypeDao.queryById(node.getnId());
			node.setName(nodeType.getName());
		}
		List<Node> relist =new ArrayList<>();//返回有标记回显的用户权限
		for (int i=0;i<list.size();i++){
			Node node=list.get(i);
			if (listNode.contains(node.getId())){
				node.setTag("1");
			}
			relist.add(node);
		}
		return relist;
	}
	//返回登录人员的站点权限数组
	public int[] getUserStation(){
		Subject subject = SecurityUtils.getSubject();//获取登录用户id
		Emp emp=new Emp();
		if(subject != null) {
			String empName = (String)subject.getPrincipal();
			emp=empDao.selectById(empName);
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
