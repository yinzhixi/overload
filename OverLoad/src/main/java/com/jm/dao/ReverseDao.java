package com.jm.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jm.bean.Reverse;

public interface ReverseDao {
	
	  public List<Reverse> queryReverse(	          
	            @Param("stationName")String stationName, 
	            @Param("carNum")String carNum, 
	            @Param("maxId")Integer maxId);
	   public Integer queryMaxId();

	  public List<Reverse> reverseList(
			@Param("stationName")String stationName,
			@Param("carNum")String carNum,
			@Param("startDate")String  startDate,
			@Param("endDate")String  endDate
	  );
	  public Integer reverseDel(@Param("id") Integer id);

	  public Reverse reversById(@Param("id") Integer id);
}
