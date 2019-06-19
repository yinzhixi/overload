package com.jm.bean;

import java.util.Date;

public class Weight {
    private String flowNo;

    private String deviceId;

    private String stationId;

    private String matchNo;

    private Byte lane;

    private Double sumWeight;

    private Integer axleCnt;

    private Integer axleGrpCnt;

    private String axleWeight;

    private String lAxleWeight;

    private String rAxleWeight;

    private String axleType;

    private String axleDis;

    private String axleGrpType;

    private Short length;

    private Byte speed;

    private Byte runState;

    private Byte reverse;

    private String attachmentNo;

    private String dateTime;

    private Date createTime;

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo == null ? null : flowNo.trim();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId == null ? null : stationId.trim();
    }

    public String getMatchNo() {
        return matchNo;
    }

    public void setMatchNo(String matchNo) {
        this.matchNo = matchNo == null ? null : matchNo.trim();
    }

    public Byte getLane() {
        return lane;
    }

    public void setLane(Byte lane) {
        this.lane = lane;
    }

    public Double getSumWeight() {
        return sumWeight;
    }

    public void setSumWeight(Double sumWeight) {
        this.sumWeight = sumWeight;
    }

    public Integer getAxleCnt() {
        return axleCnt;
    }

    public void setAxleCnt(Integer axleCnt) {
        this.axleCnt = axleCnt;
    }

    public Integer getAxleGrpCnt() {
        return axleGrpCnt;
    }

    public void setAxleGrpCnt(Integer axleGrpCnt) {
        this.axleGrpCnt = axleGrpCnt;
    }

    public String getAxleWeight() {
        return axleWeight;
    }

    public void setAxleWeight(String axleWeight) {
        this.axleWeight = axleWeight == null ? null : axleWeight.trim();
    }

    public String getlAxleWeight() {
        return lAxleWeight;
    }

    public void setlAxleWeight(String lAxleWeight) {
        this.lAxleWeight = lAxleWeight == null ? null : lAxleWeight.trim();
    }

    public String getrAxleWeight() {
        return rAxleWeight;
    }

    public void setrAxleWeight(String rAxleWeight) {
        this.rAxleWeight = rAxleWeight == null ? null : rAxleWeight.trim();
    }

    public String getAxleType() {
        return axleType;
    }

    public void setAxleType(String axleType) {
        this.axleType = axleType == null ? null : axleType.trim();
    }

    public String getAxleDis() {
        return axleDis;
    }

    public void setAxleDis(String axleDis) {
        this.axleDis = axleDis == null ? null : axleDis.trim();
    }

    public String getAxleGrpType() {
        return axleGrpType;
    }

    public void setAxleGrpType(String axleGrpType) {
        this.axleGrpType = axleGrpType == null ? null : axleGrpType.trim();
    }

    public Short getLength() {
        return length;
    }

    public void setLength(Short length) {
        this.length = length;
    }

    public Byte getSpeed() {
        return speed;
    }

    public void setSpeed(Byte speed) {
        this.speed = speed;
    }

    public Byte getRunState() {
        return runState;
    }

    public void setRunState(Byte runState) {
        this.runState = runState;
    }

    public Byte getReverse() {
        return reverse;
    }

    public void setReverse(Byte reverse) {
        this.reverse = reverse;
    }

    public String getAttachmentNo() {
        return attachmentNo;
    }

    public void setAttachmentNo(String attachmentNo) {
        this.attachmentNo = attachmentNo == null ? null : attachmentNo.trim();
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime == null ? null : dateTime.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}