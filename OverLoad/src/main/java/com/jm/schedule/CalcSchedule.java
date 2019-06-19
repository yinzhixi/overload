package com.jm.schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.jm.dao.CalcScheduleDao;

@Component 
public class CalcSchedule {
    @Autowired
    private CalcScheduleDao calcDao;
    
    //0 0 0 * * ? 每天零点执行
    /*public CalcSchedule() {
        new Thread(new Runnable(){
            @Override
            public void run() {
                while(true) {
                    try {
                        start();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    */
    
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void start(){
        System.out.println("calc start !");
        
        String fromDateStr = calcDao.findFromDate();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        if(StringUtils.isEmpty(fromDateStr)) {
            fromDateStr = sf.format(new Date());
        }
        
        Date fromDate = null;
        try {
            fromDate = DateUtils.parseDate(fromDateStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date curDate = new Date();
        double distance = getDistanceOfTwoDate(fromDate,curDate);
        
        while(distance >= 0){
            this.calc_s_ton(fromDateStr);
            this.calc_s_over(fromDateStr);
            this.calc_s_recg(fromDateStr);
            this.calc_s_hour(fromDateStr);
            this.calc_s_cartype(fromDateStr);
            calcDao.updateFromDate(fromDateStr);
            fromDate = DateUtils.addDays(fromDate, 1);
            distance = getDistanceOfTwoDate(fromDate, curDate);
            fromDateStr = sf.format(fromDate);
        }
        
    }
    
    public double  getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = DateUtils.truncate(before, Calendar.DATE).getTime();
        long afterTime = DateUtils.truncate(after, Calendar.DATE).getTime();
        return (afterTime - beforeTime) / (1000d * 60 * 60 * 24);
    }
    
    /**
     * 
     */
    private void calc_s_ton(String fromDate){
        this.calcDao.del_calc_s_ton(fromDate);
        this.calcDao.calc_s_ton(fromDate);
    }
    
    /**
     * 
     */
    private void calc_s_over(String fromDate){
        this.calcDao.del_calc_s_over(fromDate);
        this.calcDao.calc_s_over(fromDate);
    }
    
    /**
     * 
     */
    private void calc_s_recg(String fromDate){
        this.calcDao.del_calc_s_recg(fromDate);
        this.calcDao.calc_s_recg(fromDate);
    }
    
    /**
     * 
     */
    private void calc_s_hour(String fromDate){
        this.calcDao.del_calc_s_hour(fromDate);
        this.calcDao.calc_s_hour(fromDate);
    }
    
    private void calc_s_cartype(String fromDate) {
        this.calcDao.del_calc_s_cartype(fromDate);
        this.calcDao.calc_s_cartype(fromDate);
        
    }
    
    
    
    
}
