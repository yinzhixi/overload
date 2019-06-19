package com.jm.dao;

import com.jm.bean.UserStation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserStationDao {


    List<UserStation> getByEmpId(@Param("userId") Integer userId);

    int add(UserStation userStation);

    int del(@Param("userId") Integer userId, @Param("stationId") Integer stationId);


    int userStationCount(@Param("userId") Integer userId, @Param("stationId") Integer stationId);


    void cancleAllStation(@Param("userId") Integer userId);

    void assignAllStation(@Param("userId") Integer userId);


}
