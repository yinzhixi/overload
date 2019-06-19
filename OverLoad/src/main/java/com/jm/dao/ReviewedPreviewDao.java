package com.jm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jm.bean.Preview;
import com.jm.bean.ReviewedPreview;

public interface ReviewedPreviewDao {

    int existsCheckNo(@Param("checkNo") String checkNo);

    /**
     * 删除
     */
    int delReviewed(Integer previewId);

    /**
     * 添加
     */
    int addReviewed(Preview reviewedPreview);

    /**
     * 修改审核状态
     */
    void updateReviewed(ReviewedPreview reviewed);

    /**
     * 修改审核状态 删除后
     */
    void updateReviewed2(ReviewedPreview reviewed);

    ReviewedPreview getByReviewedId(Integer previewId);

    /**
     * 精检首页展示
     * 
     */
    List<ReviewedPreview> queryAll(
            @Param("startDate") String startDate, 
            @Param("endDate") String endDate,
            @Param("station") String station, 
            @Param("lane") Integer lane, 
            @Param("carNum") String carNum,
            @Param("axleCnt") Integer axleCnt, 
            @Param("axleCnt_op") Integer axleCnt_op, 
            @Param("startWeight") Double startWeight,
            @Param("endWeight") Double endWeight, 
            @Param("overRate") Integer overRate, 
            @Param("overRate_num") Integer overRate_num, 
            @Param("overRate_op") Integer overRate_op,
            @Param("recognition") Integer recognition, 
            @Param("direction") Integer direction,
            @Param("userStationMarks") String[] userStationMarks);

    void updatePreview2(ReviewedPreview reviewed);

    void setUploaded(
            @Param("previewId") Integer previewId, 
            @Param("res") Integer res,
            @Param("uploadTime") String uploadTime);
}
