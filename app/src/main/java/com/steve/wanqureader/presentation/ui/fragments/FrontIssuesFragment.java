package com.steve.wanqureader.presentation.ui.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.steve.wanqureader.R;
import com.steve.wanqureader.domain.executor.impl.ThreadExecutor;
import com.steve.wanqureader.network.model.Issue;
import com.steve.wanqureader.presentation.presenters.FrontIssuesPresenter;
import com.steve.wanqureader.presentation.presenters.impl.FrontIssuesPresenterImpl;
import com.steve.wanqureader.presentation.ui.adapters.IssuesAdapter;
import com.steve.wanqureader.storage.IssueRepositoryImpl;
import com.steve.wanqureader.threading.MainThreadImpl;

import java.util.ArrayList;


/**
 * Created by steve on 16-4-13.
 */
public class FrontIssuesFragment extends BaseFragment implements FrontIssuesPresenter.View {
    private FrontIssuesPresenter mFrontIssuesPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private View containerView;
    private IssuesAdapter mAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.blue700,
                R.color.green700,
                R.color.red700,
                R.color.orange700
        );

        mAdapter = new IssuesAdapter(this, mContext);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        // allows for optimizations if all items are of the same size
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(mAdapter);

        mFrontIssuesPresenter = new FrontIssuesPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new IssueRepositoryImpl(mContext)
        );
    }

//    @Override
//    public void onRefresh() {
//        mFrontIssuesPresenter.fetchIssuesList();
//    }

    @Override
    public void showIssues(ArrayList<Issue> issues) {
        mAdapter.refreshIssues(issues);
    }

    @Override
    public void onClickReadIssue(int issueId) {

    }

    @Override
    public void onSetProgressBarVisibility(int statusCode) {

    }

    @Override
    public void onError(String message) {

    }
}
