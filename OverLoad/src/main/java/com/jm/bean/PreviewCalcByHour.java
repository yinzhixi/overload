package com.jm.bean;

import java.io.Serializable;

public class PreviewCalcByHour implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer hour;
    private Integer num;
    public Integer getHour() {
        return hour;
    }
    public void setHour(Integer hour) {
        this.hour = hour;
    }
    public Integer getNum() {
        return num;
    }
    public void setNum(Integer num) {
        this.num = num;
    }
    
}
