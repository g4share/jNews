package com.g4share.common.NewsItem;

import java.io.Serializable;

/**
 * User: gm
 */
public class ChannelCategory implements Serializable, Comparable<ChannelCategory> {
    private String name;
    private int count = 0;

    public ChannelCategory(String name) {
        this.name = name;
    }

    public void inc(){
        count++;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    @Override
    public int compareTo(ChannelCategory comparedCategory) {
        if (count == 0) return name.compareTo(comparedCategory.name);
        return count > comparedCategory.count ? 1 : -1;
    }

    public void add(int count) {
        this.count += count;
    }
}
