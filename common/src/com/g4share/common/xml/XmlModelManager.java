package com.g4share.common.xml;

import com.g4share.common.log.LogLevel;
import java.util.List;
import java.util.Map;

/**
 * User: gm
 */
public interface XmlModelManager<T extends Enum<T> & XmlTag, U> {
    /**
     * occurs when an valid xml node is read
     * @param path the xml node path to the current node
     * @param text text between xml tags or null if no one
     * @param attributes current node attributes as key/value map
     * @return true if xml processing should continue, and false if not
     */
    boolean addNode(List<T> path, String text, Map<String, String> attributes);
    void eventOccurred(LogLevel level, String hint);

    /**
     * @return constructed object
     */
    U getProcessedData();
}
