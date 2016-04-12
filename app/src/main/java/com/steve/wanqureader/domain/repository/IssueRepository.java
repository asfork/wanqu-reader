package com.steve.wanqureader.domain.repository;

import com.steve.wanqureader.network.model.Issue;
import com.steve.wanqureader.network.model.Post;

import java.util.List;

/**
 * Created by steve on 3/28/16.
 */
public interface IssueRepository {
    List<Post> fetchPostsByIssueNum(int id);

    List<Issue> fetchIssuesList();
}
