package com.g4share.common.newsItem;

import java.io.Serializable;
import java.util.Date;

/**
 * User: gm
 */
public class ChannelInfo implements Serializable {
    private String title;
    private String description;
    private String link;
    private Date buildDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(Date buildDate) {
        this.buildDate = buildDate;
    }

}
