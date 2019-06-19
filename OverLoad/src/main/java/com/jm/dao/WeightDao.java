package com.jm.dao;

import com.jm.bean.Weight;

public interface WeightDao {
    int deleteByPrimaryKey(String flowNo);

    int insert(Weight record);

    int insertSelective(Weight record);

    Weight selectByPrimaryKey(String flowNo);

    int updateByPrimaryKeySelective(Weight record);

    int updateByPrimaryKey(Weight record);
}