package com.steve.wanqureader.presentation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.steve.wanqureader.R;
import com.steve.wanqureader.domain.executor.impl.ThreadExecutor;
import com.steve.wanqureader.network.model.Issue;
import com.steve.wanqureader.presentation.presenters.FrontIssuesPresenter;
import com.steve.wanqureader.presentation.presenters.impl.FrontIssuesPresenterImpl;
import com.steve.wanqureader.presentation.ui.adapters.IssuesAdapter;
import com.steve.wanqureader.storage.IssueRepositoryImpl;
import com.steve.wanqureader.threading.MainThreadImpl;
import com.steve.wanqureader.utils.SnackbarUtil;

import java.util.List;

/**
 * Created by steve on 3/23/16.
 */
public class FrontIssuesActivity extends BaseActivity
        implements FrontIssuesPresenter.View, SwipeRefreshLayout.OnRefreshListener {
    private FrontIssuesPresenter mFrontIssuesPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private View containerView;
    private IssuesAdapter mAdapter;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, FrontIssuesActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        containerView = View.inflate(this, R.layout.view_common_list, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) containerView.findViewById(R.id.swipe);
        mRecyclerView = (RecyclerView) containerView.findViewById(R.id.list);

        FrameLayout parentsView = (FrameLayout) findViewById(R.id.frame_layout);
        if (parentsView == null) return;
        parentsView.addView(containerView);

        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.blue700,
                R.color.green700,
                R.color.red700,
                R.color.orange700
        );
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mAdapter = new IssuesAdapter(this, this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // allows for optimizations if all items are of the same size
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(mAdapter);

        mFrontIssuesPresenter = new FrontIssuesPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new IssueRepositoryImpl(this)
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFrontIssuesPresenter.resume();
    }

    @Override
    public void onRefresh() {
        mFrontIssuesPresenter.fetchIssuesList();
    }

    @Override
    public void showIssues(List<Issue> issues) {
        mAdapter.refreshIssues(issues);
    }

    @Override
    public void onClickReadIssue(int issueId) {

    }

    @Override
    public void onSetProgressBarVisibility(boolean visibility) {
        mSwipeRefreshLayout.setRefreshing(visibility);
    }

    @Override
    public void onError(String message) {
        SnackbarUtil.show(containerView, message, 0);
    }
}
