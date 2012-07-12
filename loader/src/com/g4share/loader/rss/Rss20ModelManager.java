package com.g4share.loader.rss;

import com.g4share.common.newsItem.*;
import com.g4share.common.log.LogLevel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: gm
 */
public class Rss20ModelManager extends AbstractRssModelManager<Rss20Tag, RssChannel> {
    private ChannelInfo info;
    private List<ChannelItem> items = new ArrayList<>();

    @Override
    public boolean addNode(List<Rss20Tag> path, String text, Map<String, String> attributes) {
        if (info == null) info = new ChannelInfo();

        switch(path.get(path.size() - 1)){
            case description:
                setDescription(path, text);
                break;
            case title:
                setTitle(path, text);
                break;
            case link:
                setLink(path, text);
                break;
            case lastBuildDate:
                info.setBuildDate(format(text));
                break;
            case pubDate:
                items.get(items.size() - 1).setPubDate(format(text));
                break;
            case category:
                items.get(items.size() - 1).setCategory(text);
                break;
            case item:
                items.add(new ChannelItem());
                break;

        }

        return true;
    }


    @Override
    public void eventOccurred(LogLevel level, String hint) {  }

    @Override
    public RssChannel getProcessedData() {
        RssChannel channel = new RssChannel();
        channel.setInfo(info);
        channel.setItems(items.toArray(new ChannelItem[items.size()]));
        channel.setCategories(getCategories(channel.getItems()));
        return channel;
    }

    private ChannelCategory[] getCategories(ChannelItem[] items) {
        List<ChannelCategory> categories = new ArrayList<>();
        for (ChannelItem item : items) {
            if (item == null ||
                item.getCategory() == null ||
                item.getCategory() == "") continue;

            getCategoryByName(categories, item.getCategory()).inc();
        }
        return categories.toArray(new ChannelCategory[categories.size()]);
    }

    private ChannelCategory getCategoryByName(List<ChannelCategory> categories, String name){
        for (ChannelCategory category : categories) {
            if (category.getName().equalsIgnoreCase(name)) {
                return category;
            }
        }

        ChannelCategory category = new ChannelCategory(name);
        categories.add(category);
        return category;
    }


    private void setDescription(List<Rss20Tag> path,  String text){
        //get the parent
        switch(path.get(path.size() - 2)){
            case channel:
                info.setDescription(text);
                break;
            case item:
                items.get(items.size() - 1).setDescription(text);
                break;
        }
    }

    private void setTitle(List<Rss20Tag> path,  String text){
        //get the parent
        switch(path.get(path.size() - 2)){
            case channel:
                info.setTitle(text);
                break;
            case item:
                items.get(items.size() - 1).setTitle(text);
                break;

        }
    }

    private void setLink(List<Rss20Tag> path,  String text){
        //get the parent
        switch(path.get(path.size() - 2)){
            case channel:
                info.setLink(text);
                break;
            case item:
                items.get(items.size() - 1).setLink(text);
                break;

        }
    }
}