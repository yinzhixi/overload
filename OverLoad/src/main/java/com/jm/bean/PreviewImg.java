package com.jm.bean;

import java.io.Serializable;

//预检明细图片表
public class PreviewImg implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String img;
	private String creatTime;
	
	private Integer previewId;
	private String carNum;
	private String snapTime;
	
	
	public Integer getPreviewId() {
		return previewId;
	}
	public void setPreviewId(Integer previewId) {
		this.previewId = previewId;
	}
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	public String getSnapTime() {
		return snapTime;
	}
	public void setSnapTime(String snapTime) {
		this.snapTime = snapTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}
	

}
