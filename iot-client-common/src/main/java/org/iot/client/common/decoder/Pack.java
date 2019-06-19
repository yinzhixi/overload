package org.iot.client.common.decoder;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import org.iot.client.common.utils.MD5Utils;
import org.iot.client.common.utils.TransUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class Pack {
    private static Logger log = LoggerFactory.getLogger(Pack.class);
    private int startMark1;
    private int version = -1;//协议版本
    private int hLen = -1;//头数据长度
    private byte[] hdata;
    private int hoffset = 0;
    private boolean completed = false;
    private PackHeader header;
    private String headKey;
    private byte[] htoken;
    
    private byte[] contentLenBytes;
    private int contentLenOffset = 0;
    private int contentLen = 0;
    private ByteBuffer dataBuffer;
    
    private byte[] contentBytes;
    private int contentOffset = 0;
    
    private byte[] tokenBytes;
    private int tokenOffset = 0;
    
    private long readed;
    private long areaded;
    
    public void readPackFromByteBuffer(String clientId,ByteBuffer buffer) throws Exception {
        int position = 0;
        while(buffer.hasRemaining()) {
            position = buffer.position();
            //读取开始
            if(buffer.hasRemaining() && !this.startMark1IsComplete()) {
                byte start = buffer.get();
                int s1 = Byte.toUnsignedInt(start);
                this.setStartMark1(s1);
                if(!this.startMark1IsComplete()) {
                    this.setStartMark1((byte)-1);
                    throw new Exception(String.format("错误包头:%x",start));
                }
            }
            
            //读取版本
            if(this.startMark1IsComplete() && buffer.hasRemaining() && !this.versionIsComplete()) {
                byte v = buffer.get();
                this.version = Byte.toUnsignedInt(v);
                log.debug(clientId+":已读取版本："+this.version);
            }
            
            //读取包头长度
            if(this.versionIsComplete() && buffer.hasRemaining() && !this.hLenIsComplete()) {
                byte len = buffer.get();
                this.hLen = Byte.toUnsignedInt(len);
                log.debug(clientId+":已读取头长度："+this.hLen);
            }
            
            //读取包头数据
            if(this.hLenIsComplete() && buffer.hasRemaining() && !this.headerIsComplete()) {
                if(this.hdata == null) {
                    this.hdata = new byte[this.getHLen()];
                }
                int remain = this.getHLen() - this.hoffset;
                if(buffer.remaining() >= remain) {
                    buffer.get(this.hdata,this.hoffset,remain);
                    this.hoffset = this.hoffset + remain;
                    String headerJSON = new String(hdata,Charset.forName("UTF-8"));
                    log.debug(clientId+":已读取头数据1："+headerJSON);
                    //json对象转Map
                    header = JSON.parseObject(headerJSON, PackHeader.class);
                }else {
                    int remain_bf = buffer.remaining();
                    buffer.get(this.hdata,this.hoffset,remain_bf);
                    log.debug(clientId+":已读取头数据0："+remain_bf);
                    this.hoffset = this.hoffset + remain_bf;
                }
            }
            
            //读取4字节内容长度
            if(this.headerIsComplete() && buffer.hasRemaining() && !this.contentLenIsComplete()) {
                if(this.contentLenBytes == null) {
                    this.contentLenBytes = new byte[4];
                }
                int remain = 4 - this.contentLenOffset;
                if(buffer.remaining() >= remain) {
                    buffer.get(this.contentLenBytes,this.contentLenOffset,remain);
                    this.contentLen = ((int)TransUtils.bytesToLong(contentLenBytes[0], contentLenBytes[1], contentLenBytes[2], contentLenBytes[3]));
                    log.debug(clientId+":已读取数据长度1："+contentLen);
                    this.contentLenOffset = this.contentLenOffset + remain;
                    //分配包缓存
                    this.dataBuffer = ByteBuffer.allocate(this.contentLen + this.hLen + 7 + 128);
                    this.dataBuffer.put((byte)this.getStartMark1());
                    this.dataBuffer.put((byte)this.getVersion());
                    this.dataBuffer.put((byte)this.getHLen());
                    this.dataBuffer.put(this.getHdata());
                    this.dataBuffer.put(this.contentLenBytes);
                }else {
                    int remain_bf = buffer.remaining();
                    buffer.get(this.contentLenBytes,this.contentLenOffset,remain_bf);
                    log.debug(clientId+":已读取数据长度0："+contentLen);
                    this.contentLenOffset = this.contentLenOffset + remain_bf;
                }
            }
            
            //读取内容
            if(this.contentLenIsComplete() && buffer.hasRemaining() && !this.contentIsComplete()) {
                if(this.contentBytes == null) {
                    this.contentBytes = new byte[this.contentLen];
                }
                int remain = this.contentLen - this.contentOffset;
                if(buffer.remaining() >= remain) {
                    buffer.get(this.contentBytes,this.contentOffset,remain);
                    log.debug(clientId+":已读取数据1："+remain);
                    this.contentOffset = this.contentOffset + remain;
                    this.dataBuffer.put(this.contentBytes);
                }else {
                    int remain_bf = buffer.remaining();
                    buffer.get(this.contentBytes,this.contentOffset,remain_bf);
                    log.debug(clientId+":已读取数据0："+remain);
                    this.contentOffset = this.contentOffset +remain_bf;
                }
            }
            
            //token
            if(this.contentIsComplete() && buffer.hasRemaining() && !this.tokenIsComplete()) {
                if(this.tokenBytes == null) {
                    this.tokenBytes = new byte[32];
                }
                int remain = 32 - this.tokenOffset;
                if(buffer.remaining() >= remain) {
                    buffer.get(this.tokenBytes,this.tokenOffset,remain);
                    this.tokenOffset = this.tokenOffset + remain;
                    this.setCompleted(true);
                    log.debug(clientId+":已读取TOKEN1："+new String(this.tokenBytes));
                    return;
                }else {
                    int remain_bf = buffer.remaining();
                    buffer.get(this.tokenBytes,this.tokenOffset,remain_bf);
                    log.debug(clientId+":已读取TOKEN0："+remain_bf);
                    this.tokenOffset = this.tokenOffset + remain_bf;
                }
            }
            
            if(position == buffer.position()) {
                throw new Exception("包解析错误：未读取数据");
            }
        }
    }

    private boolean tokenIsComplete() {
        return this.tokenOffset == 32;
    }

    private boolean contentIsComplete() {
        return this.contentOffset == this.contentLen;
    }

    private boolean contentLenIsComplete() {
        return this.contentLenBytes != null && this.contentLenOffset == 4;
    }

    public byte[] getContentBytes() {
        return contentBytes;
    }

    public boolean isValid(String clientKey) {
        try {
            /*byte[] keyArr = clientKey.getBytes();
            ByteBuffer buffer = ByteBuffer.allocate(this.gethLen()+131);
            buffer.put((byte)this.getStartMark1());
            buffer.put((byte)this.getVersion());
            buffer.put((byte)this.gethLen());
            buffer.put(this.hdata);
            buffer.put(keyArr);
            String curToken = MD5Utils.encode(buffer.array());
            String tokenStr = new String(htoken);
            
            if(tokenStr.equals(curToken)) {
                return true;
            }*/
            
            byte[] keyArr = clientKey.getBytes();
            int keyLen = keyArr.length;
            
            this.dataBuffer.put(keyArr);
            byte[] src = dataBuffer.array();
            
            String curToken = MD5Utils.encode(src);
            
            String tokenStr = new String(this.tokenBytes);
            
            if(tokenStr.equals(curToken)) {
              //覆盖
                this.dataBuffer.position(this.dataBuffer.limit() - keyLen);
                int overBytes = keyLen - this.tokenBytes.length;
                if(overBytes > 0 ) {
                    byte[] blank = new byte[overBytes];
                    this.dataBuffer.put(blank,0,overBytes);
                }
                this.dataBuffer.flip();
                this.dataBuffer.limit(this.dataBuffer.limit() - keyLen + tokenBytes.length);
                return true;
            }else {
                log.debug(String.format("校验失败 发送TOKEN %s ,计算TOKEN %s",tokenStr,curToken));
                log.debug(new String(this.contentBytes));
                dataBuffer.position(0);
                StringBuilder sb = new StringBuilder();
                while(dataBuffer.hasRemaining()) {
                    sb.append(String.format("%02x ",dataBuffer.get()));
                }
                log.debug(sb.toString());
            }
        } catch (Exception e) {
            log.debug("校验时异常："+e.getMessage()+":"+e.getClass().getName());
        }
        return false;
    }
    
    public int getContentLen() {
        return contentLen;
    }

    public static void main(String[] args) throws Exception {
        byte[] data = new byte[] {
                -86,-86,
                1,
                8,0,0,0,
                1,2,3,4,5,6,7,8,
                5,0,0,0,
                0,0,0,0,1,
                1,2,3,4,5,6,7,8,9};
        ByteBuffer buffer = ByteBuffer.allocate(data.length);
        buffer.put(data);
        buffer.flip();
    }
    
    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int gethLen() {
        return hLen;
    }

    private boolean hLenIsComplete() {
        return this.getHLen() != -1;
    }
    
    private boolean versionIsComplete() {
        return this.getVersion() != -1;
    }
    
    private boolean startMark1IsComplete() {
        int s = 0xAA;
        return this.getStartMark1() == s;
    }
    
    private boolean headerIsComplete() {
        return this.header != null;
    }
    
    public int getStartMark1() {
        return startMark1;
    }
    public void setStartMark1(int startMark1) {
        this.startMark1 = startMark1;
    }
    
    public int getHLen() {
        return hLen;
    }
    public void setHLen(byte hLen) {
        this.hLen = hLen;
    }
    
    public byte[] getHdata() {
        return hdata;
    }

    public String getHeadKey() {
        return headKey;
    }

    public byte[] getToken() {
        return htoken;
    }

    public int getVersion() {
        return version;
    }

    public PackHeader getHeader() {
        return header;
    }

    public long getReaded() {
        return readed;
    }

    public void setReaded(long readed) {
        this.readed = readed;
    }

    public long getAreaded() {
        return areaded;
    }

    public void setAreaded(long areaded) {
        this.areaded = areaded;
    }

    public ByteBuffer getDataBuffer() {
        return dataBuffer;
    }

    
}
