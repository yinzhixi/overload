package com.jm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ArchiveDao {

    List<Map<String,Object>> findYearMonths();

    int existsTable(@Param("year") String year, 
            @Param("month") String month);

    void createTable(@Param("year") String year, 
            @Param("month") String month);

    void copyData(@Param("year") String year, 
            @Param("month") String month,  
            @Param("date") String date);

    void deleteDate(@Param("year") String year, 
            @Param("month") String month, 
            @Param("date") String date);
    
}
