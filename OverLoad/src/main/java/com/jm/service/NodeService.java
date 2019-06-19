package com.jm.service;
import java.util.List;
import java.util.Map;
import com.jm.bean.Node;


public interface NodeService {
	
	List<Node> queryAll(String startDate,String endDate,String nodeName,String[] stationCodes);

	Map<String,Object> addNode(Node node);
	
	Map<String,Object> delNode(String id);
	
	Map<String,Object> editNode(Node node);

	List<Node> queryAllByUser(String startDate,String endDate,String nodeName,String createUser);
}
