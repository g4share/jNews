package com.g4share;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * User: gm
 */
@Service
public class MemCachedConnectionInfo {
    private String host;
    private int port;
    private int timeout;


    @Autowired
    public MemCachedConnectionInfo(@Value("${memCached.host}") String host,
                                   @Value("${memCached.port}") int port,
                                   @Value("${memCached.timeout}") int timeout) {
        this.host = host;
        this.port = port;
        this.timeout = timeout;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getTimeout() {
        return timeout;
    }
}

