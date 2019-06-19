package org.iot.client.common;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileLock;
import java.nio.channels.InterruptedByTimeoutException;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.ShutdownChannelGroupException;
import java.nio.channels.WritePendingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

import org.iot.client.common.decoder.Pack;
import org.iot.client.common.utils.Const;
import org.iot.client.common.utils.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * Client represents a remote connection to the server. It contains methods for
 * reading and writing messages from the channel.
 *
 * All reads and writes are asynchronous and uses the nio2 asynchronous
 * elements.
 */
public class Client {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private AtomicReference<ClientReader> reader;
    private volatile AsynchronousSocketChannel channel;
    private String clientId;
    private String clientKey;
    private final Queue<PackInterface> readQueue = new LinkedList<PackInterface>();
    private final Queue<QueueData> writeQueue = new LinkedList<QueueData>();
    private final String serverIp;
    private final int port;
    private boolean writing = false;
    private Connector connector;
    private long timeOut = 10;//读写超时时间，单位为分钟
    public Client(Connector connector, String serverIp, int port, String clientId, String clientKey,
            ClientReader reader) {
        this.connector = connector;
        this.serverIp = serverIp;
        this.port = port;
        this.reader = new AtomicReference<ClientReader>(reader);
        this.clientKey = clientKey;
        this.clientId = clientId;
        this.connect();
    }

    public String getServerIp() {
        return serverIp;
    }

    public int getPort() {
        return port;
    }

    public AsynchronousSocketChannel getChannel() {
        return channel;
    }

    public void setChannel(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }

    /**
     * Enqueues a write of the buffer to the channel. The call is asynchronous so
     * the buffer is not safe to modify after passing the buffer here.
     *
     * @param buffer
     *            the buffer to send to the channel
     */
    public void writeMessage(final ByteBuffer buffer) {
        boolean threadShouldWrite = false;

        synchronized (this.writeQueue) {
            QueueData data = new QueueData();
            data.setFrom(this);
            data.setBuffer(buffer);
            this.writeQueue.add(data);
            // Currently no thread writing, make this thread dispatch a write
            if (!writing) {
                writing = true;
                threadShouldWrite = true;
            }
        }

        if (threadShouldWrite) {
            writeFromQueue();
        }
    }

    private void writeFromQueue() {
        ByteBuffer buffer = null;
        synchronized (this.writeQueue) {
            QueueData data = this.writeQueue.poll();
            if (data != null) {
                buffer = data.getBuffer();
            }
            if (buffer == null) {
                writing = false;
            }
        }

        // No new data in buffer to write
        if (writing) {
            writeBuffer(buffer);
        }
    }
    
    private void writeBuffer(ByteBuffer buffer) {
        AsynchronousSocketChannel channel = this.getChannel();
        CompletionHandler<Integer, ByteBuffer> handler = new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer buffer) {
                logger.debug(clientId+"写字节数："+result);
                if (result < 1) {
                    connect();
                }
                if (buffer.hasRemaining()) {
                    writeBuffer(buffer);
                } else {
                    writeFromQueue();
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer buffer) {
                logger.debug("发送失败：" + exc.getMessage());
                
                if (exc instanceof WritePendingException) {
                } else if (exc instanceof NotYetConnectedException || exc instanceof InterruptedByTimeoutException
                        || exc instanceof ShutdownChannelGroupException) {
                    connect();
                } else {
                    connect();
                }
                
                if(buffer.hasRemaining()) {
                    writeBuffer(buffer);
                }else {
                    writeFromQueue();
                }
            }
        };
        
        try {
            channel.write(buffer, buffer, handler);
        } catch (Throwable exc) {
            //exc.printStackTrace();
            logger.debug("发送时异常：" + exc.getMessage());
            /*if (exc instanceof WritePendingException) {
                if(buffer.hasRemaining()) {
                    writeBuffer(buffer);
                }
            } else if (exc instanceof NotYetConnectedException || exc instanceof InterruptedByTimeoutException
                    || exc instanceof ShutdownChannelGroupException) {
                reconnect();
                if(buffer.hasRemaining()) {
                    writeBuffer(buffer);
                }
            } else {
                reconnect();
                if(buffer.hasRemaining()) {
                    writeBuffer(buffer);
                }
            }*/
        }
    }
    
    public void sendBeatHeart() {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        buffer.put((byte)1);
        buffer.flip();
        writeMessage(buffer);
    }
    
    private volatile boolean connecting = false;
    public void connect() {
        if(connecting) {
            return;
        }
        connecting = true;
        try {
            close();
            this.connector.connect(this);
            writing = false;
            //Thread.sleep(2000);
            sendClientInfo();
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("连接发送客户信息时异常：" + e.getMessage());
        }finally {
            connecting = false;
        }
        run();
    }

    /**
     * Enqueue a read
     * 
     * @param completionHandler
     *            callback on completed read
     */
    public void read(CompletionHandler<Integer, ? super ByteBuffer> completionHandler) {
        try {
            ByteBuffer input = ByteBuffer.allocate(Const.PACK_PER_LEN);
            getChannel().read(input, input, completionHandler);
        } catch (Throwable exc) {
            //exc.printStackTrace();
            logger.debug("读取时异常：" + exc.getClass().getName());
            /*if (exc instanceof ReadPendingException || exc instanceof IllegalArgumentException) {
                run();
            } else if (exc instanceof NotYetConnectedException || exc instanceof InterruptedByTimeoutException
                    || exc instanceof ShutdownChannelGroupException) {
                reconnect();
            } else {
                reconnect();
            }*/
        }
    }

    public ByteBuffer allacateBuffer(int size) {
        return ByteBuffer.allocate(size);
    }

    /**
     * Closes the channel
     */
    public void close() {
        try {
            if (getChannel() != null && getChannel().isOpen()) {
                getChannel().close();
                logger.debug(clientId+":连接断开");
            }
        } catch (Exception e) {
            logger.debug("close exception:" + e.getMessage());
            //e.printStackTrace();
        }
    }

    /**
     * Run the current states actions.
     */
    public void run() {
        reader.get().run(this);
    }

    public void setReader(ClientReader reader) {
        if(this.reader == null) {
            this.reader = new AtomicReference<ClientReader>(reader);
        }else {
            this.reader.set(reader);
        }
    }

    public String getClientIp() {
        if (getChannel() == null) {
            return "";
        }
        SocketAddress add = null;
        try {
            add = getChannel().getRemoteAddress();
        } catch (IOException e) {
            logger.debug("getClientIp exception:" + e.getMessage());
            //e.printStackTrace();
        }
        if (add != null) {
            return add.toString();
        }
        return null;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void readPackToQueue(PackInterface pack) {
        synchronized (this.readQueue) {
            readQueue.add(pack);
            logger.debug(pack.getCmd() + "->" + pack.getDataLen() + "-> Queue");
        }
    }

    public PackInterface readQueuePoll() {
        PackInterface pack = null;
        synchronized (readQueue) {
            pack = readQueue.poll();
        }
        return pack;
    }

    private String fileFullName = null;
    private Long readed = new Long(0), total = new Long(0);
    private RandomAccessFile fileOut = null;
    private FileLock fileLock = null;

    public String getFileFullName() {
        return fileFullName;
    }

    public void setFileFullName(String fileFullName) {
        this.fileFullName = fileFullName;
    }

    public Long getReaded() {
        return readed;
    }

    public void setReaded(Long readed) {
        this.readed = readed;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public RandomAccessFile getFileOut() {
        return fileOut;
    }

    public void setFileOut(RandomAccessFile fileOut) {
        this.fileOut = fileOut;
    }

    public void sendClientInfo() throws UnsupportedEncodingException {
        // 准备数据
        Map<String, Object> header = new HashMap<String, Object>();
        header.put("from", this.clientId);
        header.put("dest", "*");
        
        String json = JSON.toJSONString(header);
        int dtLen = json.length(), keyLen = 32,
                packLen = 3 + dtLen + 4 + keyLen+2;
        
        ByteBuffer buffer = ByteBuffer.allocate(packLen);
        
        // begin
        buffer.put((byte) 0xaa);
        buffer.put((byte) 0xaa);
        buffer.put((byte) dtLen);
        buffer.put(json.getBytes("UTF-8"));
        buffer.put((byte) 0x00);
        buffer.put((byte) 0x00);
        buffer.put((byte) 0x00);
        buffer.put((byte) 0x00);

        // 放入token
        TokenUtil.appendToken(buffer, clientKey);

        // end
        buffer.put((byte) 0xee);
        buffer.put((byte) 0xee);
        buffer.flip();
        this.writeMessage(buffer);
    }

    public FileLock getFileLock() {
        return fileLock;
    }

    public void setFileLock(FileLock fileLock) {
        this.fileLock = fileLock;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }
    
    private final List<ClientListener> listeners = new CopyOnWriteArrayList<ClientListener>();
    
    //输出
    public void fireListeners(Pack pack) {
        ByteBuffer buffer = ByteBuffer.wrap(pack.getContentBytes());
        int bytes = pack.getContentLen();
        for (ClientListener listener : listeners) {
            ByteBuffer bufferShare = buffer.duplicate();
            listener.onData(pack.getHeader(),bufferShare,bytes);
        }
    }
    
    public void addListener(ClientListener listener) {
        this.listeners.add(listener);
    }
    
}
