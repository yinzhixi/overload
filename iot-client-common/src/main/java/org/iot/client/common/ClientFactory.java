package org.iot.client.common;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;

public class ClientFactory{
    private final ConcurrentHashMap<String,Client> connectMap = new ConcurrentHashMap<String,Client>();
    
    public Client createClient(String serverIp,int port,String clientId,String clientKey, ClientReader clientReader) {
        Client client = new Client(new Connector(),serverIp,port,clientId,clientKey,clientReader);
        connectMap.put(clientId, client);
        return client;
    }
    
    public ConcurrentHashMap<String,Client> getConnectMap(){
        return this.connectMap;
    }

    public void closeClient(Client qcyClient) {
        this.connectMap.remove(qcyClient.getClientId());
    }
    
    public boolean onLine(String connectId) {
        return this.connectMap.containsKey(connectId);
    }
    
    public void sendData(ByteBuffer buffer,String clientId) {
        Client client = this.connectMap.get(clientId);
        if(client != null) {
            client.writeMessage(buffer);
        }else {
            throw new RuntimeException("连接已断开:"+clientId);
        }
    }
    
}
