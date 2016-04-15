package com.steve.wanqureader.domain.repository;

import com.steve.wanqureader.network.model.Issue;
import com.steve.wanqureader.network.model.Post;

import java.util.ArrayList;

/**
 * Created by steve on 3/28/16.
 */
public interface IssueRepository {
    ArrayList<Post> fetchPostsByIssueNum(int id);

    ArrayList<Issue> fetchIssuesList();

    ArrayList<Issue> fetchMoreIssuesList(int page);
}
