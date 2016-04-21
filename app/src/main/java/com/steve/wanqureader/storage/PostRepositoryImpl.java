package com.steve.wanqureader.storage;

import android.content.Context;
import android.util.Log;

import com.steve.wanqureader.domain.repository.PostRepository;
import com.steve.wanqureader.network.RestClient;
import com.steve.wanqureader.network.model.Post;
import com.steve.wanqureader.network.services.WanquService;
import com.steve.wanqureader.storage.converters.StorageModelConverter;
import com.steve.wanqureader.storage.model.StarredPost;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by steve on 3/29/16.
 */
public class PostRepositoryImpl implements PostRepository {
    private static String TAG = "PostRepositoryImpl";
    private Context mContext;
    private WanquService wanqu;

    public PostRepositoryImpl(Context context) {
        mContext = context;
        wanqu = RestClient.getService(WanquService.class);
    }

    @Override
    public void insert(Post post) {
        StarredPost starredPost = StorageModelConverter.convertToStoragePostModel(post);
        starredPost.save();
    }

    @Override
    public void delete(StarredPost post) {
        post.delete();
    }

    @Override
    public StarredPost getStarredPostbyId(long id) {
        return StarredPost.findById(StarredPost.class, id);
    }

    @Override
    public List<StarredPost> getStarredPostsList() {
//        return StarredPost.findWithQuery(StarredPost.class,
//                "SELECT * FROM STARRED_POST ORDER BY STARREDDATE DESC", null);
        return StarredPost.find(StarredPost.class, null, null, null, "STARREDDATE DESC", null);
    }

    @Override
    public Post fetchPostByNum(int id) {
        Call<Post> call = wanqu.postById(id);
        try {
            return call.execute().body();
        } catch (IOException e) {
            Log.e(TAG, "fetch post by number ", e);
        }
        return null;
    }

    @Override
    public ArrayList<Post> fetchPostsByIssueNum(int id) {
        Call<ArrayList<Post>> call = wanqu.postsByIssue(id);
        try {
            return call.execute().body();
        } catch (IOException e) {
            Log.e(TAG, "fetch posts by issue number ", e);
        }
        return null;
    }

    @Override
    public ArrayList<Post> fetchPostsList() {
        Call<ArrayList<Post>> call = wanqu.listPosts(null);
        try {
            return call.execute().body();
        } catch (IOException e) {
            Log.e(TAG, "fetch posts list ", e);
        }
        return null;
    }

    @Override
    public ArrayList<Post> fetchMorePostsList(int page) {
        Call<ArrayList<Post>> call = wanqu.listPosts(page);
        try {
            return call.execute().body();
        } catch (IOException e) {
            Log.e(TAG, "fetch more posts list ", e);
        }
        return null;
    }
}
