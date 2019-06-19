package com.jm.websocket;

import org.iot.client.common.PackInterface;
import org.iot.client.common.utils.ImgUtils;
import org.iot.client.common.utils.TransUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class STCWeightDecoder {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public STCWeight decode(PackInterface pack, String clientId, String picPath) throws Exception {
        
        if(pack.getCmd() == 0x01) {
            return this.decodeRuntimeData(pack,clientId,picPath);
        }
        if(pack.getCmd() == 0x02) {
            return this.decodeValideData(pack,clientId,picPath);
        }
        return null;
    }
    
    private STCWeight decodeValideData(PackInterface pack, String clientId, String picPath) throws Exception {
        byte[] data = pack.getData();
        ByteBuffer dataBuf = ByteBuffer.wrap(data);
        byte[] headLenBs = new byte[4];
        dataBuf.get(headLenBs, 0, 4);
        int headLen = (int)TransUtils.bytesToLongLH(headLenBs);
        if(headLen > 256) {
            throw new Exception("头长度错误");
        }
        
        byte[] headBs= new byte[headLen];
        dataBuf.get(headBs, 0, headLen);
        
        ByteBuffer headBuffer = ByteBuffer.wrap(headBs);
        Charset cs = Charset.forName ("GBK");
        CharBuffer cb = cs.decode(headBuffer);
        String msg = cb.toString();
        String[] items = msg.split("\\|");
        int len = items.length;
        String head = len > 0 ? items[0] : "";
        String frontPic = len > 1 ? items[1] : "";
        String backPic = len > 2 ? items[2] : "";
        String time = len > 3 ? items[3] : "";
        
        STCWeight weight = new STCWeight();
        weight.setType(head.substring(0, 1));
        String weightStr = head.substring(3,head.length()).replaceAll(" ", "");
        weight.setWeight(Long.parseLong(weightStr));
        
        if(head.startsWith("d")) {//有效数据 
            String path = ImgUtils.getRelativePath(time,clientId);
            weight.setFrontPic(path+frontPic);
            weight.setBackPic(path+backPic);
            weight.setWeightTime(time);
            String fullPath = picPath + path;
            
            byte[] frontPicLenBs = new byte[4];
            dataBuf.get(frontPicLenBs,0,4);
            int frontPicLen = (int)TransUtils.bytesToLongLH(frontPicLenBs);
            savePic(fullPath, data, headLen + 8, frontPicLen, frontPic);
            dataBuf.position(dataBuf.position()+frontPicLen);
            byte[] backPicLenBs = new byte[4];
            dataBuf.get(backPicLenBs ,0,4);
            int backPicLen = (int)TransUtils.bytesToLongLH(backPicLenBs);
            savePic(fullPath, data, headLen + frontPicLen + 12, backPicLen, backPic);
        }
        return weight;
    }

    private STCWeight decodeRuntimeData(PackInterface pack, String clientId, String picPath) throws Exception {
        byte[] data = pack.getData();
        ByteBuffer dataBuf = ByteBuffer.wrap(data);
        Charset cs = Charset.forName ("GBK");
        CharBuffer cb = cs.decode(dataBuf);
        String msg = cb.toString();
        
        STCWeight weight = new STCWeight();
        weight.setType(msg.substring(0, 1));
        String weightStr = msg.substring(3,msg.length()).replaceAll(" ", "");
        weight.setWeight(Long.parseLong(weightStr));
        return weight;
    }

    private void savePic(String fullPath,byte[] data,int offset,int lenght,String fileName) {
        File dir = new File(fullPath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        
        String fullName = fullPath + fileName;

        DataOutputStream fileOut = null;
        try {
            fileOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fullName,false)));
            fileOut.write(data, offset, lenght);
            fileOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fileOut != null) {
                try {
                    fileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        logger.debug("accepting: "+fullName);
    }
    
}
