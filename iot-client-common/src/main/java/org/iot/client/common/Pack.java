package org.iot.client.common;

import java.nio.ByteBuffer;

import org.iot.client.common.utils.Const;
import org.iot.client.common.utils.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pack implements PackInterface {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    private int dataLen = -1;
    
    private byte[] data;
    private int dataOffset = 0;

    private byte[] token;
    private int tokenoffset = 0;
    private boolean complete = false;
    
    public Pack(String clientId,long dataSize) throws Exception {
        if(dataSize > Const.CLIENT_PACK_PER_LIMIT_LEN || dataSize <= 0) {
            String errorInfo = String.format("%s数据长度最大值为：%d，请求长度：", 
                    clientId,
                    Const.CLIENT_PACK_PER_LIMIT_LEN,
                    this.getDataLen());
            
            log.error(errorInfo);
            clear();
            throw new Exception(errorInfo);
        }else {
            this.dataLen = (int)dataSize;
            this.data = new byte[this.dataLen];
        }
    }
    
    public void readPackFromByteBuffer(String clientId,ByteBuffer buffer,long bytes) throws Exception {
        
        while(buffer.hasRemaining()) {
            if(buffer.hasRemaining() && !this.dataIsComplete()) {
                int remain = (int)(this.dataLen - this.dataOffset);
                if(buffer.remaining() >= remain) {
                    buffer.get(this.data,this.dataOffset,remain);
                    this.dataOffset = this.dataLen;
                    log.debug(clientId+":数据已解析完成");
                }else {
                    int remain_bf = buffer.remaining();
                    buffer.get(this.data,this.dataOffset,remain_bf);
                    this.dataOffset = this.dataOffset + remain_bf;
                    log.debug(clientId+":数据已解析，未完成");
                }
            }
            
            //读取token
            if(this.dataIsComplete() && buffer.hasRemaining() && !this.tokenIsComplete() ) {
                int total = 32,offset = 0;
                if(this.token == null) {
                    this.token = new byte[total];
                }else {
                    offset = this.tokenoffset;
                }
                int remain = total - offset;
                if(buffer.remaining() >= remain) {
                    buffer.get(this.token,offset,remain);
                    this.tokenoffset = offset + remain;
                    log.debug(clientId+":token 已解析完成");
                    this.complete = true;
                    log.debug(clientId+":包读取完成");
                    log.debug(clientId+":已读取TOKEN："+new String(token));
                    return;
                }else {
                    int remain_bf = buffer.remaining();
                    buffer.get(this.token,offset,remain_bf);
                    this.tokenoffset = offset + remain_bf;
                }
            }
        }
        
        
    }
    
    /*public void readPackFromByteBuffer(String clientId,ByteBuffer buffer,long bytes) throws Exception {
        while(buffer.hasRemaining()) {
            //读取开始
            if(buffer.hasRemaining() && !this.startMark1IsComplete()) {
                int s1 = Byte.toUnsignedInt(buffer.get());
                this.setStartMark1(s1);
                if(!this.startMark1IsComplete()) {
                    this.setStartMark1((byte)-1);
                    continue;
                }
            }
            
            if(this.startMark1IsComplete() && buffer.hasRemaining() && !this.startMark2IsComplete()) {
                int s2 = Byte.toUnsignedInt(buffer.get());
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
                        String errorInfo = clientId+":数据长度错误！"+this.getDataLen();
                        log.error(errorInfo);
                        clear();
                        throw new Exception(errorInfo);
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
            
            //读取token
            if(this.dataIsComplete() && buffer.hasRemaining() && !this.tokenIsComplete() ) {
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
                    this.complete = true;
                    log.debug(clientId+":包读取完成");
                    return;
                }else {
                    int remain_bf = buffer.remaining();
                    buffer.get(this.token,offset,remain_bf);
                    packLen+=remain_bf;
                    this.tokenoffset = offset + remain_bf;
                }
            }
        }
    }
    */
    
    private void clear() {
        
        dataLen = -1;
        
        data = null;
        dataOffset = 0;
        
        token = null;
        this.tokenoffset = 0;
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
    
    private boolean dataIsComplete() {
        return this.dataOffset == this.getDataLen();
    }
    private boolean tokenIsComplete() {
        return this.tokenoffset == 32;
    }
    public int getDataLen() {
        return dataLen;
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
    public byte[] getToken() {
        return token;
    }
    public void setToken(byte[] token) {
        this.token = token;
    }
    public boolean isComplete() {
        return complete;
    }
    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean isValid(String clientKey) {
        try {
            
            byte[] keyArr = clientKey.getBytes();
            ByteBuffer buffer = ByteBuffer.allocate(dataLen+128);
            buffer.put(this.data);
            buffer.put(keyArr);
            String curToken = MD5Utils.encode(buffer.array());
            String tokenStr = new String(this.getToken());
            
            //PrintStream out = System.out;
            if(tokenStr.equals(curToken)) {
                /*synchronized (out) {
                    out.println("success:");
                    for (byte b : buffer.array()) {
                        out.print(b+",");
                    }
                    out.println();
                }*/
                return true;
            }
            
            /*synchronized (out) {
                out.println("error:");
                for (byte b : buffer.array()) {
                    out.print(b+",");
                }
                out.println();
            }*/
        } catch (Exception e) {
            log.debug("校验时异常："+e.getMessage()+":"+e.getClass().getName());
            //e.printStackTrace();
        }
        
        
        return false;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public byte getCmd() {
        return 0;
    }
    
}
