package com.jm.dao;
import org.apache.ibatis.annotations.Param;

import com.jm.bean.Car;
public interface CarDao {
	/**
	 * 查询车辆的具体信息
	 * @param id
	 * @return
	 */
	Car queryByCar(@Param("carNum")String carNum);

}
