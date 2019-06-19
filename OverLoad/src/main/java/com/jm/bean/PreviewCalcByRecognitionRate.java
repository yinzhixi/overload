package com.jm.bean;

import java.io.Serializable;
import java.text.DecimalFormat;

import freemarker.template.utility.NumberUtil;

/**
 * 按超限率统计
 * @author Administrator
 *
 */
public class PreviewCalcByRecognitionRate implements Serializable{

    private static final long serialVersionUID = 1L;
    private Integer lane;//车道
    private Integer d_totalNum;//白天总数量
    private Integer d_num;//白天识别数量
    private String d_rate;//白天识别率
    private Integer n_totalNum;//夜晚总数量
    private Integer n_num;//夜晚识别数量
    private String n_rate;//夜晚识别率
    
    public Integer getLane() {
        return lane;
    }
    public void setLane(Integer lane) {
        this.lane = lane;
    }
    public Integer getD_totalNum() {
        return d_totalNum;
    }
    public void setD_totalNum(Integer d_totalNum) {
        this.d_totalNum = d_totalNum;
    }
    public Integer getD_num() {
        return d_num;
    }
    public void setD_num(Integer d_num) {
        this.d_num = d_num;
    }
    public Integer getN_totalNum() {
        return n_totalNum;
    }
    public void setN_totalNum(Integer n_totalNum) {
        this.n_totalNum = n_totalNum;
    }
    public Integer getN_num() {
        return n_num;
    }
    public void setN_num(Integer n_num) {
        this.n_num = n_num;
    }
    public String getD_rate() {
        DecimalFormat df = new DecimalFormat(".00");
        double total =  this.getD_totalNum()==null?0.0:(double)this.getD_totalNum();
        double num = this.getD_num()==null?0.0:(double)this.getD_num();
        if(total != 0){
            return df.format(num/total);
        }
        return "0";
    }
    public void setD_rate(String d_rate) {
        this.d_rate = d_rate;
    }
    public String getN_rate() {
        DecimalFormat df = new DecimalFormat(".00");
        double total =  this.getN_totalNum()==null?0.0:(double)this.getN_totalNum();
        double num = this.getN_num()==null?0.0:(double)this.getN_num();
        if(total != 0){
            return df.format(num/total);
        }
        return "0";
    }
    public void setN_rate(String n_rate) {
        this.n_rate = n_rate;
    }
    
}
