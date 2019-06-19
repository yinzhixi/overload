package org.iot.client.common;

import java.nio.ByteBuffer;

import org.iot.client.common.decoder.PackHeader;

public interface ClientListener {

    void onData(PackHeader header, ByteBuffer buffer, int bytes);
    
}
