package com.jm.bean;

import java.io.Serializable;

/**
 * 高速超限统计
 * @author Administrator
 *
 */
public class PreviewCalc implements Serializable{
    private static final long serialVersionUID = 1L;
    private String date;//日期
    private String vehicle;//车型
    private Integer totalNum;//总数
    private Integer overNum;//超限总数
    private String vehicleTypeName;
    
    
	/**    
	 * @author: yinzhixi       
	 * @created: 2019年1月7日 上午9:53:04 
	 * @return: String 
	 */
	
	public String getVehicleTypeName() {
		return vehicleTypeName;
	}
	
	/**     
	 * @author: yinzhixi       
	 * @created 2019年1月7日 上午9:53:04         
	 * @param vehicleTypeName   
	 */
	public void setVehicleTypeName(String vehicleTypeName) {
		this.vehicleTypeName = vehicleTypeName;
	}
	public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getVehicle() {
        return vehicle;
    }
    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }
    public Integer getTotalNum() {
        return totalNum;
    }
    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }
    public Integer getOverNum() {
        return overNum;
    }
    public void setOverNum(Integer overNum) {
        this.overNum = overNum;
    }}
