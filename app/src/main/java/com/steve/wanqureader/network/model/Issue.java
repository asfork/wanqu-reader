package com.steve.wanqureader.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Issue {

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
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The issueNo
     */
    public Integer getIssueNo() {
        return issueNo;
    }

    /**
     *
     * @param issueNo
     * The issue_no
     */
    public void setIssueNo(Integer issueNo) {
        this.issueNo = issueNo;
    }

    /**
     * @return The readableTitle
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

}