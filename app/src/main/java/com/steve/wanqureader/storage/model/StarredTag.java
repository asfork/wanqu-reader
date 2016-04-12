package com.steve.wanqureader.storage.model;

import com.orm.SugarRecord;

/**
 * Created by steve on 4/9/16.
 */
public class StarredTag extends SugarRecord {
    private String tag;

    private StarredPost post;

    public StarredTag() {
    }

    public StarredTag(String tag, StarredPost post) {
        this.tag = tag;
        this.post = post;
    }

    public String getTag() {
        return tag;
    }
}
