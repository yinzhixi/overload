package com.jm.service;

import java.util.List;

import com.jm.bean.Dict;

public interface DictService {
    String DICT_DIRECTION = "direction";
    
	/**
	 * 查询全部
	 */
	public List<Dict> findByType(String code);
    public Dict findByCode(String parseInt);
	

}
