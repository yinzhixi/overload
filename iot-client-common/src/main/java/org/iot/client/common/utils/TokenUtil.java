package org.iot.client.common.utils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class TokenUtil {
    public static void appendToken(ByteBuffer buffer,String key) throws UnsupportedEncodingException {
        int len = buffer.position();
        buffer.position(0);
        byte[] ar = new byte[len];
        buffer.get(ar, 0, len);
        String decrypt = MD5Utils.encode(ar,key.getBytes("UTF-8"));
        buffer.put(decrypt.getBytes("UTF-8"));
    }
}
