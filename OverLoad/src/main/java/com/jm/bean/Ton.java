package com.jm.bean;

import java.io.Serializable;

//吨位表
public class Ton implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer tonId;
	private String tonRegion;//吨位区间
	
	public Integer getTonId() {
		return tonId;
	}
	public void setTonId(Integer tonId) {
		this.tonId = tonId;
	}
	public String getTonRegion() {
		return tonRegion;
	}
	public void setTonRegion(String tonRegion) {
		this.tonRegion = tonRegion;
	}
	
	
	

}
