package com.g4share.loader;

import com.g4share.common.newsItem.ChannelDescription;
import com.g4share.common.download.UrlDownloaderFactory;
import com.g4share.loader.rss.ChannelProcessor;

/**
 * User: gm
 */
public class Worker {
    private ChannelDescription[] channelsDescriptions;
    private NewsDownloader newsDownloader;
    private UrlDownloaderFactory downloaderFactory;
    private ChannelProcessor processor;


    public Worker(ChannelDescription[] channelsDescriptions,
                  ChannelProcessor processor,
                  UrlDownloaderFactory downloaderFactory) {
        this.channelsDescriptions = channelsDescriptions;
        this.processor = processor;
        this.downloaderFactory = downloaderFactory;
    }

    public void GetNews(){
        newsDownloader = new NewsDownloader(downloaderFactory,
                channelsDescriptions,
                processor);
        newsDownloader.GetNews();

    }
}
