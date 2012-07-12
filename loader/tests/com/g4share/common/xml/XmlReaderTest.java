package com.g4share.common.xml;

import com.g4share.CommonTestMethods;
import com.g4share.common.newsItem.RssChannel;
import com.g4share.common.log.LogLevel;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

/**
 * User: gm
 */
public class XmlReaderTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private int index;
    LinkedHashMap<FakedTag[], LinkedHashMap<String, String>> tags;

    boolean[] calledEntries;

    private XmlReader<FakedTag, RssChannel> reader;


    @Before
    public void setUp() throws Exception {
        XmlModelManager<FakedTag, RssChannel> modelManager = new XmlModelManager<FakedTag, RssChannel>() {
            @Override
            public boolean addNode(List<FakedTag> path, String text, Map<String, String> attributes) {
                System.out.println("-----");
                FakedTag[] tag = CommonTestMethods.getByIndex(tags, index);
                assertThat("null node",tag, notNullValue());


                assertThat("Wrong node path: " + tag[tag.length - 1].getText(), path.size(), is(tag.length));
                for(int i = 0; i < path.size(); i++){
                    System.out.print(path.get(i).getText());
                    if (i != path.size() - 1) System.out.println("");

                    assertThat("Wrong node: " + i, tag[i], is(path.get(i)));
                }

                System.out.println("  (" + text + ")");

                //check properties
                Map<String, String> tagsProperties = tags.get(tag);
                Map<String, String> receivedProperties = attributes;

                assertThat("wrong property", tagsProperties == null, is(receivedProperties == null));
                if (receivedProperties != null){
                    assertThat("wrong property length", tagsProperties.size(), is(receivedProperties.size()));

                    for(String key : tagsProperties.keySet()){
                        assertThat("wrong property : " + key,tagsProperties.get(key), is(receivedProperties.get(key)));
                        System.out.println("    " + key + " -> " + receivedProperties.get(key));
                    }
                }

                calledEntries[index++] = true;
                return true;
            }

            @Override
            public void eventOccurred(LogLevel level, String hint) { }

            @Override
            public RssChannel getProcessedData() { return null; }
        };

        reader = new XmlReader(new XmlTagStore<>(FakedTag.class),
                modelManager);
    }

    @Test
    public void testRead() throws Exception {
        index = 0;

        String tFileName = folder.getRoot().getAbsolutePath() + "/fakedXml";
        CommonTestMethods.createXmlFile(tFileName);
        LinkedHashMap<String, String> prop;

        tags = new LinkedHashMap<>();

        prop = new LinkedHashMap<>();
        prop.put("value1", "node1Value1");
        tags.put(new FakedTag[] {FakedTag.node1}, new LinkedHashMap(prop));

        prop = new LinkedHashMap<>();
        prop.put("value1", "node2value1");
        prop.put("value2", "node2value2");
        tags.put(new FakedTag[]{ FakedTag.node1, FakedTag.node2}, new LinkedHashMap(prop));

        prop = new LinkedHashMap<>();
        prop.put("value1", "node3value1");
        tags.put(new FakedTag[]{ FakedTag.node1, FakedTag.node2, FakedTag.node3}, new LinkedHashMap(prop));

        prop = new LinkedHashMap<>();
        prop.put("value", "text1");
        tags.put(new FakedTag[]{ FakedTag.node1, FakedTag.node2, FakedTag.textNode}, new LinkedHashMap(prop));

        prop = new LinkedHashMap<>();
        prop.put("value", "text2");
        tags.put(new FakedTag[]{ FakedTag.node1, FakedTag.node2, FakedTag.textNode}, new LinkedHashMap(prop));

        tags.put(new FakedTag[]{ FakedTag.node1, FakedTag.node2, FakedTag.textNode}, null);
        tags.put(new FakedTag[]{ FakedTag.node1, FakedTag.node2, FakedTag.textNode}, null);

        tags.put(new FakedTag[]{ FakedTag.node1, FakedTag.node3}, null);

        calledEntries = new boolean[tags.size()];
        for(int i = 0; i < calledEntries.length; i++){
            calledEntries[i] = false;
        }

        reader.read(CommonTestMethods.getStream(tFileName));

        for(int i = 0; i < calledEntries.length; i++){
            FakedTag[] tag = CommonTestMethods.getByIndex(tags, i);
            assertThat("Cannot find tag " + i + " : " +  tag[tag.length - 1].getText(), calledEntries[i], is(true));
        }
    }
}