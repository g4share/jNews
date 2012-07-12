package com.g4share.loader.rss;

import com.g4share.common.Constants;
import com.g4share.common.xml.XmlReader;
import com.g4share.common.newsItem.ChannelDescription;
import com.g4share.common.newsItem.RssChannel;
import java.io.ByteArrayInputStream;

/**
 * User: gm
 */
public class ChannelProcessor {
    private RssReaderFactory readerFactory;
    private ChannelCollection channelCollection;

    public ChannelProcessor(RssReaderFactory readerFactory, ChannelCollection channelCollection) {
        this.readerFactory = readerFactory;
        this.channelCollection = channelCollection;
    }

    public void process(ChannelDescription channelDescription, byte[] data){
        ByteArrayInputStream stream = new ByteArrayInputStream(data);
        XmlReader<?, RssChannel> reader = readerFactory.Create(stream);

        stream.reset();
        Constants.ResultCode result = reader.read(stream);
        if (result.isError()) return;

        RssChannel rssChannel = reader.getModelManager().getProcessedData();
        rssChannel.setDescription(channelDescription);
        channelCollection.Add(rssChannel);
    }
}
