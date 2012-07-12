package com.g4share;

import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * User: gm
 */
@Service
public class MemCachedConnector {
    private MemCachedConnectionInfo connectionInfo;

    private InetSocketAddress socket;
    private MemcachedClient client;


    @Autowired
    public MemCachedConnector(MemCachedConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

    public int getTimeout() {
        return connectionInfo.getTimeout();
    }

    public void connect() throws IOException {
        if (socket == null){
            socket = new InetSocketAddress(connectionInfo.getHost(), connectionInfo.getPort());
        }
        if (client == null){
            client = new MemcachedClient(socket);
        }
    }

    public MemcachedClient getClient() throws IOException {
        connect();
        return client;
    }

    public void close(){
        if (client != null) {
            client.shutdown();
            client = null;
            System.out.println("client shutdown");
        }
        if (socket != null) {
            socket = null;
        }
    }
}
