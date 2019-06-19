package com.jm.service;

import java.util.List;

import com.jm.bean.OverLoadRate;

public interface OverLoadRateService {
	
	public List<OverLoadRate> getByOverLoad(String startDate,String endDate,String station, Integer direction,int[] userStationIds);

}
