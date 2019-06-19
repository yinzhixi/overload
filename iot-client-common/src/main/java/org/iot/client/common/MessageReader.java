package org.iot.client.common;

import java.nio.ByteBuffer;
import java.util.Map;

import org.iot.client.common.decoder.Pack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
public class MessageReader implements DataReader {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private Map<String,String> clientKeyMap;
    private String clientKey;
    public MessageReader() {
        
    }
    
    public MessageReader(String clientKey) {
        this.clientKey = clientKey;
    }
    
    public MessageReader(Map<String,String> clientKeyMap) {
        this.clientKeyMap = clientKeyMap;
    }
    
    public boolean acceptsMessages() {
        return true;
    }

    /**
     *
     * @param client the client to read messages from
     */
    @Override
    public void beforeRead(Client client) {
    }
    
    /**
     * @param client the client to append messages to
     * @param byteBuffer the buffer we received from the socket
     * @param bytes the number of bytes read into the buffer
     */
    private Pack pack;
    @Override
    public void onData(Client client, ByteBuffer byteBuffer, int bytes) {
        try {
            //记录日志
            log.debug(client.getClientId() +":读取字节数 "+ bytes);
            byteBuffer.flip();
            
            while(byteBuffer.hasRemaining()) {
                if(pack == null) {
                    pack = new Pack();
                }
                //解析数据包
                pack.readPackFromByteBuffer(client.getClientId(), byteBuffer);
                
                if(pack.isCompleted()) {
                    if(pack.isValid(client.getClientKey())) {
                        //触发数据监听
                        client.fireListeners(pack);
                        pack = null;
                    }else {
                        log.debug(String.format("校验失败 bytes=%d",bytes));
                        /*byteBuffer.position(0);
                        StringBuilder sb = new StringBuilder();
                        while(byteBuffer.hasRemaining()) {
                            sb.append(String.format("%02x ",byteBuffer.get()));
                        }
                        log.debug(sb.toString());*/
                        
                        pack = null;
                        return;
                    }
                }
            }
        } catch (Exception e) {
            pack = null;
            log.debug("解析报错误 "+e.getMessage());
        }
   }
}
