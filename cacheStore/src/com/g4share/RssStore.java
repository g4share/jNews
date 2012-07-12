package com.g4share;

import com.g4share.common.newsItem.*;
import java.io.IOException;

/**
 * User: gm
 */
public class RssStore {
    private final String SYS_COUNT = "/sys/channelCount";

    private final String STORE_DESCRIPTION = "/store/description/";
    private final String STORE_INFO = "/store/info/";
    private final String STORE_DATA = "/store/data/";
    private final String STORE_CATEGORIES = "/store/categories/";


    private MemCachedStore store;

    public RssStore(MemCachedStore store) {
        this.store = store;
    }

    public boolean set(RssChannel[] channels){

        int oldCt;
        try {
            store.set(SYS_COUNT, channels.length);
            oldCt = store.get(SYS_COUNT);
        } catch (IOException e) {
            return false;
        }

        //delete unnecessary items
        for(int i = oldCt; i < channels.length; i++){
            try {
                store.delete(STORE_DESCRIPTION + i);
                store.delete(STORE_INFO + i);
                store.delete(STORE_DATA + i);
                store.delete(STORE_CATEGORIES + i);
            } catch (IOException e) {  } //TODO log
        }

        for(int i = 0; i < channels.length; i++){
            try{
                store.set(STORE_DESCRIPTION + i, channels[i].getDescription());
                store.set(STORE_INFO + i, channels[i].getInfo());
                store.set(STORE_DATA + i, channels[i].getItems());
                store.set(STORE_CATEGORIES + i, channels[i].getCategories());
            } catch (IOException e) {  } //TODO log
        }
        return true;
    }

    public RssChannel[] get(){
        try {
            int ct = store.get(SYS_COUNT);
            RssChannel[] channels = new RssChannel[ct];

            for(int i = 0; i < ct; i++){
                channels[i] = new RssChannel();
                channels[i].setDescription(store.<ChannelDescription>get(STORE_DESCRIPTION + i));
                channels[i].setInfo(store.<ChannelInfo>get(STORE_INFO + i));
                channels[i].setItems(store.<ChannelItem[]>get(STORE_DATA + i));
                channels[i].setCategories(store.<ChannelCategory[]>get(STORE_CATEGORIES + i));
            }

            return channels;
        } catch (IOException e) {
            return null;
        }
    }
}
