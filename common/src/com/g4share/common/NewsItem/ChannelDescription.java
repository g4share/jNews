package com.g4share.common.NewsItem;

import java.io.Serializable;

/**
 * User: gm
 */
public class ChannelDescription implements Serializable {
    private String name;
    private String url;
    private String date;

    private boolean enabled;

    public ChannelDescription() { }

    public ChannelDescription(String name, String url, boolean enabled) {
        this.name = name;
        this.url = url;
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
