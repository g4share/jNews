package com.g4share.loader.rss;

import com.g4share.common.log.LogLevel;
import java.util.List;
import java.util.Map;

/**
 * User: gm
 */
public class RssVersionModelManager extends AbstractRssModelManager<RssVersion, String> {
    private String rssVersion;

    @Override
    public boolean addNode(List<RssVersion> path, String text, Map<String, String> attributes) {
        if (path.size() == 1
                && path.get(0) == RssVersion.rss
                && attributes != null
                && attributes.containsKey("version")){
            rssVersion = attributes.get("version");

            return false;
        }

        return true;
    }

    @Override
    public void eventOccurred(LogLevel level, String hint) { }

    @Override
    public String getProcessedData() { return rssVersion;}}