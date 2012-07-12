package com.g4share.common.xml;

import java.util.List;

/**
 * User: gm
 */
public class XmlTagStore<T extends Enum<T> & XmlTag> {
    public Class<T> tagList;

    public XmlTagStore(Class<T> tagList) {
        this.tagList = tagList;
    }

    public T fromString(String text) {
        if (text != null) {
            for (T tag : tagList.getEnumConstants()) {
                if (tag.getText().equalsIgnoreCase(text)){
                    return tag;
                }
            }
        }
        return null;
    }

    public boolean isPathKey(T child, List<T> path) {
        for(int i = path.size() - 1; i >= 0; i--){
            if (!hasParent(child, path.get(i))) return false;
            child = path.get(i);
        }

        return child.getParent() == null;
    }

    private boolean hasParent(T child, T possibleParent){
        if (child == null || possibleParent == null) return false;

        XmlTag[] parents = child.getParent();
        for(XmlTag tag : parents){
            if (tag == possibleParent) return true;
        }
        return false;
    }
}
