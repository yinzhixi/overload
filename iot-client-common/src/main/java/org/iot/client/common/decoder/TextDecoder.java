package org.iot.client.common.decoder;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import org.iot.client.common.PackInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextDecoder {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    public String decode(PackInterface pack, String charsetName) {
        ByteBuffer buffer = ByteBuffer.wrap(pack.getData());
        Charset cs = Charset.forName (charsetName);
        CharBuffer cb = cs.decode(buffer);
        String msg = cb.toString();
        log.debug(String.format("解析数据:%s", msg));
        return msg;
    }

}
