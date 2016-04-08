package com.steve.wanqureader.storage;

import android.content.Context;
import android.util.Log;

import com.steve.wanqureader.domain.repository.IssueRepository;
import com.steve.wanqureader.network.RestClient;
import com.steve.wanqureader.network.model.Issue;
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

    public IssueRepositoryImpl(Context context) {
        mContext = context;
    }

    @Override
    public Issue fetchIssueById(long id) {
        return null;
    }

    @Override
    public List<Issue> fetchIssuesList() {
        WanquService wanqu = RestClient.getService(WanquService.class);
        Call<List<Issue>> call = wanqu.listIssues();
        try {
            List<Issue> issues = call.execute().body();
            return issues;
        } catch (IOException e) {
            Log.e(TAG, "fetch Issues List ", e);
        }
        return null;
    }
}
