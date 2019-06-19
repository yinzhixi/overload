package com.jm.bean;

public class PreviewPre {
	
	private Integer previewId;//预检序列号
    private String dateTime;//称重系统检测车的时间
    private Integer lane;//车道
    private String vehicleType;//车型
    private Integer speed;//车速
    
    private Integer axleCnt;//轴数
    private Integer axleDis;//轴距
    private Integer direction;//车向{1+正向} {0- 反向}
    private String directionDesc;
    private double sumWeight;//总重已经放大100倍
    private double overRage;//超出多少吨
    private String carNum;//车牌号
    
    private String frontPic;//26、车前图片文件
    private String backPic;//车后图片文件
    private String sidePic;
    private String upPic;
    private String picbak;//图片备用字段
    private String movie;//视频
    private Integer overLoadRate;//穿过来的超限率
    String limitWeight;//限重    
    private String platePic;
    private String vedioPath;
    private String screenPic;
    private Boolean snapScreen = false;
    private String relCode;//图片编号

	public String getRelCode() {
		return relCode;
	}

	public void setRelCode(String relCode) {
		this.relCode = relCode;
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
    public String getPlatePic() {
        return platePic;
    }
    public void setPlatePic(String platePic) {
        this.platePic = platePic;
    }
public String getDirectionDesc() {
        return directionDesc;
    }
    public void setDirectionDesc(String directionDesc) {
        this.directionDesc = directionDesc;
    }
public String getLimitWeight() {
        return limitWeight;
    }
    public void setLimitWeight(String limitWeight) {
        this.limitWeight = limitWeight;
    }

private Integer recognition;//1 识别车牌  0 未识别车牌
   
   private String station;
   
   private String overLoadId;
   
   
   public String getOverLoadId() {
 	return overLoadId;
 }
 public void setOverLoadId(String overLoadId) {
 	this.overLoadId = overLoadId;
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
	   // return this.formatNum(sumWeight/1000);
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

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}
    public String getSidePic() {
        return sidePic;
    }
    public void setSidePic(String sidePic) {
        this.sidePic = sidePic;
    }
    public String getUpPic() {
        return upPic;
    }
    public void setUpPic(String upPic) {
        this.upPic = upPic;
    }

	

}
