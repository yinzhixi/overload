package com.jm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jm.bean.PreviewCalc;
import com.jm.bean.PreviewCalcByHour;
import com.jm.bean.PreviewCalcByOverPercent;
import com.jm.bean.PreviewCalcByRecognitionRate;
import com.jm.bean.PreviewCalcByWeight;

@Service
public interface PreviewCalcService {
    
    public List<PreviewCalc> previewCalc(String startDate, String endDate, String station,int[] userStationIds);

    public List<PreviewCalcByWeight> previewCalcByWeight(String startDate, String endDate, String station,int[] userStationIds);

    public List<PreviewCalcByWeight> previewCalcByTon(String startDate, String endDate, String station);

    public List<PreviewCalcByOverPercent> previewCalcByOverPercent(String startDate, String endDate, String station);

    public List<PreviewCalcByRecognitionRate> calcByRecognitionRate(String startDate, String endDate, String station);

    public List<PreviewCalcByHour> calcByHour(String startDate, String endDate, String station);
    
}
