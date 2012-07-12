package com.g4share;

import java.io.IOException;

/**
 * User: gm
 */
public class MemCachedStore  {
    private String nameSpace = "";

    private MemCachedConnector connector;

    public MemCachedStore(MemCachedConnector connector) throws IOException {
        this.connector = connector;
        this.connector.connect();
    }

    public MemCachedStore(MemCachedConnector connector, String nameSpace) throws IOException {
        this.connector = connector;
        this.nameSpace = (nameSpace == null || nameSpace.trim().equals("") ? "" : nameSpace.trim()) + "/";
        this.connector.connect();
    }

    public <T> void set(String key, T item) throws IOException {
        connector.getClient().set(getKey(key), 0, item);
    }

    public void delete(String key) throws IOException {
        connector.getClient().delete(getKey(key));
    }


    public <T> T get(String key) throws IOException {
        return (T)connector.getClient().get(getKey(key));
    }


    private String getKey(String key){
        return nameSpace + key;
    }
}
