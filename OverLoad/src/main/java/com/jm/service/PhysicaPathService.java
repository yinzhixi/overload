package com.jm.service;

import com.jm.bean.PhysicaPath;

import java.util.List;

public interface PhysicaPathService {
    List<PhysicaPath> findList(String pathname, String pathCode);

    int updatePath(String id, String pathname, String pathCode, String status, String path);
}
