package com.jm.service;

import java.util.List;

import com.jm.bean.FlowView;

public interface FlowViewService {

    List<FlowView> queryByCarNum(String carNum, String station,String[] stationMarks);

    List<FlowView> reciveByCarNum(String carNum, String station, Integer maxId);

    List<FlowView> queryByTime(String startDate, String endDate, String station,Integer direction,String[] stationMarks);

    List<FlowView> reciveByTime(String startDate, String endDate, String station, Integer maxId);
    
    List<FlowView> apiQueryByTime(String startDate, String endDate, String station);

	  
	    /**     
	     * @discription: 
	     * @author: yinzhixi       
	     * @created: 2019年1月10日 上午10:15:48  
	     * @param overRage
	     * @return     
	     */
	List<FlowView> queryOverRagePreview(double overRage,String[] stationMarks);

}
