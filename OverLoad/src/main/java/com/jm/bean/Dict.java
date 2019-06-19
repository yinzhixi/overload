package com.jm.bean;

import java.io.Serializable;

public class Dict implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    Integer id;
    String type;
    String code;
    String name;
    String brief;
    Integer sq;
    String createTime;
    String spare1;
    String spare2;
    String spare3;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBrief() {
        return brief;
    }
    public void setBrief(String brief) {
        this.brief = brief;
    }
    public Integer getSq() {
        return sq;
    }
    public void setSq(Integer sq) {
        this.sq = sq;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getSpare1() {
        return spare1;
    }
    public void setSpare1(String spare1) {
        this.spare1 = spare1;
    }
    public String getSpare2() {
        return spare2;
    }
    public void setSpare2(String spare2) {
        this.spare2 = spare2;
    }
    public String getSpare3() {
        return spare3;
    }
    public void setSpare3(String spare3) {
        this.spare3 = spare3;
    }
    
}
