package com.jm.dao;

import com.jm.bean.Abnormal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AbnormalMapper {
    int deleteByPrimaryKey(Integer abnormalid);

    int insert(Abnormal record);

    int insertSelective(Abnormal record);

    Abnormal selectByPrimaryKey(Integer abnormalid);

    int updateByPrimaryKeySelective(Abnormal record);

    int updateByPrimaryKey(Abnormal record);

    List<Abnormal> getAlls(
            @Param("startDate")String startDate,
            @Param("endDate")String endDate,
            @Param("station")String station,
            @Param("lane")Integer lane,
            @Param("carNum")String carNum,
            @Param("axleCnt")Integer axleCnt,
            @Param("axleCnt_op")Integer axleCnt_op,
            @Param("startWeight")Double startWeight,
            @Param("endWeight")Double endWeight,
            @Param("overRate")Integer overRate,
            @Param("recognition")Integer recognition,
            @Param("direction")Integer direction,
            @Param("previewId")Integer previewId,
            @Param("overRate_op")Integer overRate_op,
            @Param("overRate_num")Integer overRate_num,
            @Param("userStationCodes") String[] userStationCodes
    );

    int delAbnormal(Integer abnormalid);
}