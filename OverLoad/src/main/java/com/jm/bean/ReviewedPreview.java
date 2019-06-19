package com.jm.bean;

import java.io.Serializable;

public class ReviewedPreview implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer previewId;//预检序列号
	
    private String dateTime;//称重系统检测车的时间
    private Integer lane;//车道
    private String vehicleType;//车型
    private Integer speed;//车速
    
    private Integer axleCnt;//轴数   
    private Integer axleDis;//轴距   
    private Integer direction;//车向{1+正向} {0- 反向}
    private double sumWeight;//总重已经放大100倍
    private String carNum;//车牌号
    
    private String frontPic;//26、车前图片文件
    private String backPic;//车后图片文件
    private String picbak;//图片备用字段
    
    private double overRage;//超出多少吨  
    private String movie;//视频
    private Integer overLoadRate;//穿过来的超限率
   private Integer recognition;//1 识别车牌  0 未识别车牌
   
   	private Integer overLoadId;

    private double axleWeight1;
    private double axleWeight2;
    private double axleWeight3;
    private double axleWeight4;
    private double axleWeight5;
    private double axleWeight6;
    private double axleWeight7;
    private double axleWeight8;
    private String checkNo;
    private String platePic;
    private String vedioPath;
    private String screenPic;
    private Boolean snapScreen = false;
    private Integer stationId;

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getPlatePic() {
        return platePic;
    }
    public void setPlatePic(String platePic) {
        this.platePic = platePic;
    }
    public String getVedioPath() {
        return vedioPath;
    }
    public void setVedioPath(String vedioPath) {
        this.vedioPath = vedioPath;
    }
    public String getScreenPic() {
        return screenPic;
    }
    public void setScreenPic(String screenPic) {
        this.screenPic = screenPic;
    }
    public Boolean getSnapScreen() {
        return snapScreen;
    }
    public void setSnapScreen(Boolean snapScreen) {
        this.snapScreen = snapScreen;
    }
    public double getAxleWeight1() {
        return axleWeight1;
    }
    public void setAxleWeight1(double axleWeight1) {
        this.axleWeight1 = axleWeight1;
    }
    public double getAxleWeight2() {
        return axleWeight2;
    }
    public void setAxleWeight2(double axleWeight2) {
        this.axleWeight2 = axleWeight2;
    }
    public double getAxleWeight3() {
        return axleWeight3;
    }
    public void setAxleWeight3(double axleWeight3) {
        this.axleWeight3 = axleWeight3;
    }
    public double getAxleWeight4() {
        return axleWeight4;
    }
    public void setAxleWeight4(double axleWeight4) {
        this.axleWeight4 = axleWeight4;
    }
    public double getAxleWeight5() {
        return axleWeight5;
    }
    public void setAxleWeight5(double axleWeight5) {
        this.axleWeight5 = axleWeight5;
    }
    public double getAxleWeight6() {
        return axleWeight6;
    }
    public void setAxleWeight6(double axleWeight6) {
        this.axleWeight6 = axleWeight6;
    }
    public double getAxleWeight7() {
        return axleWeight7;
    }
    public void setAxleWeight7(double axleWeight7) {
        this.axleWeight7 = axleWeight7;
    }
    public double getAxleWeight8() {
        return axleWeight8;
    }
    public void setAxleWeight8(double axleWeight8) {
        this.axleWeight8 = axleWeight8;
    }
    public String getCheckNo() {
        return checkNo;
    }
    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo;
    }
public Integer getOverLoadId() {
		return overLoadId;
	}
	public void setOverLoadId(Integer overLoadId) {
		this.overLoadId = overLoadId;
	}
private String station;
private String enforcement;
private String enforcementTwo;

    public String getEnforcementTwo() {
        return enforcementTwo;
    }

    public void setEnforcementTwo(String enforcementTwo) {
        this.enforcementTwo = enforcementTwo;
    }

    public String getEnforcement() {
        return enforcement;
    }

    public void setEnforcement(String enforcement) {
        this.enforcement = enforcement;
    }

    String limitWeight;//限重

public String getLimitWeight() {
     return limitWeight;
 }
 public void setLimitWeight(String limitWeight) {
     this.limitWeight = limitWeight;
 }
   
   
// @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
  // @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
   private String venifyTime;
  
   
	
public String getVenifyTime() {
	return venifyTime;
}
public void setVenifyTime(String venifyTime) {
	this.venifyTime = venifyTime;
}
public String getStation() {
	return station;
}
public void setStation(String station) {
	this.station = station;
}

public Integer getPreviewId() {
	return previewId;
}
public void setPreviewId(Integer previewId) {
	this.previewId = previewId;
}
public String getDateTime() {
	return dateTime;
}
public void setDateTime(String dateTime) {
	this.dateTime = dateTime;
}
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
public Integer getSpeed() {
	return speed;
}
public void setSpeed(Integer speed) {
	this.speed = speed;
}
public Integer getAxleCnt() {
	return axleCnt;
}
public void setAxleCnt(Integer axleCnt) {
	this.axleCnt = axleCnt;
}
public Integer getAxleDis() {
	return axleDis;
}
public void setAxleDis(Integer axleDis) {
	this.axleDis = axleDis;
}
public Integer getDirection() {
	return direction;
}
public void setDirection(Integer direction) {
	this.direction = direction;
}
public double getSumWeight() {
	return sumWeight;
}
public void setSumWeight(double sumWeight) {
	this.sumWeight = sumWeight;
}
public double getOverRage() {
	return overRage;
}
public void setOverRage(double overRage) {
	this.overRage = overRage;
}
public String getCarNum() {
	return carNum;
}
public void setCarNum(String carNum) {
	this.carNum = carNum;
}
public String getFrontPic() {
	return frontPic;
}
public void setFrontPic(String frontPic) {
	this.frontPic = frontPic;
}
public String getBackPic() {
	return backPic;
}
public void setBackPic(String backPic) {
	this.backPic = backPic;
}
public String getPicbak() {
	return picbak;
}
public void setPicbak(String picbak) {
	this.picbak = picbak;
}
public String getMovie() {
	return movie;
}
public void setMovie(String movie) {
	this.movie = movie;
}
public Integer getOverLoadRate() {
	return overLoadRate;
}
public void setOverLoadRate(Integer overLoadRate) {
	this.overLoadRate = overLoadRate;
}
public Integer getRecognition() {
	return recognition;
}
public void setRecognition(Integer recognition) {
	this.recognition = recognition;
}
   
   

}
