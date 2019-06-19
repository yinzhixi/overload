package com.jm.websocket;

import java.util.Date;

public class SnapImg {
    
    private Number id;
    private String frontPic;
    private String carNum;
    private String stationName;
    private Integer lane;
    private String snapTime;
    private Date createTime;
    public Number getId() {
        return id;
    }
    public void setId(Number id) {
        this.id = id;
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
    public Integer getLane() {
        return lane;
    }
    public void setLane(Integer lane) {
        this.lane = lane;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getSnapTime() {
        return snapTime;
    }
    public void setSnapTime(String snapTime) {
        this.snapTime = snapTime;
    }
    
    
}
