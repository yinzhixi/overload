package com.jm.serviceImpl;

import com.jm.bean.Abnormal;
import com.jm.bean.Fileidx;
import com.jm.bean.Preview;
import com.jm.dao.AbnormalMapper;
import com.jm.dao.FileidxDao;
import com.jm.service.AbnormalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AbnormalServiceImpl implements AbnormalService {

    @Autowired
    private AbnormalMapper abnormalMapper;

    @Autowired
    private FileidxDao fileidxDao;

    @Override
    public List<Abnormal> getAlls(String startDate, String endDate, String station, Integer lane, String carNum, Integer axleCnt, Integer axleCnt_op, Double startWeight, Double endWeight, Integer overRate, Integer recognition, Integer direction, Integer previewId, Integer overRate_op, Integer overRate_num, String[] userStationCodes) {
        return abnormalMapper.getAlls(startDate, endDate, station, lane, carNum, axleCnt,axleCnt_op, startWeight, endWeight, overRate,
                recognition, direction, previewId,overRate_op,overRate_num,userStationCodes);
    }

    @Override
    public int delAbnormal(Integer abnormalid) {
        return abnormalMapper.delAbnormal(abnormalid);
    }

    @Override
    public List<Abnormal> getAllImgs(String carNum, String dateTime) {
        return null;
    }

    @Override
    public Abnormal getAbnormalById(Integer id) {
        return abnormalMapper.selectByPrimaryKey(id);
    }

    /**
     * 功能描述:录入抓拍图片
     * @param null
     * @return:
     * @Author: yinzhixi
     * @Date: 2019/4/9 18:10
     */
    public Abnormal getPreviewList(Abnormal abnormal){

        String relCode=abnormal.getRelcode();
        abnormal.setFrontPic(getPicture(relCode,"3"));
        abnormal.setSidePic(getPicture(relCode,"4"));
        abnormal.setUpPic(getPicture(relCode,"5"));
        abnormal.setBackPic(getPicture(relCode,"6"));
        return abnormal;
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
}
