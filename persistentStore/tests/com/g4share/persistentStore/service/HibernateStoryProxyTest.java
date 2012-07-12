package com.g4share.persistentStore.service;

import com.g4share.common.newsItem.ChannelDescription;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * User: gm
 */
public class HibernateStoryProxyTest {
    PersistentStoreProxy proxy;

    @Before
    public void setUp() throws Exception {
        //proxy = new HibernateStoryProxy();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetRssLocations() throws Exception {
        ChannelDescription[] channelDescriptions = proxy.getEnabledChannels();
    }
}
