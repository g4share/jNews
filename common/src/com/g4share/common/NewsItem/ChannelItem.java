package com.g4share.common.NewsItem;

import java.io.Serializable;
import java.util.Date;

/**
 * User: gm
 */
public class ChannelItem implements Serializable, Comparable<ChannelItem> {
    private String title;
    private String link;
    private String description;
    private String author;
    private String category;
    private Date pubDate;

    private String channelId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @Override
    public int compareTo(ChannelItem comparedItem) {
        if (comparedItem == null) return 1;

        if (this == null && comparedItem == null) return 0;
        if (this.pubDate == null) return -1;
        if (comparedItem.pubDate == null) return 1;

        return this.pubDate.compareTo(comparedItem.pubDate);
    }
}
