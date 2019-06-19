package com.jm.bean;

import java.io.Serializable;

public class Reverse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String createTime;
	private Integer lane;
	private String frontPic;
	private String carNum;
	private String stationName;
	private String snapTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getLane() {
		return lane;
	}
	public void setLane(Integer lane) {
		this.lane = lane;
	}
	public String getFrontPic() {
		return frontPic;
	}
	public void setFrontPic(String frontPic) {
		this.frontPic = frontPic;
	}
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
    public String getSnapTime() {
        return snapTime;
    }
    public void setSnapTime(String snapTime) {
        this.snapTime = snapTime;
    }
	
	
}
