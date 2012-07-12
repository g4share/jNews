package com.g4share.persistentStore.pojo;

import com.g4share.common.newsItem.ChannelDescription;

/**
 * User: gm
 */
public class RssLocationConverter {
    private RssLocation[] locations;

    public RssLocationConverter(RssLocation[] locations) {
        this.locations = locations;
    }

    public ChannelDescription[] toChannelsDescription(){
        ChannelDescription[] channels = new ChannelDescription[locations.length];
        for(int i = 0; i < locations.length; i++){
            channels[i] = new ChannelDescription(
                    locations[i].getName(),
                    locations[i].getUrl(),
                    locations[i].isEnabled());
        }
        return channels;
    }

    public static  RssLocation fromChannelsDescription(ChannelDescription channelDescription){
        RssLocation rssLocation = new RssLocation();
        rssLocation.setName(channelDescription.getName());
        rssLocation.setUrl(channelDescription.getUrl());
        rssLocation.setEnabled(channelDescription.isEnabled());
        return rssLocation;
    }
}