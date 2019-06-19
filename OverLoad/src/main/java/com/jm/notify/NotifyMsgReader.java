package com.jm.notify;

import java.nio.ByteBuffer;

import org.iot.client.common.Client;
import org.iot.client.common.MessageReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotifyMsgReader extends MessageReader{
    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    public NotifyMsgReader() {
    }

    private Pack tmpPack;
    
    @Override
    public void onData(Client client, ByteBuffer buffer, int bytes) {
        try {
            //记录日志
            log.debug(client.getClientId() +": on data size "+ bytes);
            buffer.flip();
            
            //解析
            if(tmpPack == null) {
                tmpPack = new Pack();
            }
            
            while(buffer.hasRemaining()) {
                tmpPack.readPackFromByteBuffer(client.getClientId(),buffer, bytes);
                if(tmpPack.isComplete()) {
                    if(!tmpPack.isValid()) {
                        log.debug("data valid error!");
                    }else {
                        log.debug(client.getClientId() + ":to queue "+tmpPack.getCmd());
                        client.readPackToQueue(tmpPack);
                    }
                    
                    tmpPack = new Pack();
                }
            }
        } catch (Exception e) {
            tmpPack = new Pack();
            log.debug("onData:"+e.getMessage()+" "+e.getClass().getName());
            //e.printStackTrace();
        }
    }
    
    
}
