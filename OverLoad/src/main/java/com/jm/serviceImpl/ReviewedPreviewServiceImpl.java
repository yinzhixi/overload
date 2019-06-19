package com.jm.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jm.bean.Preview;
import com.jm.bean.ReviewedPreview;
import com.jm.dao.ReviewedPreviewDao;
import com.jm.service.PreviewService;
import com.jm.service.ReviewedPreviewService;
import com.jm.service.SystemSetService;
//import com.jm.serviceImpl.out.FXCNodeService;

@Service
public class ReviewedPreviewServiceImpl implements ReviewedPreviewService {
    @Autowired
    private ReviewedPreviewDao reviewedPreviewDao;
    @Autowired
    private PreviewService previewService;
//    @Autowired
//    private FXCNodeService fxcNodeService;
    @Autowired
    private SystemSetService systemSetService;
    
    @Override
    @Transactional
   /* public int addReviewed(ReviewedPreview review) {
        String stationCode = systemSetService.getProperty(SystemProperties.STATION_CODE);
        
        Date weightTime = null;
        try {
            weightTime = DateUtils.parseDate(review.getDateTime(), "yyyy-MM-dd HH:mm:ss") ;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
        
        *//*while(this.reviewedPreviewDao.existsCheckNo(checkNo) > 0) {
            sq++;
            checkNo = stationCode+review.getLane()+sf.format(weightTime)+sq;
        }*//*
        
        Preview preview = previewService.getPreviewById(review.getPreviewId());
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        int sq = preview.getPreviewId();
        String sqStr = String.format("%6d", sq).substring(0,6);
        String checkNo = stationCode+review.getLane()+sf.format(weightTime)+sqStr;
        
        preview.setVenifyTime(review.getVenifyTime());
        preview.setCheckNo(checkNo);
        int addReviewed = reviewedPreviewDao.addReviewed(preview);
        *//*
        int notify = NumberUtils.toInt(systemSetService.getProperty(SystemProperties.NOTIFY),0);
        if(notify == 1) {
            fxcNodeService.onCheckWeight(preview);
        }*//*
        return addReviewed;
    }*/

    //@Override
    public List<ReviewedPreview> queryAll(
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
            Integer direction,String[] userStationCodes) {
        List<ReviewedPreview> queryAll = reviewedPreviewDao.queryAll(
                startDate, 
                endDate, 
                station, 
                lane, 
                carNum, 
                axleCnt,
                axleCnt_op,
                statrWeight, 
                endWeight, 
                overRate, 
                overRate_num,
                overRate_op,
                recognition,
                direction,userStationCodes);
        
        return queryAll;
    }

    @Override
    public int delReviewed(Integer previewId) {
        return reviewedPreviewDao.delReviewed(previewId);
    }

    @Override
    public void setUploaded(Integer previewId, Integer res) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String curTime = sf.format(new Date());
        this.reviewedPreviewDao.setUploaded(previewId,res,curTime);
    }
    
}
