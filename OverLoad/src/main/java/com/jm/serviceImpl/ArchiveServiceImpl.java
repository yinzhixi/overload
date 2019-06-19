package com.jm.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.transaction.Transactional;

import org.apache.tools.ant.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jm.dao.ArchiveDao;
import com.jm.service.ArchiveService;

@Service
@Transactional
public class ArchiveServiceImpl implements ArchiveService{
    @Autowired
    private ArchiveDao archiveDao;
    
    @Override
    public void archiveExpireData(Date time) {
        String date = DateUtils.format(time, "yyyy-MM-dd");
        List<Map<String,Object>> yearMonths = this.archiveDao.findYearMonths();
        if(yearMonths != null && yearMonths.size() > 0) {
            for (Map<String, Object> item : yearMonths) {
                String year = Objects.toString(item.get("year"),"0000");
                String month = Objects.toString(item.get("month"),"00");
                year = String.format("%04d", Integer.parseInt(year));
                month = String.format("%02d", Integer.parseInt(month));
                
                int tableCnt = this.archiveDao.existsTable(year,month);
                if(tableCnt <= 0) {
                    this.archiveDao.createTable(year,month);
                }
                this.archiveDao.copyData(year,month,date);
                this.archiveDao.deleteDate(year,month,date);
            }
        }
        
    }
}
