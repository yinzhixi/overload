package com.jm.bean;

import java.io.Serializable;
import java.sql.Timestamp;

//预检明细表
public class PreviewRecord implements Serializable {

    /**
    * 
    */
    private static final long serialVersionUID = 1L;
    private Integer recordId;
    private Integer empId;
    private Timestamp handleTime;
    private Integer previewId;// 预检序列号
    private String dateTime;// 称重系统检测车的时间
    private Integer lane;// 车道
    private String vehicleType;// 车型
    private Integer speed;// 车速

    private Integer axleCnt;// 轴数
    private Integer axleDis;// 轴距
    private Integer direction;// 车向{1+正向} {0- 反向}
    private double sumWeight;// 总重已经放大100倍
    private double overRage;// 超出多少吨
    private String carNum;// 车牌号

    private Integer length1;// 长（备用字段）
    private Integer width2;// 宽（备用字段）
    private Integer height3;// 高（备用字段）
    private String frontPic;// 26、车前图片文件
    private String backPic;// 车后图片文件
    private String sidePic;// 车身图片
    private String upPic;// 顶部图片
    private String picbak;// 图片备用字段
    private String movie;// 视频
    private Integer overLoadRate;// 穿过来的超限率

    private Integer recognition;// 1 识别车牌 0 未识别车牌

    private String distingByday;// 白天识别率06：00——18：00
    private String distingByNight;// 夜晚识别率18：00——06：00
    private String allDisting;// 全天识别率

    private Integer countByDay;// 白天识别数量
    private Integer countByNight;// 夜晚识别数量
    private Integer countByAllDay;// 总数量

    private Integer venifyPreview;// 审核标识 0为未审核 1为审核已通过
    String venifyTime;
    double limitWeight;// 限重

    private Ton ton;// 吨位外键
    private Integer tonId;
    private String station;// 站点外键
    private String stationMark;
    private OverLoadRate overRate;// 超限率外键
    private Integer overLoadId;
    private String createTime;

    private double axleWeight1;
    private double axleWeight2;
    private double axleWeight3;
    private double axleWeight4;
    private double axleWeight5;
    private double axleWeight6;
    private double axleWeight7;
    private double axleWeight8;
    private String checkNo;
    
    private String stationId;
    private String stationIp;
    private String stationShort;
    private String screenPic;
    private boolean snapScreen;
    private String relCode;

    public String getRelCode() {
        return relCode;
    }

    public void setRelCode(String relCode) {
        this.relCode = relCode;
    }

    public String getStationMark() {
        return stationMark;
    }

    public void setStationMark(String stationMark) {
        this.stationMark = stationMark;
    }

    /**
	 * @author: yinzhixi       
	 * @created: 2018年12月25日 下午5:51:37 
	 * @return: Integer 
	 */
	
	public Integer getTonId() {
		return tonId;
	}



	
	/**     
	 * @author: yinzhixi       
	 * @created 2018年12月25日 下午5:51:37         
	 * @param tonId   
	 */
	public void setTonId(Integer tonId) {
		this.tonId = tonId;
	}



	/**    
	 * @author: yinzhixi       
	 * @created: 2018年12月25日 下午3:36:06 
	 * @return: Integer 
	 */
	
	public Integer getRecordId() {
		return recordId;
	}


	
	/**     
	 * @author: yinzhixi       
	 * @created 2018年12月25日 下午3:36:06         
	 * @param recordId   
	 */
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}


	/**    
	 * @author: yinzhixi       
	 * @created: 2018年12月25日 下午3:26:08 
	 * @return: Integer 
	 */
	
	public Integer getEmpId() {
		return empId;
	}

	
	/**     
	 * @author: yinzhixi       
	 * @created 2018年12月25日 下午3:26:08         
	 * @param empId   
	 */
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	
	/**    
	 * @author: yinzhixi       
	 * @created: 2018年12月25日 下午3:26:08 
	 * @return: Timestamp 
	 */
	
	public Timestamp getHandleTime() {
		return handleTime;
	}

	
	/**     
	 * @author: yinzhixi       
	 * @created 2018年12月25日 下午3:26:08         
	 * @param handleTime   
	 */
	public void setHandleTime(Timestamp handleTime) {
		this.handleTime = handleTime;
	}

	public boolean isSnapScreen() {
        return snapScreen;
    }

    public void setSnapScreen(boolean snapScreen) {
        this.snapScreen = snapScreen;
    }

    public String getScreenPic() {
        return screenPic;
    }

    public void setScreenPic(String screenPic) {
        this.screenPic = screenPic;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationIp() {
        return stationIp;
    }

    public void setStationIp(String stationIp) {
        this.stationIp = stationIp;
    }

    public String getStationShort() {
        return stationShort;
    }

    public void setStationShort(String stationShort) {
        this.stationShort = stationShort;
    }

    public String getCheckNo() {
        return checkNo;
    }

    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo;
    }

    public String getVenifyTime() {
        return venifyTime;
    }

    public void setVenifyTime(String venifyTime) {
        this.venifyTime = venifyTime;
    }

    public Integer getOverLoadId() {
        return overLoadId;
    }

    public void setOverLoadId(Integer overLoadId) {
        this.overLoadId = overLoadId;
    }

    public double getLimitWeight() {
        return limitWeight;
        // limitWeight;
    }

    public void setLimitWeight(double limitWeight) {
        this.limitWeight = limitWeight;
    }

    public Integer getVenifyPreview() {
        return venifyPreview;
    }

    public void setVenifyPreview(Integer venifyPreview) {
        this.venifyPreview = venifyPreview;
    }

    public String getDistingByday() {
        return distingByday;
    }

    public void setDistingByday(String distingByday) {
        this.distingByday = distingByday;
    }

    public String getDistingByNight() {
        return distingByNight;
    }

    public void setDistingByNight(String distingByNight) {
        this.distingByNight = distingByNight;
    }

    public String getAllDisting() {
        return allDisting;
    }

    public void setAllDisting(String allDisting) {
        this.allDisting = allDisting;
    }

    public Integer getCountByDay() {
        return countByDay;
    }

    public void setCountByDay(Integer countByDay) {
        this.countByDay = countByDay;
    }

    public Integer getCountByNight() {
        return countByNight;
    }

    public void setCountByNight(Integer countByNight) {
        this.countByNight = countByNight;
    }

    public Integer getCountByAllDay() {
        return countByAllDay;
    }

    public void setCountByAllDay(Integer countByAllDay) {
        this.countByAllDay = countByAllDay;
    }

    public Integer getRecognition() {
        return recognition;
    }

    public void setRecognition(Integer recognition) {
        this.recognition = recognition;
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

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public Integer getLength1() {
        return length1;
    }

    public void setLength1(Integer length1) {
        this.length1 = length1;
    }

    public Integer getWidth2() {
        return width2;
    }

    public void setWidth2(Integer width2) {
        this.width2 = width2;
    }

    public Integer getHeight3() {
        return height3;
    }

    public void setHeight3(Integer height3) {
        this.height3 = height3;
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

    public double getOverRage() {
        return overRage;
    }

    public void setOverRage(double overRage) {
        this.overRage = overRage;
    }

    public Ton getTon() {
        return ton;
    }

    public void setTon(Ton ton) {
        this.ton = ton;
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

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public OverLoadRate getOverRate() {
        return overRate;
    }

    public void setOverRate(OverLoadRate overRate) {
        this.overRate = overRate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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
    
    
}
