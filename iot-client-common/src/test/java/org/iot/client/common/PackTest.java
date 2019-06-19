package org.iot.client.common;

import java.nio.ByteBuffer;

import org.iot.client.common.utils.MD5Utils;
import org.iot.client.common.utils.TransUtils;
import org.junit.Test;

import junit.framework.TestCase;

public class PackTest extends TestCase {

    public PackTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    @Test
    public void testReadPackFromByteBuffer() {}
    
    /**
     * 测试数据包长度为零时
     */
    @Test
    public void testIsValid() {}
    
    private void inputPack(ByteBuffer buffer,byte[] data) {
        buffer.put((byte)0xAA);
        buffer.put((byte)0xAA);
        buffer.put((byte)22);
        long  len = data.length;
        byte[] lenBs = TransUtils.longToBytes(len);
        buffer.put(lenBs);
        String clientId = "9000013";
        len = clientId.length();
        buffer.put(TransUtils.longToBytes(len));
        buffer.put(clientId.getBytes());
        int position = buffer.position();
        byte[] clientKeyBs = "2a0142f066a8182386602775b14eb6c534dd0ee4e8f4a79abf97976a488d66da4d2b6b734d43e7282f9bdc7e9ec46ee3b9f412602c655fe53250ce0bbeb53643".getBytes();
        buffer.put(clientKeyBs);
        
        String curToken = MD5Utils.encode(buffer.array());
        buffer.position(position);
        buffer.put(curToken.getBytes());
        buffer.put((byte)0xee);
        buffer.put((byte)0xee);
    }
    
    /**
     * 测试非正常数据包
     */
    @Test
    public void testInvalidPack() {}

}
