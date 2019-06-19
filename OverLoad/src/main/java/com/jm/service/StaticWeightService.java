package com.jm.service;

import com.jm.bean.StaticWeight;

import java.util.List;

public interface StaticWeightService {

    public int insertStaticWeight(StaticWeight staticWeight);

    public int editStaticWeight(StaticWeight staticWeight);

    public List<StaticWeight> listStaticWeights(StaticWeight staticWeight);
}
