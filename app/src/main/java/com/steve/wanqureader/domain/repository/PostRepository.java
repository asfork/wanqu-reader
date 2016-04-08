package com.steve.wanqureader.domain.repository;

import com.steve.wanqureader.network.model.Post;

import java.util.List;

/**
 * Created by steve on 3/28/16.
 */
public interface PostRepository {
    void like(Post post);

    void unlike(Post post);

    Post fetchPostById(long id);

    List<Post> fetchPostsList();

    List<Post> fetchMorePostsList(Integer page);

    List<Post> fetchPostsByIssuesId();
}
