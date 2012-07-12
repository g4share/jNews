package com.g4share.viewer.mvc.controller;

import com.g4share.MemCachedConnector;
import com.g4share.MemCachedStore;
import com.g4share.RssStore;
import com.g4share.common.newsItem.ChannelCategory;
import com.g4share.common.newsItem.ChannelItem;
import com.g4share.common.newsItem.RssChannel;
import com.g4share.viewer.JspFormatter;
import com.g4share.viewer.UrlEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Date: 5/17/12
 */
@Controller
@Lazy
public class Home {
    private RssStore store;
    private MemCachedConnector connector;

    private JspFormatter jspFormatter = new JspFormatter();
    private UrlEncoder urlEncoder = new UrlEncoder();

    @Autowired
    public Home(MemCachedConnector connector) throws IOException {
        this.connector = connector;
        store = new RssStore(new MemCachedStore(this.connector));
    }

    @RequestMapping(value="/home", method = RequestMethod.GET)
    public String showNews(ModelMap model) {
        RssChannel[] channels = getChannels();
        ChannelItem[] items = getChannelsItems(channels, null, null) ;
        AddAttributes(model, getCategories(channels, GetItemsCount(channels)), items);
        return "home";
    }

    @RequestMapping(value="/home/category/{category}", method = RequestMethod.GET)
    public String showNewsByCategory(@PathVariable String category, ModelMap model) {
        RssChannel[] channels = getChannels();
        ChannelItem[] items = getChannelsItems(channels, null, urlEncoder.decode(category));
        AddAttributes(model, getCategories(channels, GetItemsCount(channels)), items);
        return "home";
    }

    @RequestMapping(value="/home/site/{site}", method = RequestMethod.GET)
    public String showNewsBySite(@PathVariable String site, ModelMap model) {

        RssChannel[] channels = getChannels();
        ChannelItem[] items = getChannelsItems(channels, urlEncoder.decode(site), null);
        AddAttributes(model, getCategories(channels, GetItemsCount(channels)), items);
        return "home";
    }

    private void AddAttributes(ModelMap model, ChannelCategory[] categories, ChannelItem[] items){
        model.addAttribute("newsList", items);
        model.addAttribute("newsCategories", categories);
        model.addAttribute("jspFormatter", jspFormatter);
        model.addAttribute("urlEncoder", urlEncoder);
    }

    private RssChannel[] getChannels(){
        try{
            return store.get();
        }
        finally {
            connector.close();
        }
    }

    private int GetItemsCount(RssChannel[] channels){
        int ct = 0;
        for(RssChannel channel : channels){
            ct += channel.getItems().length;
        }
        return ct;
    }

    private ChannelCategory[] getCategories(RssChannel[] channels, int totalItems){
        List<ChannelCategory> categories = new ArrayList<>();

        //add categories for first channel which has them
        int k = 0;
        while(categories.size() == 0
            && k < channels.length){
            Collections.addAll(categories, channels[k++].getCategories());
        }

        for (int i = k; i < channels.length; i++) {
            for(ChannelCategory category : channels[i].getCategories()){
                addCategory(categories, category);
            }
        }

        ChannelCategory allCategories = new ChannelCategory(null);
        allCategories.add(totalItems);
        categories.add(allCategories);

        ChannelCategory[] channelCategories =  categories.toArray(new ChannelCategory[categories.size()]);
        Arrays.sort(channelCategories, Collections.reverseOrder());
        return channelCategories;
    }

    private void addCategory(List<ChannelCategory> categories, ChannelCategory newCategory){
        for (ChannelCategory category : categories) {
            if (category.getName() == newCategory.getName()) {
                category.add(newCategory.getCount());
                return;
            }
        }

        categories.add(newCategory);
    }

    private ChannelItem[] getChannelsItems(RssChannel[] channels, String site, String category) {

        if (channels == null || channels.length == 0) return null;

        List<ChannelItem> newsList = new ArrayList<>();
        for(RssChannel channel : channels){
            //filter by site
            if (site != null &&
                !channel.getDescription().getName().equalsIgnoreCase(site)) continue;

            ChannelItem[] items = filterByCategory(channel.getItems(), channel.getDescription().getName(), category);
            if (items == null) continue;
            Collections.addAll(newsList, items);
        }
        if (newsList.size() == 0) return null;

        ChannelItem[] items = newsList.toArray(new ChannelItem[newsList.size()]);
        Arrays.sort(items, Collections.reverseOrder());
        return items;
    }

    private ChannelItem[] filterByCategory(ChannelItem[] items, String currentChannelId, String category) {
        if (items == null) return null;

        List<ChannelItem> newsList = new ArrayList<>();
        for(ChannelItem item : items){
            if (item == null) continue;

            if (category == null || //if no category filter
                (item.getCategory() != null && item.getCategory().equalsIgnoreCase(category))){

                item.setChannelId(currentChannelId); //set link to channelDescription
                newsList.add(item);
            }
        }
        return newsList.size() == 0 ? null : newsList.toArray(new ChannelItem[newsList.size()]);
    }
}