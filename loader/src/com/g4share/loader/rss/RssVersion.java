package com.g4share.loader.rss;

import com.g4share.common.xml.XmlTag;

/**
 * User: gm
 */
public enum RssVersion implements XmlTag {
    rss("rss");

    private String text;

    private RssVersion(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Rss20Tag[] getParent() {
        return null;
    }
}
