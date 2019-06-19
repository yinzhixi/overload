package com.jm.bean;

import java.util.Date;

public class Camera {
    private String flowNo;

    private String deviceId;

    private Byte cameraDir;

    private String stationId;

    private String matchNo;

    private String plateNum;

    private Byte entireBelieve;

    private Byte plateType;

    private Short plateColor;

    private Byte colorDepth;

    private Byte color;

    private Integer length;

    private Integer width;

    private Integer height;

    private Byte lane;

    private Byte vehicleType;

    private Integer speed;

    private Byte direction;

    private Short vehicleLogoRecog;

    private Byte illegalType;

    private String attachmentNo;

    private String dateTime;

    private Byte picNum;

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

    public Byte getCameraDir() {
        return cameraDir;
    }

    public void setCameraDir(Byte cameraDir) {
        this.cameraDir = cameraDir;
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

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum == null ? null : plateNum.trim();
    }

    public Byte getEntireBelieve() {
        return entireBelieve;
    }

    public void setEntireBelieve(Byte entireBelieve) {
        this.entireBelieve = entireBelieve;
    }

    public Byte getPlateType() {
        return plateType;
    }

    public void setPlateType(Byte plateType) {
        this.plateType = plateType;
    }

    public Short getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(Short plateColor) {
        this.plateColor = plateColor;
    }

    public Byte getColorDepth() {
        return colorDepth;
    }

    public void setColorDepth(Byte colorDepth) {
        this.colorDepth = colorDepth;
    }

    public Byte getColor() {
        return color;
    }

    public void setColor(Byte color) {
        this.color = color;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Byte getLane() {
        return lane;
    }

    public void setLane(Byte lane) {
        this.lane = lane;
    }

    public Byte getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Byte vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Byte getDirection() {
        return direction;
    }

    public void setDirection(Byte direction) {
        this.direction = direction;
    }

    public Short getVehicleLogoRecog() {
        return vehicleLogoRecog;
    }

    public void setVehicleLogoRecog(Short vehicleLogoRecog) {
        this.vehicleLogoRecog = vehicleLogoRecog;
    }

    public Byte getIllegalType() {
        return illegalType;
    }

    public void setIllegalType(Byte illegalType) {
        this.illegalType = illegalType;
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

    public Byte getPicNum() {
        return picNum;
    }

    public void setPicNum(Byte picNum) {
        this.picNum = picNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}