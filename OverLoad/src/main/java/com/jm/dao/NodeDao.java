package com.jm.dao;

import com.jm.bean.Node;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NodeDao {
	//首页展示
		List<Node> queryAll(@Param("startDate") String startDate, @Param("endDate") String endDate,
                            @Param("nodeName") String name, @Param("stationCodes") String[] stationCodes);

		List<Node> queryAllByUser(@Param("startDate") String startDate, @Param("endDate") String endDate,
                                  @Param("nodeName") String name, @Param("stationId") Integer stationId);
		int addNode(Node node);
		
		int delNode(String id);
		
		int editNode(Node node);
		
		Node getById(Integer id);
		
		void delAllNodeById(String id);
		
}
