package com.steve.wanqureader.network.services;

import com.steve.wanqureader.network.model.Issue;
import com.steve.wanqureader.network.model.Post;
import com.steve.wanqureader.network.model.Tag;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Steve Zhang
 * 2/16/16
 * <p/>
 * If it works, I created it. If not, I didn't.
 */

public interface WanquService {
    @GET("posts")
    Call<List<Post>> listPosts();

    @GET("posts/{id}")
    Call<List<Post>> postById(@Path("id") int postId);

    @GET("issues")
    Call<List<Issue>> listIssues();

    @GET("issue/{id}")
    Call<List<Post>> postsByIssue(@Path("id") int issueId);

    @GET("tag/{id}")
    Call<List<Tag>> tagById(@Path("id") int tagId);
}

