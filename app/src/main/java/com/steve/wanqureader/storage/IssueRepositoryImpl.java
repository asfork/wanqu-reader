package com.steve.wanqureader.storage;

import android.content.Context;
import android.util.Log;

import com.steve.wanqureader.domain.repository.IssueRepository;
import com.steve.wanqureader.network.RestClient;
import com.steve.wanqureader.network.model.Issue;
import com.steve.wanqureader.network.model.Post;
import com.steve.wanqureader.network.services.WanquService;

import java.io.IOException;
import java.util.ArrayList;

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
    public ArrayList<Post> fetchPostsByIssueNum(int id) {
        Call<ArrayList<Post>> call = wanqu.postsByIssue(id);
        try {
            return call.execute().body();
        } catch (IOException e) {
            Log.e(TAG, "fetch posts by issues number ", e);
        }
        return null;
    }

    @Override
    public ArrayList<Issue> fetchIssuesList() {
        Call<ArrayList<Issue>> call = wanqu.listIssues(null);
        try {
            return call.execute().body();
        } catch (IOException e) {
            Log.e(TAG, "fetch issues list ", e);
        }
        return null;
    }

    @Override
    public ArrayList<Issue> fetchMoreIssuesList(int page) {
        Call<ArrayList<Issue>> call = wanqu.listIssues(page);
        try {
            return call.execute().body();
        } catch (IOException e) {
            Log.e(TAG, "fetch more issues list ", e);
        }
        return null;
    }
}
