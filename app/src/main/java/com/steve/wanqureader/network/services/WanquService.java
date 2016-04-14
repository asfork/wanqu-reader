package com.steve.wanqureader.network.services;

import com.steve.wanqureader.network.model.Issue;
import com.steve.wanqureader.network.model.Post;
import com.steve.wanqureader.network.model.Tag;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Steve Zhang
 * 2/16/16
 * <p/>
 * If it works, I created it. If not, I didn't.
 */

public interface WanquService {
    @GET("posts")
    Call<ArrayList<Post>> listPosts(@Query("page") Integer page);

    @GET("posts/{id}")
    Call<Post> postById(@Path("id") int postId);

    @GET("issues")
    Call<ArrayList<Issue>> listIssues(@Query("page") Integer page);

    @GET("issue/{id}")
    Call<ArrayList<Post>> postsByIssue(@Path("id") int issueId);

    @GET("tag/{id}")
    Call<Tag> tagById(@Path("id") long tagId);
}

