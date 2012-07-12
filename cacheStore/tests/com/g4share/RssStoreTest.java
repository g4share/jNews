package com.g4share;

import com.g4share.common.newsItem.ChannelDescription;
import com.g4share.common.newsItem.RssChannel;
import com.g4share.common.download.UrlDownloaderFactory;
import com.g4share.loader.Worker;
import com.g4share.loader.rss.ChannelCollection;
import com.g4share.loader.rss.ChannelProcessor;
import com.g4share.loader.rss.RssReaderFactory;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * User: gm
 */
public class RssStoreTest {
    private Worker worker;
    private ChannelCollection channelCollection;

    private RssStore store;

    @Before
    public void setUp() throws Exception {
        channelCollection = new ChannelCollection();

        worker = new Worker(new ChannelDescription[]
                {
                        new ChannelDescription("Unimedia", "http://unimedia.md/rss/news.xml", true)
                },
                new ChannelProcessor(new RssReaderFactory(), channelCollection),
                new UrlDownloaderFactory());

        store = new RssStore(
                new MemCachedStore(
                        new MemCachedConnector(
                                new MemCachedConnectionInfo("127.0.0.1", 11211, 3600))));

    }

    @Test
    public void testSetDefault() throws Exception {
        worker.GetNews();

        RssChannel[] channels = channelCollection.getChannels();
        store.set(channels);
        check(channels, store.get());
    }


    static private void check(RssChannel[] channelsTo, RssChannel[] channelsFrom){
        assertThat(channelsTo.length, is(channelsFrom.length));

        for(int i = 0; i < channelsTo.length; i++){
            check(channelsTo[i], channelsFrom[i]);
        }
    }

    static private void check(RssChannel channelTo, RssChannel channelFrom){
        assertThat(channelTo, notNullValue());
        assertThat(channelFrom, notNullValue());

        assertThat(channelTo.getItems(), notNullValue());
        assertThat(channelTo.getInfo(), notNullValue());
        assertThat(channelTo.getDescription(), notNullValue());

        assertThat(channelFrom.getItems(), notNullValue());
        assertThat(channelFrom.getInfo(), notNullValue());
        assertThat(channelFrom.getDescription(), notNullValue());

        assertThat(channelTo.getDescription().getDate(), is(channelFrom.getDescription().getDate()));
        assertThat(channelTo.getDescription().getUrl(), is(channelFrom.getDescription().getUrl()));

        assertThat(channelTo.getInfo().getBuildDate(), is(channelFrom.getInfo().getBuildDate()));
        assertThat(channelTo.getInfo().getDescription(), is(channelFrom.getInfo().getDescription()));
        assertThat(channelTo.getInfo().getLink(), is(channelFrom.getInfo().getLink()));
        assertThat(channelTo.getInfo().getTitle(), is(channelFrom.getInfo().getTitle()));


        assertThat(channelTo.getItems().length, is(channelFrom.getItems().length));
        for(int i = 0; i < channelTo.getItems().length; i++){
            assertThat(channelTo.getItems()[i].getAuthor(), is(channelFrom.getItems()[i].getAuthor()));
            assertThat(channelTo.getItems()[i].getCategory(), is(channelFrom.getItems()[i].getCategory()));
            assertThat(channelTo.getItems()[i].getDescription(), is(channelFrom.getItems()[i].getDescription()));
            assertThat(channelTo.getItems()[i].getLink(), is(channelFrom.getItems()[i].getLink()));
            assertThat(channelTo.getItems()[i].getTitle(), is(channelFrom.getItems()[i].getTitle()));
        }
    }
}
