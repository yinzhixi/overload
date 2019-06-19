package org.iot.client.common;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.iot.client.common.decoder.PackHeader;
import org.iot.client.common.utils.MD5Utils;
import org.iot.client.common.utils.TransUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

import junit.framework.TestCase;

public class ClientTest extends TestCase {
    private String serverIp = "127.0.0.1";
    private int port = 5678;
    
    //@Test
    public void testClient() {
        
        new Thread(new Runnable() {
            
            @Override
            public void run() {

                ClientReader dataReader = new ClientReader(new MessageReader(ClientKey.getClientKeyMap()));
                ClientFactory clientFactory = new ClientFactory();
                Client client00001 = clientFactory.createClient(serverIp,port,"QSY_00001", ClientKey.getClientKey("QSY_00001") ,dataReader);
                Client client00002 = clientFactory.createClient(serverIp,port,"QSY_00002", ClientKey.getClientKey("QSY_00002") ,dataReader);
                Client client00003 = clientFactory.createClient(serverIp,port,"QSY_00003", ClientKey.getClientKey("QSY_00003") ,dataReader);
                Client client00004 = clientFactory.createClient(serverIp,port,"QSY_00004", ClientKey.getClientKey("QSY_00004") ,dataReader);
                Client client00005 = clientFactory.createClient(serverIp,port,"QSY_00005", ClientKey.getClientKey("QSY_00005") ,dataReader);
                
                Client webViewClient = clientFactory.createClient(serverIp,port,"QSY_Weight_System_WebView", ClientKey.getClientKey("QSY_Weight_System_WebView") ,dataReader);
                webViewClient.addListener(new ClientListener() {
                    
                    @Override
                    public void onData(PackHeader header, ByteBuffer buffer, int bytes) {
                        System.out.println(header.toString());
                        System.out.println(new String(buffer.array()));
                        System.out.println();
                    }
                });
                
                Map<String,Object> wrap = new HashMap<>();
                Map<String,Object> weight = new HashMap<>();
                weight.put("sumWeight", new BigDecimal(20000));
                weight.put("lane", 2);
                weight.put("axleCnt",3);
                wrap.put("data", weight);
                String headJSON = JSON.toJSONString(wrap);
                
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(true) {
                            ByteBuffer buffer = genPackData((byte)1,"QSY_00001", "*", headJSON);
                            client00001.writeMessage(buffer);
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(true) {
                            ByteBuffer buffer = genPackData((byte)1,"QSY_00002", "*", headJSON);
                            client00002.writeMessage(buffer);
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(true) {
                            ByteBuffer buffer = genPackData((byte)1,"QSY_00003", "*", headJSON);
                            client00003.writeMessage(buffer);
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(true) {
                            ByteBuffer buffer = genPackData((byte)1,"QSY_00004", "*", headJSON);
                            client00004.writeMessage(buffer);
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(true) {
                            ByteBuffer buffer = genPackData((byte)1,"QSY_00005", "*", headJSON);
                            client00005.writeMessage(buffer);
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                
                
                
                
                
            }
            
        }).start();
        
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    private ByteBuffer genPackData(byte type,String fromId,String toId, String content) {
        Map<String,Object> header = new HashMap<>();
        header.put("from", fromId);
        header.put("dest", toId);
        String headJSON = JSON.toJSONString(header);
        byte[] hbytes = headJSON.getBytes();
        
        byte[] contentBytes = content.getBytes();
        byte[] contentLenBytes = TransUtils.longToBytes(content.length()+1);
        
        ByteBuffer buffer = ByteBuffer.allocate(1+hbytes.length + contentBytes.length + 7 + 128);
        
        buffer.put((byte)0xAA);
        buffer.put((byte)1);
        buffer.put((byte)hbytes.length);
        buffer.put(hbytes);
        buffer.put(contentLenBytes);
        buffer.put(type);
        buffer.put(contentBytes);
        byte[] keyBytes = ClientKey.getClientKey("QSY_00001").getBytes();
        buffer.put(keyBytes);
        byte[] bytes = buffer.array();
        
        String token = MD5Utils.encode(bytes);
        byte[] tokenBytes = token.getBytes();
        buffer.position(buffer.position() - keyBytes.length);
        buffer.put(tokenBytes);
        
        //覆盖
        int overBytes = keyBytes.length - tokenBytes.length;
        if(overBytes > 0 ) {
            byte[] blank = new byte[overBytes];
            buffer.put(blank,0,overBytes);
        }
        buffer.flip();
        buffer.limit(buffer.limit() - keyBytes.length + tokenBytes.length);
        return buffer;
    }
}
