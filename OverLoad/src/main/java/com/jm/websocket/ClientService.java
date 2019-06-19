package com.jm.websocket;

import java.nio.ByteBuffer;

import org.iot.client.common.ClientFactory;
import org.iot.client.common.ClientReader;
import org.iot.client.common.MessageReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jm.service.SystemSetService;

@Service
//@PropertySource()
public class ClientService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ClientFactory clientFactory;
    private static MessageQueueHandler messageQueueHandler;
    
    protected static String notifyClientId;
    //节点连接id
    public static final String cameraClientId = "QSY_00001";
    public static final String weightClientId = "00002";
    //public static final String snapClientId = "800003";
    public static final String stcClientId = "std_t18_lis_001";


    //web 管理端 到非现场的代理的连接
    public static final String FXC_CLIENTID = "9000";
    
    //非现场内部服务IP
    private String serverIp = "192.168.16.123";//TO INIT
    private int port = 5678;//TO INIT
    
    @Autowired
    private SystemSetService systemSetService;
    
    public static void main(String[] args) throws InterruptedException {

        ClientService clientService = new ClientService();
        clientService.start();
        Thread.sleep(Long.MAX_VALUE);
    }
    
    public static void addDataListener(OnDataCallback callback) {
        messageQueueHandler.addListener(callback);
    }
    
    public static void removeDataListener(OnDataCallback callback) {
        messageQueueHandler.removeListener(callback);
    }
    
    public ClientFactory getClientFactory() {
        return clientFactory;
    }

    public void setClientFactory(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    public MessageQueueHandler getMessageQueueHandler() {
        return messageQueueHandler;
    }

    public void setMessageQueueHandler(MessageQueueHandler messageQueueHandler) {
        ClientService.messageQueueHandler = messageQueueHandler;
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                startWeightClient();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startCameraClient();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startStcClient();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        
        messageQueueHandler.start();
    }

    public void startStcClient() {
        logger.debug("creating 静态称重节点"+stcClientId);
        ClientReader vDataReader = new ClientReader(new MessageReader(ClientKey.getClientKeyMap()));
        clientFactory.createClient(serverIp,port,stcClientId, ClientKey.getClientKey(stcClientId), vDataReader);
    }
    public void startWeightClient() {
        logger.debug("creating 车辆称重数据节点"+weightClientId);
        ClientReader vDataReader = new ClientReader(new MessageReader(ClientKey.getClientKeyMap()));
        clientFactory.createClient(serverIp,port,weightClientId, ClientKey.getClientKey(weightClientId), vDataReader);
    }
    public void startCameraClient() {
        logger.debug("creating 车辆抓拍数据节点"+cameraClientId);
        ClientReader imgDataReader = new ClientReader(new MessageReader(ClientKey.getClientKeyMap()));
        clientFactory.createClient(serverIp,port,cameraClientId, ClientKey.getClientKey(cameraClientId) ,imgDataReader);
    }
    public void send(ByteBuffer buffer,String clientId) {
        clientFactory.sendData(buffer, clientId);
    }
    
}
