/*
package com.jm.serviceImpl.out;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

import com.jm.service.StaticWeightService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.iot.client.common.Client;
import org.iot.client.common.ClientFactory;
import org.iot.client.common.ClientReader;
import org.iot.client.common.MessageReader;
import org.iot.client.common.PackInterface;
import org.iot.client.common.utils.PackUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.jm.bean.Preview;
import com.jm.service.ReviewedPreviewService;
import com.jm.service.SystemSetService;
import com.jm.serviceImpl.SystemProperties;
import com.jm.serviceImpl.UploadService;
import com.jm.websocket.ClientService;
import com.jm.websocket.OnDataCallback;
*/
/**
 * 对外服务节点
 * @author luke
 *
 *//*

@Service
public class FXCNodeService implements OnDataCallback{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SystemSetService systemSetService;
    @Autowired
    private ReviewedPreviewService reviewedPreviewService;
    @Autowired
    private UploadService uploadService;

    private volatile ClientFactory clientFactory  = new ClientFactory();
    private final Queue<DataWrap> readQueue = new LinkedList<DataWrap>();
    private Thread connectThread;
    private Thread readThread;
    private Thread writeThread;

    @Value("#{setting.notify_server_ip}")
    private String serverIp;
    @Value("#{setting.notify_server_port}")
    private int port;
    @Value("#{setting.notify_client_id}")
    private String clientId;
    @Value("#{setting.notify_client_key}")
    private String clientKey;

    public void start() {
        this.startConnectThread();
        this.startWriteThread();
        this.startReadThread();
        ClientService.addDataListener(this);
    }

    private void startConnectThread() {
        this.connectThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ClientReader dataReader = new ClientReader(new MessageReader(clientKey));
                clientFactory.createClient(serverIp,port,clientId, clientKey, dataReader);
            }
        });
        this.connectThread.start();
    }

    private void startReadThread() {
        this.readThread = new Thread(new Runnable() {
            Gson gson = new Gson();
            @Override
            public void run() {
                while(!Thread.interrupted()) {
                    PackInterface pack = null;
                    boolean handleFlag = false;
                    for (Map.Entry<String, Client> entity : clientFactory.getConnectMap().entrySet()) {
                        Client client = entity.getValue();

                        pack = client.readQueuePoll();
                        if(pack == null) {
                            continue;
                        }

                        if(pack != null && pack.getCmd() == 0x02){
                            byte[] data = pack.getData();
                            ByteBuffer buffer = ByteBuffer.allocate(data.length);
                            buffer.put(data);
                            buffer.flip();
                            Charset cs = Charset.forName ("utf-8");
                            CharBuffer cb = cs.decode(buffer);
                            Map resMap = gson.fromJson(cb.toString(), Map.class);
                            Integer previewId = NumberUtils.toInt(Objects.toString(resMap.get("previewId"),"-1"));
                            Integer res = NumberUtils.toInt(Objects.toString(resMap.get("res"),"-1"));

                            reviewedPreviewService.setUploaded(previewId,res);
                            uploadService.updatePreviewUpload(previewId,res);
                            handleFlag = true;
                        }

                        if(pack != null && pack.getCmd() == 0x04){
                            byte[] data = pack.getData();
                            ByteBuffer buffer = ByteBuffer.allocate(data.length);
                            buffer.put(data);
                            buffer.flip();
                            Charset cs = Charset.forName ("utf-8");
                            CharBuffer cb = cs.decode(buffer);
                            Map resMap = gson.fromJson(cb.toString(), Map.class);
                            int previewId = (int)NumberUtils.toDouble(Objects.toString(resMap.get("previewId"),"-1"));
                            Integer res = NumberUtils.toInt(Objects.toString(resMap.get("res"),"-1"));
                            String picName = Objects.toString(resMap.get("picName"));
                            uploadService.updatePreviewImgUpload(previewId,res,picName);

                        }
                    }

                    if(handleFlag == false) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        });
        this.readThread.start();
    }

    private void startWriteThread() {
        this.writeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();

                boolean handleFlag = true;
                RandomAccessFile fs = null;

                while(!Thread.interrupted()) {
                    try {
                        DataWrap dataWrap = readQueuePoll();
                        if(dataWrap != null) {
                            if("checkWeight".equals(dataWrap.getDataType())) {
                                String json = gson.toJson(dataWrap.getData());
                                byte[] bs = new byte[0];
                                bs = json.getBytes("utf-8");
                                ByteBuffer byteBuffer = PackUtils.wrap(clientId, bs,bs.length, (byte)0x01, clientKey);
                                logger.debug("send:"+json);
                                byteBuffer.flip();
                                clientFactory.sendData(byteBuffer, clientId);
                            }
                            if("frontPic".equals(dataWrap.getDataType()) ||
                                    "backPic".equals(dataWrap.getDataType())||
                                    "sidePic".equals(dataWrap.getDataType())||
                                    "upPic".equals(dataWrap.getDataType())||
                                    "screenPic".equals(dataWrap.getDataType())) {
                                String json = gson.toJson(dataWrap);
                                byte[] bs = json.getBytes("utf-8");

                                String fileName = ((Map)dataWrap.getData()).get("picName").toString();
                                String picPath = systemSetService.getProperty(SystemProperties.PIC_PATH);
                                File file = new File(picPath+fileName);
                                if(file.exists()) {
                                    fs = new RandomAccessFile(file,"r");
                                    long fileLen = fs.length();
                                    ByteBuffer buffer = ByteBuffer.allocate(4+bs.length);
                                    buffer.putInt((int)fileLen);
                                    buffer.put(bs);
                                    buffer.flip();
                                    ByteBuffer byteBuffer = PackUtils.wrap(clientId, buffer, (byte)0x03, clientKey);
                                    logger.debug("send:"+json);
                                    byteBuffer.flip();
                                    clientFactory.sendData(byteBuffer, clientId);

                                    byte[] fileBuf = new byte[1024*1024];
                                    int len;
                                    while ((len = fs.read(fileBuf)) != -1){
                                        byteBuffer = PackUtils.wrap(clientId, fileBuf,len, (byte)0x33, clientKey);
                                        byteBuffer.flip();
                                        clientFactory.sendData(byteBuffer, clientId);
                                    }
                                }
                            }
                            handleFlag = true;
                        }else {
                            handleFlag = false;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        if(fs != null) {
                            try {
                                fs.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if(!handleFlag) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        this.writeThread.start();
    }

    */
/**
     * 存储上传的记录
     * 放入上传队列
     * @param preview
     *//*

    public void onCheckWeight(Preview preview) {

        SimpleDateFormat sfa = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String curTime = sfa.format(new Date());
        uploadService.addPreviewUpload(preview.getPreviewId(), 0, curTime);
        if(!StringUtils.isBlank(preview.getFrontPic())) {
            this.uploadService.addPreviewImgUpload(preview.getPreviewId(), preview.getFrontPic(), 0, curTime);
        }
        if(!StringUtils.isBlank(preview.getBackPic())) {
            this.uploadService.addPreviewImgUpload(preview.getPreviewId(), preview.getBackPic(), 0, curTime);
        }
        if(!StringUtils.isBlank(preview.getSidePic())) {
            this.uploadService.addPreviewImgUpload(preview.getPreviewId(), preview.getSidePic(), 0, curTime);
        }
        if(!StringUtils.isBlank(preview.getUpPic())) {
            this.uploadService.addPreviewImgUpload(preview.getPreviewId(), preview.getUpPic(), 0, curTime);
        }

        String stationShort = systemSetService.getProperty(SystemProperties.STATION_SHORT);
        String stationId = systemSetService.getProperty(SystemProperties.STATION_CODE);
        String stationIp = systemSetService.getProperty(SystemProperties.STATION_IP);

        DataWrap dataWrap = new DataWrap();
        dataWrap.setDataType("checkWeight");
        preview.setStationId(stationId);
        preview.setStationIp(stationIp);
        preview.setStationShort(stationShort);
        dataWrap.setData(preview);
        this.readPackToQueue(dataWrap);
        if(StringUtils.isNotBlank(preview.getFrontPic())) {
            DataWrap imgWrap = new DataWrap();
            imgWrap.setDataType("frontPic");
            Map<String,Object> data = new HashMap<String,Object>();
            data.put("checkNo", preview.getCheckNo());
            data.put("previewId", preview.getPreviewId());
            data.put("picName", preview.getFrontPic());
            data.put("dateTime", preview.getDateTime());
            data.put("lane", preview.getLane());
            data.put("stationId", stationId);
            data.put("stationIp", stationIp);
            data.put("stationShort", stationShort);
            imgWrap.setData(data);
            this.readPackToQueue(imgWrap);
        }
        if(StringUtils.isNotBlank(preview.getBackPic())) {
            DataWrap imgWrap = new DataWrap();
            imgWrap.setDataType("backPic");
            Map<String,Object> data = new HashMap<String,Object>();
            data.put("checkNo", preview.getCheckNo());
            data.put("previewId", preview.getPreviewId());
            data.put("picName", preview.getBackPic());
            data.put("dateTime", preview.getDateTime());
            data.put("lane", preview.getLane());
            data.put("stationId", stationId);
            data.put("stationIp", stationIp);
            data.put("stationShort", stationShort);
            imgWrap.setData(data);
            this.readPackToQueue(imgWrap);
        }
        if(StringUtils.isNotBlank(preview.getSidePic())) {
            DataWrap imgWrap = new DataWrap();
            imgWrap.setDataType("sidePic");
            Map<String,Object> data = new HashMap<String,Object>();
            data.put("checkNo", preview.getCheckNo());
            data.put("previewId", preview.getPreviewId());
            data.put("picName", preview.getSidePic());
            data.put("dateTime", preview.getDateTime());
            data.put("lane", preview.getLane());
            data.put("stationId", stationId);
            data.put("stationIp", stationIp);
            data.put("stationShort", stationShort);
            imgWrap.setData(data);
            this.readPackToQueue(imgWrap);
        }
        if(StringUtils.isNotBlank(preview.getUpPic())) {
            DataWrap imgWrap = new DataWrap();
            imgWrap.setDataType("upPic");
            Map<String,Object> data = new HashMap<String,Object>();
            data.put("checkNo", preview.getCheckNo());
            data.put("previewId", preview.getPreviewId());
            data.put("picName", preview.getUpPic());
            data.put("dateTime", preview.getDateTime());
            data.put("lane", preview.getLane());
            data.put("stationId", stationId);
            data.put("stationIp", stationIp);
            data.put("stationShort", stationShort);
            imgWrap.setData(data);
            this.readPackToQueue(imgWrap);
        }
        if(StringUtils.isNotBlank(preview.getScreenPic())) {
            DataWrap imgWrap = new DataWrap();
            imgWrap.setDataType("screenPic");
            Map<String,Object> data = new HashMap<String,Object>();
            data.put("checkNo", preview.getCheckNo());
            data.put("previewId", preview.getPreviewId());
            data.put("picName", preview.getScreenPic());
            data.put("dateTime", preview.getDateTime());
            data.put("lane", preview.getLane());
            data.put("stationId", stationId);
            data.put("stationIp", stationIp);
            data.put("stationShort", stationShort);
            imgWrap.setData(data);
            this.readPackToQueue(imgWrap);
        }

    }

    public void readPackToQueue(DataWrap pack) {
        synchronized (this.readQueue) {
            readQueue.add(pack);
        }
    }

    public DataWrap readQueuePoll() {
        DataWrap pack = null;
        synchronized (readQueue) {
            pack = readQueue.poll();
        }
        return pack;
    }

    @Override
    public void onVehicleData(String json) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onImgData(String imgFileName) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSnapData(String json) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onFrontImgComplete(String jsonVehicle, String jsonImg) {
        // TODO Auto-generated method stub

    }

    private Gson gson = new Gson();

    @Override
    public void onAllImgComplete(String jsonVehicle) {
        Preview preview = gson.fromJson(jsonVehicle, Preview.class);
        this.onCheckWeight(preview);
    }

    @Override
    public void onSnapImgComplete(String jsonVehicle, String jsonSnapImg) {

    }

    @Override
    public void onStcData(String data, StaticWeightService staticWeightService) {

    }


}
*/
