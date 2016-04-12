package com.steve.wanqureader.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Post {

    @SerializedName("creation_date")
    @Expose
    private String creationDate;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("issue")
    @Expose
    private Integer issue;
    @SerializedName("post_no")
    @Expose
    private Integer postNo;
    @SerializedName("read_time_minutes")
    @Expose
    private Integer readTimeMinutes;
    @SerializedName("readable_article")
    @Expose
    private String readableArticle;
    @SerializedName("readable_summary")
    @Expose
    private String readableSummary;
    @SerializedName("readable_title")
    @Expose
    private String readableTitle;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("tags")
    @Expose
    private List<Integer> tags = new ArrayList<Integer>();
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("url_domain")
    @Expose
    private String urlDomain;

    /**
     *
     * @return
     * The creationDate
     */
    public String getCreationDate() {
        return creationDate;
    }

    /**
     *
     * @param creationDate
     * The creation_date
     */
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The issue
     */
    public Integer getIssue() {
        return issue;
    }

    /**
     *
     * @param issue
     * The issue
     */
    public void setIssue(Integer issue) {
        this.issue = issue;
    }

    /**
     *
     * @return
     * The postNo
     */
    public Integer getPostNo() {
        return postNo;
    }

    /**
     *
     * @param postNo
     * The post_no
     */
    public void setPostNo(Integer postNo) {
        this.postNo = postNo;
    }

    /**
     *
     * @return
     * The readTimeMinutes
     */
    public Integer getReadTimeMinutes() {
        return readTimeMinutes;
    }

    /**
     *
     * @param readTimeMinutes
     * The read_time_minutes
     */
    public void setReadTimeMinutes(Integer readTimeMinutes) {
        this.readTimeMinutes = readTimeMinutes;
    }

    /**
     *
     * @return
     * The readableArticle
     */
    public String getReadableArticle() {
        return readableArticle;
    }

    /**
     *
     * @param readableArticle
     * The readable_article
     */
    public void setReadableArticle(String readableArticle) {
        this.readableArticle = readableArticle;
    }

    /**
     *
     * @return
     * The readableSummary
     */
    public String getReadableSummary() {
        return readableSummary;
    }

    /**
     *
     * @param readableSummary
     * The readable_summary
     */
    public void setReadableSummary(String readableSummary) {
        this.readableSummary = readableSummary;
    }

    /**
     *
     * @return
     * The readableTitle
     */
    public String getReadableTitle() {
        return readableTitle;
    }

    /**
     * @param readableTitle The readable_title
     */
    public void setReadableTitle(String readableTitle) {
        this.readableTitle = readableTitle;
    }

    /**
     * @return The slug
     */
    public String getSlug() {
        return slug;
    }

    /**
     *
     * @param slug
     * The slug
     */
    public void setSlug(String slug) {
        this.slug = slug;
    }

    /**
     *
     * @return
     * The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     *
     * @param summary
     * The summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     *
     * @return
     * The tags
     */
    public List<Integer> getTags() {
        return tags;
    }

    /**
     *
     * @param tags
     * The tags
     */
    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     * The urlDomain
     */
    public String getUrlDomain() {
        return urlDomain;
    }

    /**
     *
     * @param urlDomain
     * The url_domain
     */
    public void setUrlDomain(String urlDomain) {
        this.urlDomain = urlDomain;
    }
}