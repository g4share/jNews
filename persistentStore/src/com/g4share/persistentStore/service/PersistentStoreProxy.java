package com.g4share.persistentStore.service;

import com.g4share.common.newsItem.ChannelDescription;

public interface PersistentStoreProxy {
    public ChannelDescription[] getEnabledChannels();
    public ChannelDescription[] getChannels();

    public void setChannelStatus(String name, boolean isEnabled);
    public void setChannel(ChannelDescription channel);
    public void removeChannel(String name);
}