package com.jm.websocket;

import com.jm.service.StaticWeightService;

public interface OnDataCallback {
    void onVehicleData(String json);
    void onImgData(String imgFileName);
    void onSnapData(String json);
    
    void onFrontImgComplete(String jsonVehicle,String jsonImg);
    void onAllImgComplete(String jsonVehicle);
    void onSnapImgComplete(String jsonVehicle,String jsonSnapImg);
    void onStcData(String data, StaticWeightService staticWeightService);

}
