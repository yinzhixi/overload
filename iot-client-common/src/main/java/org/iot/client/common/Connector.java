package org.iot.client.common;

import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Connector {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public volatile boolean connecting = false;

    /**
     * 连接到服务
     * 
     * @param serverIp
     * @param port
     * @return
     */
    public void connect(Client client) {
        String clientId = client.getClientId();
        String serverIp = client.getServerIp();
        int port = client.getPort();
        
        //正在连接 或者已连接
        if (this.connecting || (client.getChannel() != null && client.getChannel().isOpen())) {
            return ;
        }
        //连接
        AsynchronousSocketChannel channel = null;
        this.connecting = true;
        do {
            try {
                channel = AsynchronousSocketChannel.open();
                channel.setOption(StandardSocketOptions.TCP_NODELAY, true);
                client.setChannel(channel);
                logger.debug(String.format("正在连接：%s:%s:%d ...",clientId, serverIp, port));
                Future<Void> future = channel.connect(new InetSocketAddress(serverIp, port));
                if (future.get() == null) {
                    logger.debug(String.format("连接成功：%s:%s:%d ...", clientId,serverIp, port));
                } else {
                    logger.debug(String.format("连接失败：%s:%s:%d ...", clientId,serverIp, port));
                }
            } catch (Exception e) {
                logger.debug(String.format("连接失败：%s:%s:%d error %s", clientId,serverIp, port,e.getMessage()));
            }
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (client.getChannel() == null || !client.getChannel().isOpen());
        
        this.connecting = false;
    }

}
