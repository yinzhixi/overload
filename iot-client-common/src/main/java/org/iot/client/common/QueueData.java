package org.iot.client.common;

import java.nio.ByteBuffer;

public class QueueData {
    private Client from;
    private Client to;
    private ByteBuffer buffer;
    private int bytes;
    public Client getFrom() {
        return from;
    }
    public void setFrom(Client from) {
        this.from = from;
    }
    public Client getTo() {
        return to;
    }
    public void setTo(Client to) {
        this.to = to;
    }
    public ByteBuffer getBuffer() {
        return buffer;
    }
    public void setBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
    }
    public int getBytes() {
        return bytes;
    }
    public void setBytes(int bytes) {
        this.bytes = bytes;
    }
    
}
