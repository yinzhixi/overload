package com.jm.bean;

import java.io.Serializable;

import com.jm.util.NumberUtil;

/**
 * 视频轮放
 * @author Administrator
 *
 */
public class FlowView implements Serializable {
    private static final long serialVersionUID = 1L;
    Integer previewId;
    String imgUrl;//车头照片
    String backPic;//车辆后抓拍图片
    String carNum;//车牌号
    String carColor;//车颜色
    String totalWeight;//总重
    String limitWeight;//限重
    String overWeight;//超重
    String date;//时间
    Integer lane;//车道
    private String speed;
    private String station;
    Integer axleCnt;
    String overRage;
    Integer direction;//车向
    private String relCode;

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getBackPic() {
        return backPic;
    }

    public void setBackPic(String backPic) {
        this.backPic = backPic;
    }

    public String getRelCode() {
        return relCode;
    }

    public void setRelCode(String relCode) {
        this.relCode = relCode;
    }

    public Integer getPreviewId() {
        return previewId;
    }
    public void setPreviewId(Integer previewId) {
        this.previewId = previewId;
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public String getCarColor() {
        return carColor;
    }
    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }
    public String getTotalWeight() {
        double db = NumberUtil.parseDouble(totalWeight)/1000;
        return NumberUtil.formatNum(db);
    }
    
    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }
    public String getLimitWeight() {
        double db = NumberUtil.parseDouble(limitWeight)/1000;
        return NumberUtil.formatNum(db);
    }
    public void setLimitWeight(String limitWeight) {
        this.limitWeight = limitWeight;
    }
    public String getOverWeight() {
        double db = NumberUtil.parseDouble(overWeight)/1000;
        return NumberUtil.formatNum(db);
    }
    
    public String getOverRage() {
        double s = NumberUtil.parseDouble(totalWeight);
        double l = NumberUtil.parseDouble(limitWeight);
        double r = (s - l)/l*100;
        if(r < 0) {
            return "0%";
        }else {
            return NumberUtil.formatNum(r)+"%";
        }
    }
    public void setOverRage(String overRage) {
        this.overRage = overRage;
    }
    public void setOverWeight(String overWeight) {
        this.overWeight = overWeight;
    }
    
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getCarNum() {
        return carNum;
    }
    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }
    
    public Integer getLane() {
        return lane;
    }
    public void setLane(Integer lane) {
        this.lane = lane;
    }
    public Integer getAxleCnt() {
        return axleCnt;
    }
    public void setAxleCnt(Integer axleCnt) {
        this.axleCnt = axleCnt;
    }
    
}
