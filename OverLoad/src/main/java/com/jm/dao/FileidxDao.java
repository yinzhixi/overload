package com.jm.dao;


import com.jm.bean.Fileidx;
import org.apache.ibatis.annotations.Param;

public interface FileidxDao {
    int deleteByPrimaryKey(String id);

    int insert(Fileidx record);

    int insertSelective(Fileidx record);

    Fileidx selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Fileidx record);

    int updateByPrimaryKey(Fileidx record);

    Fileidx getFileidx(@Param("relCode") String relCode, @Param("fileType")String fileType);

    int updateFileidx(@Param("relcode") String relcode, @Param("filetype")String filetype, @Param("filepath")String filepath);
}