package com.jm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jm.bean.Dict;

public interface DictDao {
	public List<Dict> getByType(@Param("type")String type);

	/**
	 * 按照code查询
	 */	
    public Dict getByTypeCode(@Param("type")String type,@Param("code")String code);
    
	
}
