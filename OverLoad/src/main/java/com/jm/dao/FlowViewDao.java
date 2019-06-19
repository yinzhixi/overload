package com.jm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jm.bean.FlowView;

public interface FlowViewDao {
    /**
     * @param station 站点
     * @param 车牌
     */
    public List<FlowView> queryPreview(
            @Param("startDate")String startDate, 
            @Param("endDate")String endDate, 
            @Param("station")String station, 
            @Param("direction")Integer direction, 
            @Param("carNum")String carNum, 
            @Param("maxId")Integer maxId);

    /**
     * @param station 根据用户站点权限查询数据
     * @param
     */
    public List<FlowView> queryPreviewStation(
            @Param("startDate")String startDate,
            @Param("endDate")String endDate,
            @Param("station")String station,
            @Param("direction")Integer direction,
            @Param("carNum")String carNum,
            @Param("maxId")Integer maxId,
            @Param("stationMarks") String[] stationMarks);
    public Integer queryMaxId();
	  
    /**     
     * @discription: 
     * @author: yinzhixi       
     * @created: 2019年1月10日 上午10:16:40  
     * @param overRage
     * @return     
     */
	public List<FlowView> queryOverRagePreview(@Param("overRage")double overRage,
                                               @Param("stationMarks") String[] stationMarks);
}
