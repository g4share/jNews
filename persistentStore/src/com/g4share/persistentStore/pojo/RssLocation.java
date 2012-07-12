package com.g4share.persistentStore.pojo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: gm
 */
@Entity
@Table(name = "rss_location")
public class RssLocation implements Serializable {
    @Id
    @Column(name = "name")
    private String name = null;

    @Column(name = "url")
    private String url = null;

    @Column(name = "enabled")
    private boolean isEnabled = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}