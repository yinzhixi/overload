package com.jm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jm.bean.Preview;

public interface CalcScheduleDao {

    Integer findStonFromId();

    List<Preview> findListToCalc(@Param("fromId")Integer fromId);

    String findFromDate();

    void del_calc_s_ton(@Param("fromDate")String fromDate);
    void calc_s_ton(@Param("fromDate")String fromDate);

    void del_calc_s_over(@Param("fromDate")String fromDate);

    void calc_s_over(@Param("fromDate")String fromDate);

    void del_calc_s_recg(@Param("fromDate")String fromDate);

    void calc_s_recg(@Param("fromDate")String fromDate);

    void del_calc_s_hour(@Param("fromDate")String fromDate);

    void calc_s_hour(@Param("fromDate")String fromDate);

    void del_calc_s_cartype(@Param("fromDate")String fromDate);

    void calc_s_cartype(@Param("fromDate")String fromDate);

    void updateFromDate(@Param("fromDate")String fromDateStr);
    
    
}
