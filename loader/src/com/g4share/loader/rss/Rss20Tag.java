package com.g4share.loader.rss;

import com.g4share.common.xml.XmlTag;

/**
 * User: gm
 */
public enum Rss20Tag  implements XmlTag {
    rss("rss"),
    channel("channel", rss),

    item("item", channel),

    title("title", channel, item),
    description("description", channel, item),
    link("link", channel, item),
    lastBuildDate("lastBuildDate", channel),

    category("category", item),
    pubDate("pubDate", item);


    private String text;
    private Rss20Tag[] parent;

    private Rss20Tag(String text, Rss20Tag... parent) {
        this.text = text;
        if (parent.length == 0) return;

        this.parent = new Rss20Tag[parent.length];
        for(int i = 0; i < parent.length; i++){
            this.parent[i] = parent[i];
        }
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Rss20Tag[] getParent() {
        return parent;
    }
}
