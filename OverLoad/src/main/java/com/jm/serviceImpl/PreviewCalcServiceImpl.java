package com.jm.serviceImpl;

import com.jm.bean.*;
import com.jm.dao.PreviewCalcDao;
import com.jm.service.PreviewCalcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreviewCalcServiceImpl implements PreviewCalcService {
    @Autowired
    private PreviewCalcDao dao;
    @Override
    public List<PreviewCalc> previewCalc(String startDate, String endDate, String station,int[]userStationIds) {
        List<PreviewCalc> list = dao.previewCalc( startDate,  endDate, station,userStationIds);
        return list;
    }
    
    @Override
    public List<PreviewCalcByWeight> previewCalcByWeight(String startDate, String endDate, String station,int[] userStationIds) {
        List<PreviewCalcByWeight> list = dao.previewCalcByWeight( startDate,  endDate, station,userStationIds);
        return list;
    }

    @Override
    public List<PreviewCalcByWeight> previewCalcByTon(String startDate, String endDate, String station) {
        List<PreviewCalcByWeight> list = dao.previewCalcByTon( startDate,  endDate, station);
        return list;
    }

    @Override
    public List<PreviewCalcByOverPercent> previewCalcByOverPercent(String startDate, String endDate, String station) {
        List<PreviewCalcByOverPercent> list = dao.previewCalcByOverPercent( startDate,  endDate, station);
        return list;
    }

    @Override
    public List<PreviewCalcByRecognitionRate> calcByRecognitionRate(String startDate, String endDate, String station) {
        List<PreviewCalcByRecognitionRate> list = dao.previewCalcByRecognitionRate( startDate,  endDate, station);
        return list;
    }

    @Override
    public List<PreviewCalcByHour> calcByHour(String startDate, String endDate, String station) {
        List<PreviewCalcByHour> list = dao.previewCalcByHour( startDate,  endDate, station);
        return list;
    }
    
}
