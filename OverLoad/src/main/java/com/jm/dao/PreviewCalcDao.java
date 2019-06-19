package com.jm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jm.bean.PreviewCalc;
import com.jm.bean.PreviewCalcByHour;
import com.jm.bean.PreviewCalcByOverPercent;
import com.jm.bean.PreviewCalcByRecognitionRate;
import com.jm.bean.PreviewCalcByWeight;

public interface PreviewCalcDao {
    /**
     * 展示
     * @param station 站点
     * @param endDate  结束时间
     * @param startDate  起始时间
     */
    public List<PreviewCalc> previewCalc(@Param("startDate")String startDate, @Param("endDate")String endDate, @Param("station")String station,@Param("userStationIds") int[] userStationIds);
    
    /**
     * 按车道、吨位统计
     * @param station 站点
     * @param endDate  结束时间
     * @param startDate  起始时间
     */
    public List<PreviewCalcByWeight> previewCalcByWeight(@Param("startDate")String startDate, @Param("endDate")String endDate, @Param("station")String station,@Param("userStationIds")int[] userStationIds);
    
    /**
     * 按吨位统计
     * @param startDate
     * @param endDate
     * @param station
     * @return
     */
    public List<PreviewCalcByWeight> previewCalcByTon(@Param("startDate")String startDate, @Param("endDate")String endDate, @Param("station")String station);
    
    /**
     * 按超限率统计
     * @param startDate
     * @param endDate
     * @param station
     * @return
     */
    public List<PreviewCalcByOverPercent> previewCalcByOverPercent(@Param("startDate")String startDate, @Param("endDate")String endDate, @Param("station")String station);
    
    /**
     * 车道白天、夜晚识别率
     * @param startDate
     * @param endDate
     * @param station
     * @return
     */
    public List<PreviewCalcByRecognitionRate> previewCalcByRecognitionRate(@Param("startDate")String startDate, @Param("endDate")String endDate, @Param("station")String station);

    /**
     * 按时辰统计车辆数量
     * @param startDate
     * @param endDate
     * @param station
     * @return
     */
    public List<PreviewCalcByHour> previewCalcByHour(@Param("startDate")String startDate, @Param("endDate")String endDate, @Param("station")String station);
    
}
