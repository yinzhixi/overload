package com.jm.dao;

import com.jm.bean.Station;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StationDao {
	/**
	 * 查询全部站点
	 */	
	public List<Station> getAll();

	public List<Station> getAllBy(@Param("stationName") String stationName, @Param("stationCode") String stationCode);

	int addStation(Station station);

	int delStation(@Param("id") String id);

	int editStation(Station station);

    Station getByStationName(@Param("stationName") String stationName);

    Station getById(@Param("id") Integer id);

    int getStationConut(Station station);
}
