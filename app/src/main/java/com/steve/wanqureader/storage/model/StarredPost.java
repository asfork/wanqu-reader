package com.steve.wanqureader.storage.model;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by steve on 4/9/16.
 */
public class StarredPost extends SugarRecord {
    private long starred_date;
    private int issue_num;
    private int post_num;
    private int read_time_minutes;
    private String readable_article;
    private String readable_title;
    private String title;
    private String readable_summary;
    private String summary;
    private String creation_date;
    private String slug;
    private String url;
    private String url_domain;

    public StarredPost() {

    }

    public StarredPost(long starred_date, String creation_date, int issue_num,
                       int post_num, int read_time_minutes, String readable_article,
                       String readable_title, String title, String readable_summary,
                       String summary, String slug, String url, String url_domain) {
        this.starred_date = starred_date;
        this.creation_date = creation_date;
        this.issue_num = issue_num;
        this.post_num = post_num;
        this.read_time_minutes = read_time_minutes;
        this.readable_article = readable_article;
        this.readable_title = readable_title;
        this.title = title;
        this.readable_summary = readable_summary;
        this.summary = summary;
        this.slug = slug;
        this.url = url;
        this.url_domain = url_domain;
    }

    public List<StarredTag> getTags() {
        return StarredTag.find(StarredTag.class, "post = ?", String.valueOf(this.getId()));
    }

    public long getStarredDate() {
        return starred_date;
    }

    public String getCreationDate() {
        return creation_date;
    }

    public int getIssueNum() {
        return issue_num;
    }

    public int getPostNum() {
        return post_num;
    }

    public int getReadTimeMinutes() {
        return read_time_minutes;
    }

    public String getReadablerAticle() {
        return readable_article;
    }

    public String getReadableTitle() {
        return readable_title;
    }

    public String getTitle() {
        return title;
    }

    public String getReadableSummary() {
        return readable_summary;
    }

    public String getSummary() {
        return summary;
    }

    public String getSlug() {
        return slug;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlDomain() {
        return url_domain;
    }
}
