package com.jm.dao;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.jm.bean.Resource;
public interface ResourceDao {
    
    /**
     * 展示
     */
    public List<Resource> getAll();
    
    
    List<Resource> getAllRoles(@Param("roles")List list);

}