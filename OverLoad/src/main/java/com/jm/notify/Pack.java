package com.jm.notify;

import java.nio.ByteBuffer;

import org.iot.client.common.PackInterface;
import org.iot.client.common.utils.Const;
import org.iot.client.common.utils.TransUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pack implements PackInterface{
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private byte startMark1;
    private byte startMark2;
    private byte cmd = -1;
    
    private int dataLen = -1;
    private byte[] dataLen_b;
    private int dataLenOffset = 0;
    
    private byte[] data;
    private int dataOffset = 0;
    
    private int clientIdLen = -1;
    private byte[] clientIdLen_b;
    private int clientIdLenOffset = 0;
    
    private byte[] clientId;
    private int clientIdOffset = 0;
    
    private byte[] token;
    private int tokenoffset = 0;
    
    private byte endMark1;
    private byte endMark2;
    private int packLen = 0;
    private boolean complete = false;
    
    public void readPackFromByteBuffer(String clientId,ByteBuffer buffer,int bytes) throws Exception {
        while(buffer.hasRemaining()) {
            //读取开始
            if(buffer.hasRemaining() && !this.startMark1IsComplete()) {
                byte s1 = buffer.get();
                this.setStartMark1(s1);
                if(!this.startMark1IsComplete()) {
                    this.setStartMark1((byte)-1);
                    continue;
                }
            }
            
            if(this.startMark1IsComplete() && buffer.hasRemaining() && !this.startMark2IsComplete()) {
                byte s2 = buffer.get();
                this.setStartMark2(s2);
                if(!this.startMark2IsComplete()) {
                    this.setStartMark1((byte)-1);
                    this.setStartMark2((byte)-1);
                    continue;
                }else {
                    log.debug(clientId+":已读取开始");
                    packLen = packLen + 2;
                }
            }
            
            //读取命令
            if(this.startMark2IsComplete() && buffer.hasRemaining() && !this.cmdIsComplete() ) {
                byte cmd = buffer.get();
                packLen++;
                //TODO 过滤 数据不符数据包
                log.debug(clientId+":已读取命令："+cmd);
                this.setCmd(cmd);
            }
            
            //读取数据长度
            if(this.cmdIsComplete() && buffer.hasRemaining() && !this.dataLenIsComplete()) {
                int total = 4,offset = 0;
                if(this.dataLen_b == null) {
                    this.dataLen_b = new byte[total];
                }else {
                    offset = dataLenOffset;
                }
                int remain = total - offset;
                if(buffer.remaining() >= remain) {
                    buffer.get(this.dataLen_b, offset, remain);
                    packLen+=remain;
                    this.setDataLen((int)TransUtils.bytesToLong(dataLen_b[0], dataLen_b[1], dataLen_b[2], dataLen_b[3]));
                    this.dataLenOffset = offset + remain;
                    
                    if(this.getDataLen() > Const.CLIENT_PACK_PER_LIMIT_LEN || this.getDataLen() <= 0) {
                        log.error(clientId+":数据长度错误！");
                        clear();
                        continue;
                    }else {
                        log.debug(clientId+":数据长度已解析："+this.getDataLen());
                    }
                }else {
                    int remain_bf = buffer.remaining();
                    buffer.get(this.dataLen_b, offset, remain_bf);
                    packLen+=remain_bf;
                    this.dataLenOffset = offset + remain_bf;
                }
            }
            
            //读取数据
            if(this.dataLenIsComplete() && buffer.hasRemaining() && !this.dataIsComplete()) {
                int total = this.getDataLen(),offset = 0;
                if(this.data == null) {
                    this.data = new byte[total];
                }else {
                    offset = this.dataOffset;
                }
                
                int remain = total - offset;
                if(buffer.remaining() >= remain) {
                    buffer.get(this.data,offset,remain);
                    packLen+=remain;
                    this.dataOffset = offset + remain;
                    log.debug(clientId+":数据已解析完成");
                }else {
                    int remain_bf = buffer.remaining();
                    buffer.get(this.data,offset,remain_bf);
                    packLen+=remain_bf;
                    this.dataOffset = offset + remain_bf;
                    log.debug(clientId+":数据已解析，未完成");
                }
            }
            
            //读取clientId长度
            if(this.dataIsComplete() && buffer.hasRemaining() && !this.clientIdLenIsComplete()) {
                int total = 4,offset = 0;
                if(this.clientIdLen_b == null) {
                    this.clientIdLen_b = new byte[4];
                }else {
                    offset = this.clientIdLenOffset;
                }
                
                int remain = total - offset;
                if(buffer.remaining() >= remain) {
                    buffer.get(this.clientIdLen_b, offset, remain);
                    packLen+=remain;
                    log.debug(clientId+":client id bytes "+String.format("%x%x%x%x", clientIdLen_b[0],clientIdLen_b[1],clientIdLen_b[2],clientIdLen_b[3]));
                    this.setClientIdLen((int)TransUtils.bytesToLong(this.clientIdLen_b[0], this.clientIdLen_b[1], this.clientIdLen_b[2], this.clientIdLen_b[3]));
                    this.clientIdLenOffset = offset + remain;
                    if(total > Const.CLIENT_ID_LIMIT_LEN || total <= 0) {
                        log.error(clientId+":client id 长度错误！");
                        clear();
                        continue;
                    }else {
                        log.debug(clientId+":client id 长度已解析："+this.getClientIdLen());
                    }
                }else {
                    int remain_bf = buffer.remaining();
                    buffer.get(this.clientIdLen_b,offset,remain_bf);
                    packLen+=remain_bf;
                    this.clientIdLenOffset = offset + remain_bf;
                }
            }
            
            //读取clientId
            if(this.clientIdLenIsComplete() && buffer.hasRemaining() && !this.clientIdIsComplete()) {
                int total = this.getClientIdLen(),offset = 0;
                if(this.clientId == null) {
                    this.clientId = new byte[total];
                }else {
                    offset = this.clientIdOffset;
                }
                
                int remain = total - offset ;
                if(buffer.remaining() >= remain) {
                    buffer.get(this.clientId,offset,remain);
                    packLen+=remain;
                    this.clientIdOffset = offset + remain;
                    log.debug(clientId+":clientId 已解析完成");
                }else {
                    int remain_bf = buffer.remaining();
                    buffer.get(this.clientId,offset,remain_bf);
                    packLen+=remain_bf;
                    this.clientIdOffset = offset + remain_bf;
                }
            }
            
            //读取token
            if(this.clientIdIsComplete() && buffer.hasRemaining() && !this.tokenIsComplete() ) {
                int total = 32,offset = 0;
                if(this.token == null) {
                    this.token = new byte[total];
                }else {
                    offset = this.tokenoffset;
                }
                int remain = total - offset;
                if(buffer.remaining() >= remain) {
                    buffer.get(this.token,offset,remain);
                    packLen+=remain;
                    this.tokenoffset = offset + remain;
                    log.debug(clientId+":token 已解析完成");
                }else {
                    int remain_bf = buffer.remaining();
                    buffer.get(this.token,offset,remain_bf);
                    packLen+=remain_bf;
                    this.tokenoffset = offset + remain_bf;
                }
            }
            
            //读取结束
            if(this.tokenIsComplete() && buffer.hasRemaining() && !this.endMark1IsComplete()) {
                byte e1 = buffer.get();
                packLen++;
                this.setEndMark1(e1);
                if(!this.endMark1IsComplete()) {
                    clear();
                    log.debug(clientId+":e1 读取错误");
                    continue;
                }
            }
            
            if(buffer.hasRemaining() && this.endMark1IsComplete() && !this.endMark2IsComplete()) {
                byte e2 = buffer.get();
                packLen++;
                this.setEndMark2(e2);
                if(!this.endMark2IsComplete()) {
                    clear();
                    log.debug(clientId+":e2 读取错误");
                    continue;
                }else {
                    this.complete = true;
                    log.debug(clientId+":包读取完成");
                    return;
                }
            }
        }
    }
    private void clear() {
        startMark1 = 0;
        startMark2 = 0;
        cmd = -1;
        
        dataLen = -1;
        dataLen_b = null;
        dataLenOffset = 0;
        
        data = null;
        dataOffset = 0;
        
        clientIdLen = -1;
        clientIdLen_b = null;
        clientIdLenOffset = 0;
        
        clientId = null;
        clientIdOffset = 0;
        
        token = null;
        tokenoffset = 0;
        
        endMark1 = 0;
        endMark2 = 0;
        packLen = 0;
        complete = false;
    }
    
    public static void main(String[] args) throws Exception {
        /*byte[] data = new byte[] {
                -86,-86,
                1,
                8,0,0,0,
                1,2,3,4,5,6,7,8,
                5,0,0,0,
                0,0,0,0,1,
                1,2,3,4,5,6,7,8,9};
        byte[] data2 = new byte[] {
                0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,
                -18,-18
                
        };
        ByteBuffer buffer = ByteBuffer.allocate(data.length);
        buffer.put(data);
        
        Pack pack = new Pack();
        pack.readPackFromByteBuffer(buffer, buffer.limit());
        if(!pack.isComplete()) {
            buffer = ByteBuffer.allocate(data2.length);
            buffer.put(data2);
            pack.readPackFromByteBuffer(buffer, data2.length);
        }*/
        
    }
    private boolean endMark1IsComplete() {
        byte e = -18;
        return this.getEndMark1() == e;
    }
    private boolean endMark2IsComplete() {
        byte e = -18;
        return this.getEndMark2() == e;
    }
    
    private boolean tokenIsComplete() {
        return this.tokenoffset == 32;
    }
    
    private boolean clientIdIsComplete() {
        return this.clientIdLenIsComplete() && this.clientIdOffset == this.getClientIdLen();
    }
    
    private boolean clientIdLenIsComplete() {
        return this.getClientIdLen() != -1; 
    }
    private boolean dataIsComplete() {
        return this.dataLenIsComplete() && this.dataOffset == this.getDataLen();
    }
    private boolean dataLenIsComplete() {
        return this.getDataLen() != -1;
    }
    
    private boolean cmdIsComplete() {
        return this.getCmd() != -1;
    }
    
    private boolean startMark1IsComplete() {
        byte s = -86;
        return this.getStartMark1() == s;
    }
    private boolean startMark2IsComplete() {
        byte s = -86;
        return this.getStartMark2() == s;
    }
    public byte getStartMark1() {
        return startMark1;
    }
    public void setStartMark1(byte startMark1) {
        this.startMark1 = startMark1;
    }
    public byte getStartMark2() {
        return startMark2;
    }
    public void setStartMark2(byte startMark2) {
        this.startMark2 = startMark2;
    }
    public byte getCmd() {
        return cmd;
    }
    public void setCmd(byte cmd) {
        this.cmd = cmd;
    }
    public int getDataLen() {
        return dataLen;
    }

    @Override
    public void readPackFromByteBuffer(String clientId, ByteBuffer buffer, long bytes) throws Exception {

    }

    public void setDataLen(int dataLen) {
        this.dataLen = dataLen;
    }
    public byte[] getData() {
        return data;
    }
    public void setData(byte[] data) {
        this.data = data;
    }
    public int getClientIdLen() {
        return clientIdLen;
    }
    public void setClientIdLen(int clientIdLen) {
        this.clientIdLen = clientIdLen;
    }
    public byte[] getClientId() {
        return clientId;
    }
    public void setClientId(byte[] clientId) {
        this.clientId = clientId;
    }
    public byte[] getToken() {
        return token;
    }
    public void setToken(byte[] token) {
        this.token = token;
    }
    public byte getEndMark1() {
        return endMark1;
    }
    public void setEndMark1(byte endMark1) {
        this.endMark1 = endMark1;
    }
    public byte getEndMark2() {
        return endMark2;
    }
    public void setEndMark2(byte endMark2) {
        this.endMark2 = endMark2;
    }
    public boolean isComplete() {
        return complete;
    }
    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean isValid() {
        try {
        } catch (Exception e) {
            log.debug("校验时异常："+e.getMessage()+":"+e.getClass().getName());
        }
        return false;
    }

    public byte[] getClientIdLen_b() {
        return clientIdLen_b;
    }

    public void setClientIdLen_b(byte[] clientIdLen_b) {
        this.clientIdLen_b = clientIdLen_b;
    }
    
}
