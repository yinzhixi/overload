package com.jm.bean;

import java.io.Serializable;

public class Car implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer outLane;//出高速车道号
	private String goStation;//进站
	private String outStation;//出站
	private String goTime;//进站时间
	private String outTime;//出站时间
	private String raodCard;//道路运输证号
	private String agencyCard;//发证机构
	private String vinNumber;//车架号
	private String licenseNumber;//许可证号
	private String ownerName;//业主名称
	private String ownerAddress;//业户地址
	private String telephone;//电话
	private Integer status;//状态
	private String carNum;//车辆信息外键
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOutLane() {
		return outLane;
	}
	public void setOutLane(Integer outLane) {
		this.outLane = outLane;
	}
	public String getGoStation() {
		return goStation;
	}
	public void setGoStation(String goStation) {
		this.goStation = goStation;
	}
	public String getOutStation() {
		return outStation;
	}
	public void setOutStation(String outStation) {
		this.outStation = outStation;
	}
	public String getGoTime() {
		return goTime;
	}
	public void setGoTime(String goTime) {
		this.goTime = goTime;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	public String getRaodCard() {
		return raodCard;
	}
	public void setRaodCard(String raodCard) {
		this.raodCard = raodCard;
	}
	public String getAgencyCard() {
		return agencyCard;
	}
	public void setAgencyCard(String agencyCard) {
		this.agencyCard = agencyCard;
	}
	public String getVinNumber() {
		return vinNumber;
	}
	public void setVinNumber(String vinNumber) {
		this.vinNumber = vinNumber;
	}
	public String getLicenseNumber() {
		return licenseNumber;
	}
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getOwnerAddress() {
		return ownerAddress;
	}
	public void setOwnerAddress(String ownerAddress) {
		this.ownerAddress = ownerAddress;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	
}
