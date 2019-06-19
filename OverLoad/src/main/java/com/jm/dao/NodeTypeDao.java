package com.jm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jm.bean.NodeType;

public interface NodeTypeDao {
	//首页展示
	List<NodeType> queryAll(@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("name")String name);

	int addNodeType(NodeType nodeType);
	
	int delNodeType(String id);
	
	int editNodeType(NodeType nodeType);
	
	NodeType queryById(String id);
	
	List<NodeType> getAll();
	
	
	
}
