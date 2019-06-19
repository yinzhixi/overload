package com.jm.service;

import java.util.List;

import com.jm.bean.LogEntity;

public interface LogService {

    void saveLog(LogEntity log);

    List<LogEntity> findList(String userAccount, String module, String method);

}
