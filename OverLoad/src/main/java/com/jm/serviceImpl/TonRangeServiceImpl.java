package com.jm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jm.bean.Ton;
import com.jm.dao.TonRangeDao;
import com.jm.service.TonRangeService;

@Service
public class TonRangeServiceImpl implements TonRangeService{
    @Autowired
    private TonRangeDao tonRangeDao;
    
    @Override
    public List<Ton> getAll() {
        return this.tonRangeDao.getAll();
    }

}
