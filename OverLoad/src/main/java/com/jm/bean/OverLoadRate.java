package com.jm.bean;

import java.io.Serializable;

//超限率表
public class OverLoadRate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer overLoadId;
	private String overLoadRateRegion;//超限率区间	
	 private Integer overNum;//该超限率数量
	 private String percent;//比例 %
	public Integer getOverLoadId() {
		return overLoadId;
	}
	public void setOverLoadId(Integer overLoadId) {
		this.overLoadId = overLoadId;
	}
	public String getOverLoadRateRegion() {
		return overLoadRateRegion;
	}
	public void setOverLoadRateRegion(String overLoadRateRegion) {
		this.overLoadRateRegion = overLoadRateRegion;
	}
	public Integer getOverNum() {
		return overNum;
	}
	public void setOverNum(Integer overNum) {
		this.overNum = overNum;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	
	 
	

	
}
