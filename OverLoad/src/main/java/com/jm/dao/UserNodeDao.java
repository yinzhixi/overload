package com.jm.dao;

import com.jm.bean.UserNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserNodeDao {


    List<UserNode> getByEmpId(@Param("empId") String empId);

    void add(UserNode userNode);

    void del(@Param("empId") String empId, @Param("nodeCode") String nodeCode);


    int userNodeCount(@Param("empId") String empId, @Param("nodeCode") String nodeCode);


    void cancleAllNode(@Param("empId") String empId);

    void assignAllNode(@Param("empId") String empId);


    //通过角色id删除所属用户
    void delUserNode(@Param("roleId") String nodeCode);
}
