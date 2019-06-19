package org.iot.client.common;

import java.nio.ByteBuffer;

public interface PackInterface {

    byte[] getData();

    byte getCmd();

    int getDataLen();

    void readPackFromByteBuffer(String clientId, ByteBuffer buffer, long bytes)throws Exception;

    boolean isComplete();

    boolean isValid();

}
