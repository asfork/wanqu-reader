package com.steve.wanqureader.domain.repository;

import com.steve.wanqureader.network.model.Issue;

import java.util.List;

/**
 * Created by steve on 3/28/16.
 */
public interface IssueRepository {
    Issue fetchIssueById(long id);

    List<Issue> fetchIssuesList();
}
