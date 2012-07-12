package com.g4share.loader;

import com.g4share.common.newsItem.ChannelDescription;
import com.g4share.common.download.UrlDownloader;
import com.g4share.common.download.UrlDownloaderFactory;
import com.g4share.loader.rss.ChannelProcessor;
import java.io.IOException;

/**
 * User: gm
 */
public class NewsDownloader {
    private UrlDownloaderFactory downloaderFactory;
    private ChannelDescription[] channels;
    private ChannelProcessor processor;


    public NewsDownloader(UrlDownloaderFactory downloaderFactory,
                          ChannelDescription[] channels,
                          ChannelProcessor processor) {
        this.downloaderFactory = downloaderFactory;
        this.channels = channels;
        this.processor = processor;
    }

    public void GetNews() {
        for(ChannelDescription channel : channels){
            startDownload(downloaderFactory, channel);
        }
    }

    private void startDownload(UrlDownloaderFactory downloaderFactory, ChannelDescription channel) {
        UrlDownloader downloader = downloaderFactory.Create(channel.getUrl());
        byte[] data;
        try {
            data =  downloader.downLoad();
            processor.process(channel, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
