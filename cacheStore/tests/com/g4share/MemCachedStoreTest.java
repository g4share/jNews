package com.g4share;

import com.g4share.common.newsItem.ChannelItem;
import com.g4share.common.download.UrlDownloaderFactory;
import com.g4share.common.newsItem.ChannelDescription;
import com.g4share.common.newsItem.ChannelInfo;
import com.g4share.common.newsItem.RssChannel;
import com.g4share.loader.Worker;
import com.g4share.loader.rss.ChannelCollection;
import com.g4share.loader.rss.ChannelProcessor;
import com.g4share.loader.rss.RssReaderFactory;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * User: gm
 */
public class MemCachedStoreTest {
    MemCachedStore store;

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

        store = new MemCachedStore(
                new MemCachedConnector(
                        new MemCachedConnectionInfo("127.0.0.1", 11211, 3600)));
    }

    @Test
    public void testSetDefault() throws Exception {
        worker.GetNews();

        RssChannel[] channels = channelCollection.getChannels();

        store.set("/sys/channelCount", channels.length);

        for(int i = 0; i < channels.length; i++){
            store.set("/sys/info/" + i, channels[i].getDescription());
            store.set("/store/info/" + i, channels[i].getInfo());
            store.set("/store/data/" + i, channels[i].getItems());
        }

        check(store, channels);
    }


    static private void check(MemCachedStore store, RssChannel[] channels) throws IOException {
        int ct = store.get("/sys/channelCount");
        assertThat(channels.length, is(ct));

        for(int i = 0; i < ct; i++){
            ChannelDescription description = store.get("/sys/info/" + i);
            ChannelInfo info = store.get("/store/info/" + i);
            ChannelItem[] items = store.get("/store/data/" + i);

            check(channels[i], description, info, items);
        }

    }

    static private void check(RssChannel code,
                              ChannelDescription descriptionCache,
                              ChannelInfo infoCache,
                              ChannelItem[] itemsCache) {
        assertThat(descriptionCache, notNullValue());
        assertThat(infoCache, notNullValue());
        assertThat(itemsCache, notNullValue());

        assertThat(code.getDescription().getDate(), is(descriptionCache.getDate()));
        assertThat(code.getDescription().getUrl(), is(descriptionCache.getUrl()));

        assertThat(code.getInfo().getBuildDate(), is(infoCache.getBuildDate()));
        assertThat(code.getInfo().getDescription(), is(infoCache.getDescription()));
        assertThat(code.getInfo().getLink(), is(infoCache.getLink()));
        assertThat(code.getInfo().getTitle(), is(infoCache.getTitle()));

        assertThat(code.getItems().length, is(itemsCache.length));
        for(int i = 0; i < itemsCache.length; i++){
            assertThat(code.getItems()[i].getAuthor(), is(itemsCache[i].getAuthor()));
            assertThat(code.getItems()[i].getCategory(), is(itemsCache[i].getCategory()));
            assertThat(code.getItems()[i].getDescription(), is(itemsCache[i].getDescription()));
            assertThat(code.getItems()[i].getLink(), is(itemsCache[i].getLink()));
            assertThat(code.getItems()[i].getTitle(), is(itemsCache[i].getTitle()));
        }
    }
}
