package com.jm.serviceImpl;

import java.util.List;
import java.util.UUID;

import com.jm.bean.Fileidx;
import com.jm.dao.FileidxDao;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.iot.client.common.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jm.bean.FlowView;
import com.jm.dao.FlowViewDao;
import com.jm.service.FlowViewService;

@Service
public class FlowViewServiceImpl implements FlowViewService {
    @Autowired
    private FlowViewDao flowViewDao;
    @Autowired
    private FileidxDao fileidxDao;
    
    @Override
    public List<FlowView> queryByCarNum(String carNum, String station,String[] stationMarks) {
        List<FlowView> list=this.flowViewDao.queryPreviewStation(null,null,station,null, carNum,null,stationMarks);
        return getPicture(list);
    }

    @Override
    public List<FlowView> reciveByCarNum(String carNum, String station, Integer maxId) {
        List<FlowView> list=this.flowViewDao.queryPreview(null, null, station,null, carNum,maxId);
        return list;
    }

    @Override
    public List<FlowView> queryByTime(String startDate, String endDate, String station,Integer direction,String[] stationMarks) {
        List<FlowView> list=this.flowViewDao.queryPreviewStation(startDate, endDate, station,direction, null,null,stationMarks);
        return getPicture(list);
    }
    @Override
    public List<FlowView> apiQueryByTime(String startDate, String endDate, String station) {
        return this.flowViewDao.queryPreview(startDate, endDate, station,null, null,null);
    }

    @Override
    public List<FlowView> reciveByTime(String startDate, String endDate, String station, Integer maxId) {
        return this.flowViewDao.queryPreview(startDate, endDate, station, null,null, maxId);
    }

	  
    /** 
     * @discription: 
     * @author: yinzhixi       
     * @created: 2019年3月29日 上午10:16:02
     * @param overRage
     * @return     
     * @see com.jm.service.FlowViewService#queryOverRagePreview(java.lang.String)     
     */  
	@Override
	public List<FlowView> queryOverRagePreview(double overRage,String[] stationMarks) {
		// TODO Auto-generated method stub
        List<FlowView> list=this.flowViewDao.queryOverRagePreview(overRage,stationMarks);
		return getPicture(list);
	}

	/**
	 * 功能描述: 通过relCode获取图片的地址
	 * @param null
	 * @return: 
	 * @Author: yinzhixi
	 * @Date: 2019/4/9 11:54
	*/
	public List<FlowView> getPicture(List<FlowView> list){
	    for(int i=0;i<list.size();i++){
            Fileidx fileidx=fileidxDao.getFileidx(list.get(i).getRelCode(),"3");
            if(fileidx==null){
                continue;
            }
            String path=fileidx.getFilepath();
            list.get(i).setImgUrl(path);
        }
        return list;
    }

    public static void main(String[] args) {
        String Str1=UUID.randomUUID().toString().replace("-", "").toUpperCase();
        System.out.println("UUID................................."+Str1);

    }

}
