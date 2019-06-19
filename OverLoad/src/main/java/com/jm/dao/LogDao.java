package com.jm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jm.bean.LogEntity;

public interface LogDao {
    int addLog(LogEntity logEntity);

    List<LogEntity> findList(
            @Param("userId")String userId, 
            @Param("module")String module,
            @Param("method")String method);
}
