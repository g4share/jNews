package com.g4share.persistentStore.service;

import com.g4share.common.newsItem.ChannelDescription;
import com.g4share.persistentStore.pojo.RssLocation;
import com.g4share.persistentStore.pojo.RssLocationConverter;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: gm
 */
@Service
public class HibernateStoryProxy implements PersistentStoreProxy {
    private ConfigReader.HibernateSession hibernateSession;

    @Autowired
    public HibernateStoryProxy(ConfigReader configReader) {
        hibernateSession = new ConfigReader.HibernateSession(configReader);
    }


    @Override
    public ChannelDescription[] getEnabledChannels() {
        return new RssLocationConverter(hibernateSession.getList(
                RssLocation.class, Restrictions.eq("isEnabled", true)))
                .toChannelsDescription();
    }

    @Override
    public ChannelDescription[] getChannels() {
        return new RssLocationConverter(hibernateSession.getList(
                RssLocation.class, null))
                .toChannelsDescription();
    }

    @Override
    public void setChannelStatus(String name, boolean isEnabled) {
        RssLocation[] locations = hibernateSession.getList(RssLocation.class, Restrictions.eq("name", name));
        if (locations.length != 1) return;
        locations[0].setEnabled(isEnabled);
        hibernateSession.update(locations[0]);
    }

    @Override
    public void setChannel(ChannelDescription channel) {
        hibernateSession.add(RssLocationConverter.fromChannelsDescription(channel));
    }

    @Override
    public void removeChannel(String name) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
