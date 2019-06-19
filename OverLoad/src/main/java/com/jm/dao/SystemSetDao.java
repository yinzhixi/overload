package com.jm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jm.bean.SystemSet;

public interface SystemSetDao {

    List<SystemSet> loadAllProperty();

    List<SystemSet> findList(@Param("key") String key, @Param("val")String val);

    void update(@Param("id")String id, @Param("key")String key, 
            @Param("val")String val, @Param("comment")String comment);

}
