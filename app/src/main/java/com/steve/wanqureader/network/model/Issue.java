package com.steve.wanqureader.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Issue implements Parcelable {

    @SerializedName("creation_date")
    @Expose
    private String creationDate;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("issue_no")
    @Expose
    private Integer issueNo;
    @SerializedName("readable_title")
    @Expose
    private String readableTitle;

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

    public Integer getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(Integer issueNo) {
        this.issueNo = issueNo;
    }

    public String getReadableTitle() {
        return readableTitle;
    }

    public void setReadableTitle(String readableTitle) {
        this.readableTitle = readableTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.creationDate);
        dest.writeValue(this.id);
        dest.writeValue(this.issueNo);
        dest.writeString(this.readableTitle);
    }

    public Issue() {
    }

    protected Issue(Parcel in) {
        this.creationDate = in.readString();
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.issueNo = (Integer) in.readValue(Integer.class.getClassLoader());
        this.readableTitle = in.readString();
    }

    public static final Parcelable.Creator<Issue> CREATOR = new Parcelable.Creator<Issue>() {
        @Override
        public Issue createFromParcel(Parcel source) {
            return new Issue(source);
        }

        @Override
        public Issue[] newArray(int size) {
            return new Issue[size];
        }
    };
}