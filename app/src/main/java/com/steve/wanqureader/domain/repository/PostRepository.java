package com.steve.wanqureader.domain.repository;

import com.steve.wanqureader.network.model.Post;
import com.steve.wanqureader.storage.model.StarredPost;

import java.util.List;

/**
 * Created by steve on 3/28/16.
 */
public interface PostRepository {
    void insert(Post post);

    void delete(StarredPost post);

    Post fetchPostByNum(int id);

    List<Post> fetchPostsList();

    List<Post> fetchMorePostsList(int page);

    List<StarredPost> getStarredPostsList();

    StarredPost getStarredPostbyId(long id);
}
