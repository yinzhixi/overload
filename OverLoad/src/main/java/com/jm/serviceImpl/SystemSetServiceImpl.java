package com.jm.serviceImpl;

import java.util.List;
import java.util.Objects;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jm.bean.SystemSet;
import com.jm.dao.SystemSetDao;
import com.jm.service.SystemSetService;

@Service
public class SystemSetServiceImpl implements SystemSetService{
    @Autowired
    private SystemSetDao systemSetDao;
    
    private volatile Properties properties;
    
    @Override
    public String getProperty(String property) {
        return getProperties().getProperty(property);
    }
    
    private synchronized Properties getProperties() {
    	if(properties == null) {
            synchronized (Properties.class) {
                if (properties == null) {
                    properties = new Properties();
                    List<SystemSet> propertyList = systemSetDao.loadAllProperty();
                    for (SystemSet item : propertyList) {
                    	String key = Objects.toString(item.getKey(),"");
                    	String val = Objects.toString(item.getVal(),"");
                    	
                        properties.put(key,val);
                    }
                }
            }
        }
        return properties;
    }
    
    @Override
    public List<SystemSet> findList(String key, String val) {
        return this.systemSetDao.findList(key,val);
    }

    @Override
    public void setProperty(String id,String key, String val,String comment) {
        StringUtils.defaultIfEmpty(id, "");
        StringUtils.defaultIfEmpty(key, "");
        StringUtils.defaultIfEmpty(val, "");
        StringUtils.defaultIfEmpty(comment, "");
        this.getProperties().setProperty(key, val);
        this.systemSetDao.update(id,key,val,comment);
    }
    
    
}
