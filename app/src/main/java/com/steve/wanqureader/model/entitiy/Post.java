package com.steve.wanqureader.model.entitiy;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.steve.wanqureader.R;
import com.steve.wanqureader.util.Constant;
import com.steve.wanqureader.util.StringUtil;

import java.util.List;

/**
 * Created by Steve Zhang
 * 2/23/16
 * <p>
 * If it works, I created it. If not, I didn't.
 */
public class Post implements Parcelable {
    @SerializedName("url_domain")
    public String urlDomain;
    public String picture;
    public List<String> tags;
    public String url;
    @SerializedName("readable_title")
    public String readableTitle;
    @SerializedName("created_at")
    public long createdAt;
    public String title;
    public int id;
    public String content;
    @SerializedName("issue_id")
    public int issueId;
    public String summary;
    public String slug;
    @SerializedName("readable_article")
    public String readableArticle;
    @SerializedName("read_time_minutes")
    public int readTime;
    @SerializedName("readable_summary")
    public String readableSummary;

    private String shareBody;

    public Post() {
    }

    public Post(String urlDomain, String picture, List<String> tags, String url, String readableTitle,
                long createdAt, String title, int id, String content, int issueId, String summary,
                String slug, String readableArticle, int readTime, String readableSummary) {
        this.urlDomain = urlDomain;
        this.picture = picture;
        this.tags = tags;
        this.url = url;
        this.readableTitle = readableTitle;
        this.createdAt = createdAt;
        this.title = title;
        this.id = id;
        this.content = content;
        this.issueId = issueId;
        this.summary = summary;
        this.slug = slug;
        this.readableArticle = readableArticle;
        this.readTime = readTime;
        this.readableSummary = readableSummary;
    }

    public Post(Parcel in) {
        int[] ints = new int[3];
        in.readIntArray(ints);
        id = ints[0];
        issueId = ints[1];
        readTime = ints[2];

        createdAt = in.readLong();

        String[] strs = new String[11];
        in.readStringArray(strs);
        urlDomain = strs[0];
        picture = strs[1];
        tags = StringUtil.unserializeStringList(strs[2]);
        url = strs[3];
        readableTitle = strs[4];
        title = strs[5];
        content = strs[6];
        summary = strs[7];
        slug = strs[8];
        readableArticle = strs[9];
        readableSummary = strs[10];
    }

    public String getShareBody(Context context) {
        if (shareBody == null) {
            shareBody = readableTitle +
                    context.getString(R.string.via_app, Constant.playUrl) +
                    url;
        }

        return shareBody;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(new int[]{id, issueId, readTime});
        dest.writeLong(createdAt);
        dest.writeStringArray(new String[] {
                urlDomain, picture, StringUtil.serializeStringList(tags), readableArticle, title,
                content, summary, slug, readableArticle, readableSummary
        });
    }

    public static final Creator CREATOR = new Creator() {

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
