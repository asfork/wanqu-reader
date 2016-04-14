package com.steve.wanqureader.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class Post implements Parcelable {
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
    private ArrayList<Integer> tags = new ArrayList<Integer>();
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("url_domain")
    @Expose
    private String urlDomain;

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIssue() {
        return issue;
    }

    public void setIssue(Integer issue) {
        this.issue = issue;
    }

    public Integer getPostNo() {
        return postNo;
    }

    public void setPostNo(Integer postNo) {
        this.postNo = postNo;
    }

    public Integer getReadTimeMinutes() {
        return readTimeMinutes;
    }

    public void setReadTimeMinutes(Integer readTimeMinutes) {
        this.readTimeMinutes = readTimeMinutes;
    }

    public String getReadableArticle() {
        return readableArticle;
    }

    public void setReadableArticle(String readableArticle) {
        this.readableArticle = readableArticle;
    }

    public String getReadableSummary() {
        return readableSummary;
    }

    public void setReadableSummary(String readableSummary) {
        this.readableSummary = readableSummary;
    }

    public String getReadableTitle() {
        return readableTitle;
    }

    public void setReadableTitle(String readableTitle) {
        this.readableTitle = readableTitle;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ArrayList<Integer> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Integer> tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlDomain() {
        return urlDomain;
    }

    public void setUrlDomain(String urlDomain) {
        this.urlDomain = urlDomain;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.creationDate);
        dest.writeValue(this.id);
        dest.writeValue(this.issue);
        dest.writeValue(this.postNo);
        dest.writeValue(this.readTimeMinutes);
        dest.writeString(this.readableArticle);
        dest.writeString(this.readableSummary);
        dest.writeString(this.readableTitle);
        dest.writeString(this.slug);
        dest.writeString(this.summary);
        dest.writeList(this.tags);
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeString(this.urlDomain);
    }

    public Post() {
    }

    protected Post(Parcel in) {
        this.creationDate = in.readString();
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.issue = (Integer) in.readValue(Integer.class.getClassLoader());
        this.postNo = (Integer) in.readValue(Integer.class.getClassLoader());
        this.readTimeMinutes = (Integer) in.readValue(Integer.class.getClassLoader());
        this.readableArticle = in.readString();
        this.readableSummary = in.readString();
        this.readableTitle = in.readString();
        this.slug = in.readString();
        this.summary = in.readString();
        this.tags = new ArrayList<Integer>();
        in.readList(this.tags, Integer.class.getClassLoader());
        this.title = in.readString();
        this.url = in.readString();
        this.urlDomain = in.readString();
    }

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel source) {
            return new Post(source);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
}