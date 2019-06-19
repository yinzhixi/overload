package com.jm.bean;

import java.io.Serializable;

/**
 * 按吨位分类报表
 * @author Administrator
 *
 */
public class PreviewCalcByWeight implements Serializable{

    private static final long serialVersionUID = 1L;
    private String lane;//车道
    private String ton;//吨位
    private Integer tonNum;//该吨位数量
    private String percent;//比例 %
    public String getLane() {
        return lane;
    }
    public void setLane(String lane) {
        this.lane = lane;
    }
    public String getTon() {
        return ton;
    }
    public void setTon(String ton) {
        this.ton = ton;
    }
    public Integer getTonNum() {
        return tonNum;
    }
    public void setTonNum(Integer tonNum) {
        this.tonNum = tonNum;
    }
    public String getPercent() {
        return percent;
    }
    public void setPercent(String percent) {
        this.percent = percent;
    }
    
}
