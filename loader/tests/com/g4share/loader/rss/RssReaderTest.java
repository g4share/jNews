package com.g4share.loader.rss;

import com.g4share.CommonTestMethods;
import com.g4share.common.xml.XmlReader;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import java.io.IOException;
import java.io.InputStream;

/**
 * User: gm
 */
public class RssReaderTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    String tFileName ;

    @Before
    public void setUp() throws Exception {
        tFileName = folder.getRoot().getAbsolutePath() + "/fakedXml";
        CommonTestMethods.createRssFile(tFileName);
    }

    @Test
    public void ReadStream() throws IOException {
        RssReaderFactory factory = new RssReaderFactory();
        InputStream stream;

        stream = CommonTestMethods.getStream(tFileName);
        XmlReader<?, ?> reader = factory.Create(stream);
        stream.close();

        stream = CommonTestMethods.getStream(tFileName);
        reader.read(stream);
        stream.close();
    }

}
