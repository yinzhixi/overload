package com.jm.serviceImpl;

import com.jm.bean.StaticWeight;
import com.jm.dao.StaticWeightDao;
import com.jm.service.StaticWeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaticWeightServiceImpl implements StaticWeightService {

    @Autowired
    private StaticWeightDao staticWeightMapper;

    @Override
    public int insertStaticWeight(StaticWeight staticWeight) {
        return staticWeightMapper.insert(staticWeight);
    }

    @Override
    public int editStaticWeight(StaticWeight staticWeight) {
        return staticWeightMapper.updateByPrimaryKey(staticWeight);
    }

    @Override
    public List<StaticWeight> listStaticWeights(StaticWeight staticWeight) {
        return staticWeightMapper.listStaticWeights(staticWeight);
    }
}
