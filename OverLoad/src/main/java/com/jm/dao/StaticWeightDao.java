package com.jm.dao;


import com.jm.bean.StaticWeight;

import java.util.List;

public interface StaticWeightDao {
    int deleteByPrimaryKey(String staticid);

    int insert(StaticWeight record);

    int insertStaticWeight(StaticWeight record);

    StaticWeight selectByPrimaryKey(String staticid);

    int updateByPrimaryKeySelective(StaticWeight record);

    int updateByPrimaryKeyWithBLOBs(StaticWeight record);

    int updateByPrimaryKey(StaticWeight record);

    List<StaticWeight> listStaticWeights(StaticWeight staticWeight);
}