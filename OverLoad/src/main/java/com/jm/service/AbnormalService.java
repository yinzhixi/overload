package com.jm.service;

import com.jm.bean.Abnormal;

import java.util.List;

public interface AbnormalService {

    List<Abnormal> getAlls(String startDate, String endDate, String station, Integer lane, String carNum, Integer axleCnt, Integer axleCnt_op, Double startWeight, Double endWeight, Integer overRate, Integer recognition, Integer direction, Integer previewId, Integer overRate_op, Integer overRate_num, String[] userStationCodes);

    int delAbnormal(Integer abnormalid);

    List<Abnormal> getAllImgs(String carNum, String dateTime);

    Abnormal getAbnormalById(Integer id);
}
