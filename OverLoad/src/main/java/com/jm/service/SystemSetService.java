package com.jm.service;

import java.util.List;

import com.jm.bean.SystemSet;

public interface SystemSetService {

    String getProperty(String property);

    List<SystemSet> findList(String key, String val);

    void setProperty(String id,String key, String val, String comment);

}
