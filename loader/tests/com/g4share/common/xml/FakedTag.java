package com.g4share.common.xml;


/**
 * User: gm
 */
public enum FakedTag implements XmlTag {
    node1("node1"),
    node2("node2", node1),
    node3("node3", node1, node2),
    textNode("textNode", node2);

    private String text;
    private FakedTag[] parent;

    private FakedTag(String text, FakedTag... parent) {
        this.text = text;
        if (parent.length == 0) return;

        this.parent = new FakedTag[parent.length];
        for(int i = 0; i < parent.length; i++){
            this.parent[i] = parent[i];
        }
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public FakedTag[] getParent() {
        return parent;
    }
}
