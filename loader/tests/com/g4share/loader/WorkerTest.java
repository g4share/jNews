package com.g4share.loader;

import com.g4share.common.newsItem.ChannelDescription;
import com.g4share.common.download.UrlDownloaderFactory;
import com.g4share.loader.rss.ChannelCollection;
import com.g4share.loader.rss.ChannelProcessor;
import com.g4share.loader.rss.RssReaderFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * User: gm
 */
public class WorkerTest {
    Worker worker;
    ChannelCollection channelCollection;

    @Before
    public void setUp() throws Exception {

        channelCollection = new ChannelCollection();

        worker = new Worker(new ChannelDescription[]
                {
                    new ChannelDescription("Unimedia", "http://unimedia.md/rss/news.xml", true)
                },
                new ChannelProcessor(new RssReaderFactory(), channelCollection),
                new UrlDownloaderFactory());
    }

    @Test
    public void testGetNews() throws Exception {
         worker.GetNews();
    }
}
