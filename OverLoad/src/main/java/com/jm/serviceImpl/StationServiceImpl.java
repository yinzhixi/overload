package com.jm.serviceImpl;

import com.jm.bean.Station;
import com.jm.bean.UserStation;
import com.jm.dao.StationDao;
import com.jm.dao.UserStationDao;
import com.jm.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StationServiceImpl implements StationService {
	@Autowired
	private StationDao dao;
	@Autowired
	private UserStationDao userStationDao;
	
	//查询全部站点
	@Override
	public List<Station> getAll() {
		return dao.getAll();
	}

	@Override
	public List<Station> getAllByUser(String stationName, String id, String createUser) {
		List<UserStation> userStationslist=userStationDao.getByEmpId(Integer.parseInt(createUser));//该员工所有的设备权限
		List<Integer> listStation=new ArrayList();
		for(int j=0;j<userStationslist.size();j++){
			listStation.add(userStationslist.get(j).getStationId());
		}

		List<Station> list=dao.getAllBy(stationName,id);//查询全部站点
		List<Station> relist =new ArrayList<>();//返回有标记回显的用户权限
		for (int i=0;i<list.size();i++){
			Station station=list.get(i);
			if (listStation.contains(station.getId())){
				station.setTag("1");
			}
			relist.add(station);
		}
		return relist;
	}

    @Override
    public Station getByStationName(String stationName) {
        return dao.getByStationName(stationName);
    }

    @Override
    public Station getById(Integer id) {
        return dao.getById(id);
    }

}
