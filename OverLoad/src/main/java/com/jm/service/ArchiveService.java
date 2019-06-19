package com.jm.service;

import java.util.Date;

public interface ArchiveService {
    /**
     * 复制过期数据到历史表
     * @param time 过期时间节点
     */
    void archiveExpireData(Date time);

}
