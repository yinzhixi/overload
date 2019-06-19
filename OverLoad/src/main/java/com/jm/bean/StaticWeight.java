package com.jm.bean;

public class StaticWeight {
    private String staticid;

    private String carnum;

    private String cargoname;

    private String specificationtype;

    private String forwardunit;

    private String trafficunit;

    private String driver;

    private String relcode;

    private String stationmark;

    private String remark;

    private Double weight;
    private Integer lane;
    private String vehicleType;
    private String createTime;
    private String updateTime;

    public Integer getLane() {
        return lane;
    }

    public void setLane(Integer lane) {
        this.lane = lane;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getStaticid() {
        return staticid;
    }

    public void setStaticid(String staticid) {
        this.staticid = staticid == null ? null : staticid.trim();
    }

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum == null ? null : carnum.trim();
    }

    public String getCargoname() {
        return cargoname;
    }

    public void setCargoname(String cargoname) {
        this.cargoname = cargoname == null ? null : cargoname.trim();
    }

    public String getSpecificationtype() {
        return specificationtype;
    }

    public void setSpecificationtype(String specificationtype) {
        this.specificationtype = specificationtype == null ? null : specificationtype.trim();
    }

    public String getForwardunit() {
        return forwardunit;
    }

    public void setForwardunit(String forwardunit) {
        this.forwardunit = forwardunit == null ? null : forwardunit.trim();
    }

    public String getTrafficunit() {
        return trafficunit;
    }

    public void setTrafficunit(String trafficunit) {
        this.trafficunit = trafficunit == null ? null : trafficunit.trim();
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver == null ? null : driver.trim();
    }

    public String getRelcode() {
        return relcode;
    }

    public void setRelcode(String relcode) {
        this.relcode = relcode == null ? null : relcode.trim();
    }

    public String getStationmark() {
        return stationmark;
    }

    public void setStationmark(String stationmark) {
        this.stationmark = stationmark == null ? null : stationmark.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}