package com.g4share.loader.rss;

import com.g4share.common.Constants;
import com.g4share.common.xml.XmlModelManager;
import com.g4share.common.xml.XmlReader;
import com.g4share.common.xml.XmlTag;
import com.g4share.common.xml.XmlTagStore;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * User: gm
 */
public class RssReaderFactory {
    private Map<String, RssChooser> rssEnums = new HashMap<>();

    public RssReaderFactory() {
        rssEnums.put(null, new RssChooser(new XmlTagStore<>(RssVersion.class), new XmlModelManagerFactory<>(RssVersionModelManager.class)));
        rssEnums.put("2.0", new RssChooser(new XmlTagStore<>(Rss20Tag.class), new XmlModelManagerFactory<>(Rss20ModelManager.class)));
    }


    public <U>  XmlReader<?, U> Create(InputStream stream){
        if (rssEnums == null|| rssEnums.isEmpty()) return null;
        RssChooser versionChooser = rssEnums.get(null);

        XmlReader<RssVersion, String> versionReader =  new XmlReader(versionChooser.getTagStore(), versionChooser.getModelManager());
        Constants.ResultCode code = versionReader.read(stream);

        String rssVersion = versionReader.getModelManager().getProcessedData();
        if (code.isError()
                || rssVersion == null
                || !rssEnums.containsKey(rssVersion)) return null;

        RssChooser<?, U> chooser = rssEnums.get(rssVersion);

        return new XmlReader(chooser.getTagStore(), chooser.getModelManager());
    }


    private class RssChooser <T extends Enum<T> & XmlTag, U>{
        private XmlTagStore<T> tagStore;
        private XmlModelManagerFactory modelManagerFactory;

        private RssChooser(XmlTagStore<T> tagStore, XmlModelManagerFactory modelManagerFactory ) {
            this.tagStore = tagStore;
            this.modelManagerFactory = modelManagerFactory;
        }

        public XmlTagStore<T> getTagStore() {
            return tagStore;
        }

        public XmlModelManager<T, U> getModelManager() {
            try {
                return (XmlModelManager<T, U>) modelManagerFactory.create();
            } catch (IllegalAccessException | InstantiationException e) {
                return null;
            }
        }
    }
}
