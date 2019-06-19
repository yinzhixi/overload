package com.jm.schedule;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jm.service.ArchiveService;
import com.jm.service.SystemSetService;
import com.jm.serviceImpl.SystemProperties;

@Component
public class ArchiveSchedule {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private ArchiveService archiveService;

    @Autowired
    private SystemSetService systemSetService;
    
    /**
     * 数据归档计划任务
     * 
     */
    //@Scheduled(cron = "0/59 * * * * ?")
    @Scheduled(cron = "0 0 0 * * ?")
    public void execute() {
        int retain_days = NumberUtils.toInt(systemSetService.getProperty(SystemProperties.ARCHIVE_RETAIN_DAYS),30);
        System.out.println("run archive"+retain_days);
        logger.debug("开始执行归档任务，实时数据保留天数：" + retain_days);
        
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -retain_days);
        
        Date sDate = new Date();
        //复制过期数据到历史表
        this.archiveService.archiveExpireData(calendar.getTime());
        
        Date eDate = new Date();
        logger.debug("归档任务执行结束，用时："+(eDate.getTime() - sDate.getTime()));
    }
}
