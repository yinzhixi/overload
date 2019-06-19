package com.jm.dao;

import com.jm.bean.Camera;

public interface CameraDao {
    int deleteByPrimaryKey(String flowNo);

    int insert(Camera record);

    int insertSelective(Camera record);

    Camera selectByPrimaryKey(String flowNo);

    int updateByPrimaryKeySelective(Camera record);

    int updateByPrimaryKey(Camera record);
}