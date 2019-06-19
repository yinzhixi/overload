package com.jm.service;

import com.jm.bean.Station;

import java.util.List;

public interface StationService {
	/**
	 * 查询全部
	 */
	public List<Station> getAll();

	List<Station> getAllByUser(String stationName, String id, String createUser);

	Station getByStationName(String stationName);

	Station getById(Integer id);

}
