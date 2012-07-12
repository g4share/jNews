package com.g4share.loader.rss;
import com.g4share.common.xml.XmlModelManager;

/**
 * User: gm
 */
public class XmlModelManagerFactory<T extends XmlModelManager> {
    private Class<T> clazz;

    public XmlModelManagerFactory(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T create() throws IllegalAccessException, InstantiationException {
        return clazz.newInstance();
    }
}
