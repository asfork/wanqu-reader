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
        return StarredPost.findWithQuery(StarredPost.class,
                "SELECT * FROM StarredPost ORDER BY starred_date DESC", null);
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
    public List<Post> fetchPostsList() {
        Call<List<Post>> call = wanqu.listPosts(null);
        try {
            return call.execute().body();
        } catch (IOException e) {
            Log.e(TAG, "fetch posts list ", e);
        }
        return null;
    }

    @Override
    public List<Post> fetchMorePostsList(int page) {
        Call<List<Post>> call = wanqu.listPosts(page);
        try {
            return call.execute().body();
        } catch (IOException e) {
            Log.e(TAG, "fetch more posts list ", e);
        }
        return null;
    }
}
