package com.g4share.loader.rss;

import com.g4share.common.newsItem.RssChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * User: gm
 */
public class ChannelCollection {
    private List<RssChannel> channels = new ArrayList<>();

    public void Add(RssChannel channel){
        channels.add(channel);
    }

    public RssChannel[] getChannels() {
        return channels.toArray(new RssChannel[channels.size()]);
    }
}
