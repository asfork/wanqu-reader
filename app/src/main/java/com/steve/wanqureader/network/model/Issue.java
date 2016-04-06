package com.steve.wanqureader.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Issue {

    @SerializedName("creation_date")
    @Expose
    private String creationDate;
    @SerializedName("issue_id")
    @Expose
    private Integer issueId;
    @SerializedName("summary")
    @Expose
    private String summary;

    /**
     * @return The creationDate
     */
    public String getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate The creation_date
     */
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return The issueId
     */
    public Integer getIssueId() {
        return issueId;
    }

    /**
     * @param issueId The issue_id
     */
    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }

    /**
     * @return The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary The summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

}