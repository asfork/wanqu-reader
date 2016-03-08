package com.steve.wanqureader.model.entitiy;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by steve on 3/8/16.
 */
public class IssueWrapper {
    @SerializedName("oldest_timestamp")
    public long oldestTimestamp;
    public List<Issue> issues;
}
