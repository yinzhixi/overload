package org.iot.client.common.utils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PackUtils {
    private static Logger log = LoggerFactory.getLogger(PackUtils.class);
    public static ByteBuffer wrap(String clientId,ByteBuffer data,byte cmd,String key) throws UnsupportedEncodingException {
        int clientIdLen = clientId.length();
        int keyLen = 32;
        byte s0 = (byte)0xaa;
        byte s1 = (byte)0xaa;
        byte[] dataLen = TransUtils.longToBytes(data.limit());
        byte[] clientIdLenBs = TransUtils.longToBytes(clientIdLen);
        byte e0 = (byte)0xee;
        byte e1 = (byte)0xee;
        ByteBuffer nwData = ByteBuffer.allocate(data.limit()+9+4+clientIdLen+keyLen);
        nwData.put(s0).put(s1).put(cmd).put(dataLen).put(data.array()).put(clientIdLenBs).
        put(clientId.getBytes("UTF-8"));
        TokenUtil.appendToken(nwData, key);
        nwData.put(e0);
        nwData.put(e1);
        return nwData;
    }
    public static ByteBuffer wrap(String clientId,byte[] data,int len,byte cmd,String key) throws UnsupportedEncodingException {
        ByteBuffer dataBuffer = ByteBuffer.allocate(len);
        dataBuffer.put(data, 0, len);
        dataBuffer.flip();
        int clientIdLen = clientId.length();
        int keyLen = 32;
        byte s0 = (byte)0xaa;
        byte s1 = (byte)0xaa;
        byte[] dataLen = TransUtils.longToBytes(len);
        byte[] clientIdLenBs = TransUtils.longToBytes(clientIdLen);
        byte e0 = (byte)0xee;
        byte e1 = (byte)0xee;
        ByteBuffer nwData = ByteBuffer.allocate(len+9+4+clientIdLen+keyLen);
        nwData.put(s0).put(s1).put(cmd).put(dataLen).put(dataBuffer.array()).put(clientIdLenBs).
        put(clientId.getBytes("UTF-8"));
        TokenUtil.appendToken(nwData, key);
        nwData.put(e0);
        nwData.put(e1);
        return nwData;
    }
    /*
    public static Pack unWrap(Pack pack,String clientKey) {
        if(pack.getCmd() == 0x0e) {//服务转发包
            Pack nwPack = new Pack();
            ByteBuffer buffer = ByteBuffer.allocate(pack.getDataLen());
            buffer.put(pack.getData());
            buffer.flip();
            try {
                nwPack.readPackFromByteBuffer("test",buffer, pack.getDataLen());
                boolean isValid = nwPack.isValid(clientKey);
                if(isValid) {
                    return nwPack;
                }else {
                    return null;
                }
            } catch (Exception e) {
                log.debug("unWrap Exception:"+e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }*/
}
