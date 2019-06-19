package com.jm.service;

import java.util.List;
import java.util.Map;

import com.jm.bean.NodeType;

public interface NodeTypeService {
	
	List<NodeType> queryAll(String startDate,String endDate,String name);

	Map<String,Object> addNodeType(NodeType nodeType);
	
	Map<String,Object> delNodeType(String id);
	
	Map<String,Object> editNodeType(NodeType nodeType);
	


}
