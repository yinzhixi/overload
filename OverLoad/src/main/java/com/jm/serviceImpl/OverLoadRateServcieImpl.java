package com.jm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jm.bean.OverLoadRate;
import com.jm.dao.OverLoadRateDao;
import com.jm.service.OverLoadRateService;

@Service
public class OverLoadRateServcieImpl implements OverLoadRateService {
	
	@Autowired
	private OverLoadRateDao dao;
	
	@Override
	public List<OverLoadRate> getByOverLoad(String startDate, String endDate,
			String station,Integer direction,int[] userStationIds) {
		return dao.getAllByOverRate(startDate, endDate, station,direction,userStationIds);
	}

	

}
