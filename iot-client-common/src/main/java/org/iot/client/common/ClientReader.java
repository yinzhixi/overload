package org.iot.client.common;

import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;
import java.nio.channels.InterruptedByTimeoutException;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.ReadPendingException;
import java.nio.channels.ShutdownChannelGroupException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles a cycle of reading / writing on the {@code Client}.
 */
public class ClientReader {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private final DataReader callback;

    public ClientReader(DataReader callback) {
        this.callback = callback;
    }

    public boolean acceptsMessages() {
        return callback.acceptsMessages();
    }

    /**
     * Runs a cycle of doing a beforeRead action and then enqueuing a new read on
     * the client. Handles closed channels and errors while reading. If the client
     * is still connected a new round of actions are called.
     */
    public void run(final Client client) {
        callback.beforeRead(client);
        client.read(new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer buffer) {
                // client.queueLogRecv(buffer, new Date(), client.getClientIp(), null,
                // client.getClientId(), null);
                // if result is negative or zero the connection has been closed or something
                // gone wrong
                if (result < 1) {
                    client.connect();
                } else {
                    callback.onData(client, buffer, result);
                    client.run();
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer buffer) {
                log.debug("读取失败：" + exc.getClass().getName());
                if (exc instanceof ReadPendingException || exc instanceof IllegalArgumentException) {
                } else if (exc instanceof NotYetConnectedException || exc instanceof InterruptedByTimeoutException
                        || exc instanceof ShutdownChannelGroupException) {
                } else {
                }
                client.connect();
            }
        });
    }
}
