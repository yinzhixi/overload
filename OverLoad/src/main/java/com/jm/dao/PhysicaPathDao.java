package com.jm.dao;


import com.jm.bean.PhysicaPath;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PhysicaPathDao {
    int deleteByPrimaryKey(Integer id);

    int insert(PhysicaPath record);

    int insertSelective(PhysicaPath record);

    PhysicaPath selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PhysicaPath record);

    int updateByPrimaryKey(PhysicaPath record);

    List<PhysicaPath> selectList(@Param("pathname")String pathname,@Param("pathCode")String pathCode);
}