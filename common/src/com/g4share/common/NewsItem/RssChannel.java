package com.g4share.common.NewsItem;

/**
 * User: gm
 */
public class RssChannel  {
    private ChannelDescription description;

    private ChannelInfo info;
    private ChannelItem[] items;
    private ChannelCategory[] categories;

    public ChannelDescription getDescription() {
        return description;
    }

    public void setDescription(ChannelDescription description) {
        this.description = description;
    }

    public ChannelInfo getInfo() {
        return info;
    }

    public void setInfo(ChannelInfo info) {
        this.info = info;
    }

    public ChannelItem[] getItems() {
        return items;
    }

    public void setItems(ChannelItem[] items) {
        this.items = items;
    }

    public ChannelCategory[] getCategories() {
        return categories;
    }

    public void setCategories(ChannelCategory[] categories) {
        this.categories = categories;
    }
}
