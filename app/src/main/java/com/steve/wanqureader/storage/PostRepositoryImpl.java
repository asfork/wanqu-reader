package com.steve.wanqureader.storage;

import android.content.Context;
import android.util.Log;

import com.steve.wanqureader.domain.repository.PostRepository;
import com.steve.wanqureader.network.RestClient;
import com.steve.wanqureader.network.model.Post;
import com.steve.wanqureader.network.services.WanquService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

/**
 * Created by steve on 3/29/16.
 */
public class PostRepositoryImpl implements PostRepository {
    private static String TAG = "PostRepositoryImpl";
    private Context mContext;

    public PostRepositoryImpl(Context context) {
        mContext = context;
    }

    @Override
    public void like(Post post) {

    }

    @Override
    public void unlike(Post post) {

    }

    @Override
    public Post fetchPostById(long id) {
        return null;
    }

    @Override
    public List<Post> fetchPostsList() {
        WanquService wanqu = RestClient.getService(WanquService.class);
        Call<List<Post>> call = wanqu.listPosts();
        try {
            List<Post> posts = call.execute().body();
            return posts;
        } catch (IOException e) {
            Log.e(TAG, "", e);
        }
        return null;
    }

    @Override
    public List<Post> fetchPostsByIssuesId() {
        return null;
    }
}
