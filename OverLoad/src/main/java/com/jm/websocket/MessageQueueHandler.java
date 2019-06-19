package com.jm.websocket;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import com.alibaba.fastjson.JSONObject;
import com.jm.bean.Camera;
import com.jm.bean.Preview;
import com.jm.bean.Weight;
import com.jm.service.PreviewService;
import com.jm.service.StaticWeightService;
import org.apache.commons.lang3.StringUtils;
import org.iot.client.common.Client;
import org.iot.client.common.ClientFactory;
import org.iot.client.common.ClientListener;
import org.iot.client.common.PackInterface;
import org.iot.client.common.decoder.Pack;
import org.iot.client.common.decoder.PackHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.jm.service.SystemSetService;
import com.jm.serviceImpl.SystemProperties;

public class MessageQueueHandler {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private ClientFactory clientFactory;
    private StaticWeightService staticWeightService;
    private PreviewService previewService;
    private CopyOnWriteArraySet<OnDataCallback> listeners = new CopyOnWriteArraySet<OnDataCallback>();
    private final CopyOnWriteArrayList<ConcurrentHashMap<String,Object>> recvVCache = new CopyOnWriteArrayList<ConcurrentHashMap<String,Object>>();
    //private final ConcurrentHashMap<String,Date> recvImgCache = new ConcurrentHashMap<String,Date>();
    
    /*
    private final ConcurrentHashMap<String,Object> recvVCache = new ConcurrentHashMap<String,Object>();*/
    private final long cacheTime = 10*60*1000;//两分钟
    
    public MessageQueueHandler( ClientFactory clientFactory) {

        this.clientFactory = clientFactory;
    }

    public StaticWeightService getStaticWeightService() {
        return staticWeightService;
    }

    public void setStaticWeightService(StaticWeightService staticWeightService) {
        this.staticWeightService = staticWeightService;
    }

    public PreviewService getPreviewService() {
        return previewService;
    }

    public void setPreviewService(PreviewService previewService) {
        this.previewService = previewService;
    }

    public void start() {
        this.startWeightQueueHandleThread();

        this.startCameraQueueHandleThread();
        this.startStcQueueHandleThread();
    }
    private void startStcQueueHandleThread() {
        new Thread(new Runnable(){
            Gson gson = new Gson();
            STCWeightDecoder decoder = new STCWeightDecoder();
            @Override
            public void run() {
                while(!Thread.interrupted()){
                    PackInterface pack = null;
                    boolean handleFlag = false;
                    Client client = clientFactory.getConnectMap().get(ClientService.stcClientId);

                    if(client != null && (pack = client.readQueuePoll()) != null){
                        try {
                            STCWeight weight = decoder.decode(pack, client.getClientId(), getPicPath());
                            String json = gson.toJson(weight);//new String(pack.getData(),"UTF-8");
                            for (OnDataCallback onDataCallback : listeners) {
                                onDataCallback.onStcData(json,staticWeightService);
                            }
                            log.debug(json);
                            handleFlag = true;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if(handleFlag == false) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }}).start();
    }
    private void startWeightQueueHandleThread(){
        new Thread(new Runnable(){
            @Override
            public void run() {
                Gson gson = new Gson();
                while(!Thread.interrupted()){
                    PackInterface pack = null;
                    boolean handleFlag = false;
                    
                    for (Map.Entry<String, Client> entity : clientFactory.getConnectMap().entrySet()) {
                        Client client = entity.getValue();
                        Pack packc;
                        client.addListener(new ClientListener() {
                            @Override
                            public void onData(PackHeader header, ByteBuffer buffer, int bytes) {

                            }
                        });
                        if(!ClientService.weightClientId.equals(client.getClientId())) {
                            continue;
                        }
                        pack = client.readQueuePoll();
                        if(pack == null) {
                            continue;
                        }
                        if(pack != null ){
                            try {
                                String json = new String(pack.getData(),"UTF-8");
                                /*Weight weight = gson.fromJson(json, Weight.class);
                                String filterRegex = systemSetService.getProperty(SystemProperties.PREVIEW_FILTER);//车牌号的正则表达式
                                if(StringUtils.isNotBlank(filterRegex)) {
                                    if(v.getCarNum().matches(filterRegex)) {
                                        continue;
                                    }
                                }*/
                                ConcurrentHashMap<String,Object> dataWrap = new ConcurrentHashMap<String,Object>();
                                log.debug("缓存数量 recvVCache :"+recvVCache.size());
                                Iterator<ConcurrentHashMap<String,Object>> iterator = recvVCache.iterator();
                                 while(iterator.hasNext()){
                                    ConcurrentHashMap<String,Object> map = iterator.next();
                                    if(map.containsKey("cameraMatchno")){
                                        Preview preview=(Preview)map.get("preview");
                                        preview=jsonToPreview(preview,json);
                                        String Str1= UUID.randomUUID().toString().replace("-", "").toUpperCase();
                                        preview.setPreviewId(Str1);
                                        if(previewService.insertSelective(preview)<=0){
                                            log.debug("车辆信息数据处理存储失败"+preview.toString());
                                        };
                                        for (OnDataCallback onDataCallback : listeners) {
                                            onDataCallback.onVehicleData(JSONObject.toJSONString(preview));
                                        }
                                        recvVCache.remove(map);
                                    }else{
                                        Preview preview=new Preview();
                                        preview=jsonToPreview(preview,json);
                                        dataWrap.put("preview", preview);
                                        dataWrap.put("time", new Date());
                                        dataWrap.put("weightMatchno", preview.getMatchNo());
                                        recvVCache.add(dataWrap);
                                    }
                                }
                                log.debug(json);
                                handleFlag = true;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if(handleFlag == false) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    
                }
            }}).start();
    }

    private void startCameraQueueHandleThread(){
        new Thread(new Runnable(){
            @Override
            public void run() {
                while(!Thread.interrupted()){
                    PackInterface pack = null;
                    boolean handleFlag = false;
                    
                    for (Map.Entry<String, Client> entity : clientFactory.getConnectMap().entrySet()) {
                        Client client = entity.getValue();
                        if(!ClientService.cameraClientId.equals(client.getClientId())) {
                            continue;
                        }
                        pack = client.readQueuePoll();
                        if(pack != null){
                            try {
                                String json = new String(pack.getData(),"UTF-8");
                                ConcurrentHashMap<String,Object> dataWrap = new ConcurrentHashMap<String,Object>();
                                log.debug("缓存数量 recvVCache :"+recvVCache.size());
                                Iterator<ConcurrentHashMap<String,Object>> iterator = recvVCache.iterator();
                                while(iterator.hasNext()){
                                    ConcurrentHashMap<String,Object> map = iterator.next();
                                    if(map.containsKey("weightMatchno")){
                                        Preview preview=(Preview)map.get("preview");
                                        preview=jsonToPreview(preview,json);
                                        String Str1= UUID.randomUUID().toString().replace("-", "").toUpperCase();
                                        preview.setPreviewId(Str1);
                                        if(previewService.insertSelective(preview)<=0){
                                            log.debug("车辆信息数据处理存储失败"+preview.toString());
                                        };
                                        for (OnDataCallback onDataCallback : listeners) {
                                            onDataCallback.onVehicleData(JSONObject.toJSONString(preview));
                                        }
                                        recvVCache.remove(map);
                                    }else{
                                        Preview preview=new Preview();
                                        preview=jsonToPreview(preview,json);
                                        dataWrap.put("preview", preview);
                                        dataWrap.put("time", new Date());
                                        dataWrap.put("cameraMatchno", preview.getMatchNo());
                                        recvVCache.add(dataWrap);
                                    }
                                }
                                log.debug(json);
                                handleFlag = true;
                                
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    
                    if(handleFlag == false) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }}).start();
    }


    public void addListener(OnDataCallback callback) {
        listeners.add(callback);
    }

    public void removeListener(OnDataCallback callback) {
        listeners.remove(callback);
    }
    
    private SystemSetService systemSetService;
    
    public void setSystemSetService(SystemSetService systemSetService) {
        this.systemSetService = systemSetService;
    }
    private String getPicPath() {
        return this.systemSetService.getProperty(SystemProperties.PIC_PATH);
    }
    
    public static void main(String[] args) {
        
        System.out.println("蓝豫12345".matches("(.*)[蓝,白,绿](.*)"));
    }

    //整合称重和抓拍数据 json字符串转对象Preview
    public Preview jsonToPreview(Preview preview,String json){
        JSONObject jsonObject = JSONObject.parseObject(json);
        Gson gson = new Gson();
        Camera camera=new Camera();
        if(jsonObject.getBoolean("plateNum")){
            camera=gson.fromJson(json,Camera.class);
            if(StringUtils.isNotBlank(preview.getMatchNo())){
                preview.setMatchNo(camera.getMatchNo());
            }
            if(StringUtils.isNotBlank(camera.getDeviceId())){
                preview.setCameraDeviceId(camera.getDeviceId());//添加抓拍设备
            }
            if(StringUtils.isNotBlank(camera.getPlateNum())){
                preview.setCameraDeviceId(camera.getPlateNum());//添加车牌号
            }
            preview.setCameraDir(camera.getCameraDir());//添加抓拍位置
            if(camera.getColor()!=null){
                preview.setColor(camera.getColor());
            }
            if(StringUtils.isNotBlank(camera.getDateTime())){
                preview.setCameraDateTime(camera.getDateTime());
            }
            if(StringUtils.isNotBlank(camera.getAttachmentNo())){
                preview.setCameraAttachmentNo(camera.getAttachmentNo());
            }
                preview.setCameraCreateTime(camera.getCreateTime());
            if(camera.getSpeed()!=null){
                preview.setCameraSpeed(camera.getSpeed());
            }
        }
        Weight weight=new Weight();
        if(jsonObject.getBoolean("sumWeight")){
            weight=gson.fromJson(json,Weight.class);
            if(StringUtils.isNotBlank(weight.getStationId())){
                preview.setStationId(weight.getStationId());
            }
                preview.setSumWeight(weight.getSumWeight());
            if(StringUtils.isNotBlank(weight.getAxleWeight())){
                preview.setAxleWeight(weight.getAxleWeight());
            }
            if(StringUtils.isNotBlank(weight.getAxleType())){
                preview.setAxleType(weight.getAxleType());
            }
            preview.setWeightLane(weight.getLane());
            preview.setWeightSpeed(weight.getSpeed());
            preview.setReverse(weight.getReverse());
            preview.setRunState(weight.getRunState());
            preview.setWeightCreateTime(weight.getCreateTime());
            if(StringUtils.isNotBlank(weight.getDateTime())){
                preview.setWeightDateTime(weight.getDateTime());
            }
            if(StringUtils.isNotBlank(weight.getDeviceId())){
                preview.setWeightDeviceId(weight.getDeviceId());
            }
            if(StringUtils.isNotBlank(weight.getAttachmentNo())){
                preview.setWeightAttachmentNo(weight.getAttachmentNo());
            }
        }

        return preview;
    }
}
