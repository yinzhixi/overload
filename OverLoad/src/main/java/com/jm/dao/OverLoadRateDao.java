package com.jm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jm.bean.OverLoadRate;

public interface OverLoadRateDao {
	
	/**
     * 按吨位统计
     * @param station 站点
     * @param endDate  结束时间
     * @param startDate  起始时间
     * @param direction  行车方向
     */
    public List<OverLoadRate> getAllByOverRate(
            @Param("startDate")String startDate, 
            @Param("endDate")String endDate, 
            @Param("station")String station, 
            @Param("direction")Integer direction,
            @Param("userStationIds")int[] userStationIds);

    
   
}
