package com.g4share.viewer.mvc.controller;

import com.g4share.common.newsItem.ChannelDescription;
import com.g4share.persistentStore.service.PersistentStoreProxy;
import com.g4share.viewer.UrlEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Date: 5/17/12
 */
@Controller
@SessionAttributes({"channel"})
public class Channel {
    private PersistentStoreProxy persistentStoreProxy;
    private UrlEncoder urlEncoder = new UrlEncoder();

    @Autowired
    public Channel(PersistentStoreProxy persistentStoreProxy) {
        this.persistentStoreProxy = persistentStoreProxy;
    }

    @RequestMapping(value="/channel", method = RequestMethod.GET)
    public String showChannels(ModelMap model) {
        ChannelDescription[] channels = persistentStoreProxy.getChannels();
        model.addAttribute("channels", channels);
        model.addAttribute("urlEncoder", urlEncoder);

        model.addAttribute("channel", new ChannelDescription("ss", "rr", false));
        return "channel";
    }

    @RequestMapping(value="/channel/{channelName}", method = RequestMethod.POST)
    public String updateChannel(@PathVariable String channelName,
                                @RequestParam(value="cb_enabled", required = false) boolean isEnabled) {

        persistentStoreProxy.setChannelStatus(urlEncoder.decode(channelName), isEnabled);
        return "redirect:/channel";
    }

    @RequestMapping(value="/channel/add", method = RequestMethod.POST)
    public String setChannel(@ModelAttribute("channel") ChannelDescription description,
                             BindingResult result){

        persistentStoreProxy.setChannel(description);
        return "redirect:/channel";
    }
}