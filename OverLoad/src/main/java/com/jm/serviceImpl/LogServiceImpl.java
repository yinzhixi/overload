package com.jm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jm.bean.LogEntity;
import com.jm.dao.LogDao;
import com.jm.service.LogService;
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogDao logDao;
    
    @Override
    public void saveLog(LogEntity logEntity) {
        logDao.addLog(logEntity);
    }

    @Override
    public List<LogEntity> findList(String userAccount, String module, String method) {
        return this.logDao.findList(userAccount, module, method);
    }
    
}
