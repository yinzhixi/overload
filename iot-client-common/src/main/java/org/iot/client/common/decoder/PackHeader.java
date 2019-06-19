package org.iot.client.common.decoder;

public class PackHeader {
    private String from;
    private String dest;
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getDest() {
        return dest;
    }
    public void setDest(String dest) {
        this.dest = dest;
    }
    @Override
    public String toString() {
        return "from:"+from+",dest:"+dest;
    }
    
}
