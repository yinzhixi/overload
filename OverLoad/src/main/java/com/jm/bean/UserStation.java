package com.jm.bean;

public class UserStation {

    private Integer userId;
    private Integer stationId;
    private String stationCode;

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public UserStation() {
    }

    public UserStation(Integer userId, Integer stationId, String stationCode) {
        this.userId = userId;
        this.stationId = stationId;
        this.stationCode = stationCode;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }
}
