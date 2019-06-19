package com.jm.service;

import java.util.List;

import com.jm.bean.ReviewedPreview;

public interface ReviewedPreviewService {

    int delReviewed(Integer previewId);

   // int addReviewed(ReviewedPreview reviewedPreview);

    List<ReviewedPreview> queryAll(
            String startDate, 
            String endDate, 
            String station, 
            Integer lane, 
            String carNum,
            Integer axleCnt, 
            Integer axleCnt_op, 
            Double statrWeight, 
            Double endWeight, 
            Integer overRate,
            Integer overRate_num,
            Integer overRate_op, 
            Integer recognition, 
            Integer direction,
            String[] userStationCodes);

    void setUploaded(Integer previewId, Integer res);

}
