package com.jm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jm.bean.Dict;
import com.jm.dao.DictDao;
import com.jm.service.DictService;

@Service
public class DictServiceImpl implements DictService {
	@Autowired
	private DictDao dao;

    @Override
    public List<Dict> findByType(String type) {
        return this.dao.getByType(type);
    }

    @Override
    public Dict findByCode(String code) {
        return this.dao.getByTypeCode(DictService.DICT_DIRECTION, code);
    }

}
