package com.jm.serviceImpl;

import java.util.Date;
import java.util.List;

import com.jm.bean.Fileidx;
import com.jm.dao.FileidxDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jm.bean.Preview;
import com.jm.bean.PreviewImg;
import com.jm.dao.PreviewDao;
import com.jm.service.PreviewService;

@Service
public class PreviewServiceImpl implements PreviewService {

    @Autowired
    private PreviewDao dao;
    @Autowired
    private FileidxDao fileidxDao;

    @Override
    public List<PreviewImg> getAllImgs(String carNum, String dateTime) {
        List<PreviewImg> allImgs = dao.getAllImgs(carNum, dateTime);
        return allImgs;
    }

    @Override
    public List<Preview> getAll(String station, Integer time,String[] userStationCodes) {
        List<Preview> all = dao.getAll(station, time,userStationCodes);
        return all;

    }

    @Override
    public List<Preview> showAllPre(String startDate, String endDate, String station, Integer lane, String carNum,
            Integer axleCnt, Integer axleCnt_op,Double startWeight, Double endWeight, Integer overRate, Integer recognition,
            Integer direction, Integer previewId,
            Integer overRate_op,
            Integer overRate_num,String[] userStationCodes) {
        return dao.showAllByPre(startDate, endDate, station, lane, carNum, axleCnt,axleCnt_op, startWeight, endWeight, overRate,
                recognition, direction, previewId,overRate_op,overRate_num,userStationCodes);

    }

    @Override
    public List<Preview> getByCarNum(String startDate, String endDate, String station,String[] userStationCodes) {
        return dao.getAllByCarNum(startDate, endDate, station,userStationCodes);
    }

    @Override
    public int delPreview(Integer id) {
        return dao.delPreview(id);

    }

    /**
     * 修改前查询
     */
    @Override
    public Preview getPreviewById(Integer previewId) {
        Preview preview=dao.getPreviewById(previewId);
//        if(!StringUtils.isEmpty(preview.getRelCode())){
//            preview.setFrontPic(getPicture(preview.getRelCode(),"3"));
//            preview.setSidePic(getPicture(preview.getRelCode(),"4"));
//            preview.setUpPic(getPicture(preview.getRelCode(),"5"));
//            preview.setBackPic(getPicture(preview.getRelCode(),"6"));
//        }
        return preview;
    }

    @Override
    public int updatePreview(Preview preview) {
        return dao.updatePreview(preview);
    }

    @Override
    public int updatePreview(Integer previewId, Integer lane, String carNum, Integer axleCnt, Integer speed,
            Double sumWeight, Double limitWeight,String frontPic) {
        Preview preview = this.getPreviewById(previewId);
        this.delPreview(previewId);
//        preview.setPreviewId(previewId);
//        preview.setCreateTime(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
//        preview.setLane(lane);
//        preview.setCarNum(carNum);
//        preview.setAxleCnt(axleCnt);
//        preview.setSpeed(speed);
//        preview.setSumWeight(sumWeight);
//        preview.setLimitWeight(limitWeight);
//        preview.setFrontPic(frontPic);
        if(limitWeight == null) {
            limitWeight = 0.0;
        }
        if(sumWeight == null) {
            sumWeight = 0.0;
        }
        double overRage = sumWeight - limitWeight;
        overRage = overRage < 0 ? 0:overRage;
//        preview.setOverRage(overRage);
//        preview.setOverLoadRate((int)(overRage/limitWeight*100));
        //preview.setOverLoadId(overLoadId);
        
        return dao.updatePreview(preview);
    }

    /**
     * 功能描述:录入抓拍图片
     * @param null
     * @return:
     * @Author: yinzhixi
     * @Date: 2019/4/9 18:10
    */
    public List<Preview> getPreviewList(List<Preview> list){
        for(int i=0;i<list.size();i++){
//            String relCode= list.get(i).getRelCode();
//            if(relCode==null){
//                continue;
//            }else {
//                list.get(i).setFrontPic(getPicture(relCode,"3"));
//                list.get(i).setSidePic(getPicture(relCode,"4"));
//                list.get(i).setUpPic(getPicture(relCode,"5"));
//                list.get(i).setBackPic(getPicture(relCode,"6"));
//            }

        }
        return list;
    }

    /**
     * 功能描述: 根据条件查询出图片信息
     * @param null
     * @return: 
     * @Author: yinzhixi
     * @Date: 2019/4/9 16:13
    */
    public String getPicture(String relCode,String fileType){

        Fileidx fileidx=fileidxDao.getFileidx(relCode,fileType);
        String path=fileidx.getFilepath();
        return path;
    }


    @Override
    public int insertSelective(Preview record) {
        return dao.insertSelective(record);
    }
}
