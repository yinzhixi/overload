package com.jm.bean;

import java.io.Serializable;

/**
 * 按超限率统计
 * @author Administrator
 *
 */
public class PreviewCalcByOverPercent implements Serializable{

    private static final long serialVersionUID = 1L;
    private Integer overLoadId;//超限率ID
    private Integer num;//该超限率数量
    private String name;
    
    public Integer getOverLoadId() {
        return overLoadId;
    }
    public void setOverLoadId(Integer overLoadId) {
        this.overLoadId = overLoadId;
    }
    public Integer getNum() {
        return num;
    }
    public void setNum(Integer num) {
        this.num = num;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
}
