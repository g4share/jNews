package com.g4share.daemon;

import com.g4share.MemCachedConnector;
import com.g4share.MemCachedStore;
import com.g4share.RssStore;
import com.g4share.common.newsItem.ChannelDescription;
import com.g4share.common.newsItem.RssChannel;
import com.g4share.common.download.UrlDownloaderFactory;
import com.g4share.loader.Worker;
import com.g4share.loader.rss.ChannelCollection;
import com.g4share.loader.rss.ChannelProcessor;
import com.g4share.loader.rss.RssReaderFactory;
import com.g4share.persistentStore.service.PersistentStoreProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Date;

/**
 * User: gm
 */
@Service
public class DownloadProcessor implements Processor{
    private ChannelCollection channelCollection;
    private RssStore rssStore;

    private MemCachedStore  memCachedStore;
    private MemCachedConnector connector;

    private PersistentStoreProxy persistentStoreProxy;


    @Autowired
    public DownloadProcessor(MemCachedConnector connector, PersistentStoreProxy persistentStoreProxy) throws IOException {
        this.connector = connector;
        memCachedStore = new MemCachedStore(this.connector);
        this.persistentStoreProxy = persistentStoreProxy;
    }


    public void process() {
        try {
            channelCollection = new ChannelCollection();
            ChannelDescription[] channelDescription = persistentStoreProxy.getEnabledChannels();
            com.g4share.loader.Worker worker = new Worker(channelDescription,
                    new ChannelProcessor(new RssReaderFactory(), channelCollection),
                    new UrlDownloaderFactory());

            rssStore = new RssStore(
                    memCachedStore);


            try{
                worker.GetNews();
            }
            finally {
                connector.close();
            }

            RssChannel[] channels = channelCollection.getChannels();
            rssStore.set(channels);

            System.out.println("set channels at "  + new Date());

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }
}