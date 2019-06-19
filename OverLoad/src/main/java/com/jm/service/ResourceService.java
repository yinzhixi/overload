package com.jm.service;

import java.util.List;

import com.jm.bean.Resource;

public interface ResourceService {
    
    /**
     * 首页展示
     * @return
     */
    public List<Resource> getAll();
}

