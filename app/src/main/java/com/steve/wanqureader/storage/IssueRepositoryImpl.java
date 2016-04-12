package com.steve.wanqureader.storage;

import android.content.Context;
import android.util.Log;

import com.steve.wanqureader.domain.repository.IssueRepository;
import com.steve.wanqureader.network.RestClient;
import com.steve.wanqureader.network.model.Issue;
import com.steve.wanqureader.network.model.Post;
import com.steve.wanqureader.network.services.WanquService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

/**
 * Created by steve on 4/6/16.
 */
public class IssueRepositoryImpl implements IssueRepository {
    private static String TAG = "IssueRepositoryImpl";
    private Context mContext;
    private WanquService wanqu;

    public IssueRepositoryImpl(Context context) {
        mContext = context;
        wanqu = RestClient.getService(WanquService.class);
    }

    @Override
    public List<Post> fetchPostsByIssueNum(int id) {
        Call<List<Post>> call = wanqu.postsByIssue(id);
        try {
            return call.execute().body();
        } catch (IOException e) {
            Log.e(TAG, "fetch posts by issues number ", e);
        }
        return null;
    }

    @Override
    public List<Issue> fetchIssuesList() {
        Call<List<Issue>> call = wanqu.listIssues(null);
        try {
            return call.execute().body();
        } catch (IOException e) {
            Log.e(TAG, "fetch issues list ", e);
        }
        return null;
    }
}
