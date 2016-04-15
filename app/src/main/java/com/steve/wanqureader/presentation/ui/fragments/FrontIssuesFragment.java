package com.steve.wanqureader.presentation.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.steve.wanqureader.R;
import com.steve.wanqureader.domain.executor.impl.ThreadExecutor;
import com.steve.wanqureader.network.model.Issue;
import com.steve.wanqureader.presentation.presenters.FrontIssuesPresenter;
import com.steve.wanqureader.presentation.presenters.impl.FrontIssuesPresenterImpl;
import com.steve.wanqureader.presentation.ui.CanRefreshLayout;
import com.steve.wanqureader.presentation.ui.DividerItemDecoration;
import com.steve.wanqureader.presentation.ui.adapters.CanRVAdapter;
import com.steve.wanqureader.presentation.ui.listeners.CanOnItemListener;
import com.steve.wanqureader.storage.IssueRepositoryImpl;
import com.steve.wanqureader.threading.MainThreadImpl;
import com.steve.wanqureader.utils.CanHolderHelper;
import com.steve.wanqureader.utils.Constant;
import com.steve.wanqureader.utils.DateUtil;
import com.steve.wanqureader.utils.SnackbarUtil;

import java.util.ArrayList;

import butterknife.Bind;


/**
 * Created by steve on 16-4-13.
 */
public class FrontIssuesFragment extends BaseFragment implements FrontIssuesPresenter.View,
        CanRefreshLayout.OnRefreshListener, CanRefreshLayout.OnLoadMoreListener {
    private static final String TAG = "FrontIssuesFragment";
    private static final String FLAG_ISSUES = "issues";
    private static final String FLAG_PAGE = "page";
    private FrontIssuesPresenter mFrontIssuesPresenter;
    private CanRVAdapter mAdapter;
    private int page = 1;

    @Bind(R.id.refresh)
    CanRefreshLayout mCanRefreshLayout;
    @Bind(R.id.can_content_view)
    RecyclerView mRecyclerView;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mCanRefreshLayout.setOnRefreshListener(this);
        mCanRefreshLayout.setOnLoadMoreListener(this);
        mCanRefreshLayout.setStyle(1, 1);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CanRVAdapter<Issue>(mRecyclerView, R.layout.item_issue) {
            @Override
            protected void setView(CanHolderHelper viewHelper, int position, Issue model) {
                String title = mContext.getResources().getString(R.string.issue_title);

                viewHelper.setText(R.id.tv_title, String.format(title,
                        DateUtil.displayTime(model.getCreationDate()),
                        model.getIssueNo()));
                viewHelper.setText(R.id.tv_article, model.getReadableTitle());
            }

            @Override
            protected void setItemListener(CanHolderHelper viewHelper) {
                viewHelper.setItemChildClickListener(R.id.linear_layout);
            }
        };

        mRecyclerView.addItemDecoration(new DividerItemDecoration(
                getResources().getDrawable(R.drawable.abc_list_divider_mtrl_alpha)));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemListener(new CanOnItemListener() {
            public void onItemChildClick(View view, int position) {
                Issue issue = (Issue) mAdapter.getItem(position);
                switch (view.getId()) {
                    case R.id.linear_layout:
                        onClickReadIssue(issue.getIssueNo());
                        break;
                }
            }
        });

        mFrontIssuesPresenter = new FrontIssuesPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new IssueRepositoryImpl(mContext)
        );

        mFrontIssuesPresenter.fetchIssuesList();
    }

    @SuppressWarnings("unchecked ")
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList(FLAG_ISSUES, mAdapter.getList());
        savedInstanceState.putInt(FLAG_PAGE, page);
    }

    @SuppressWarnings("unchecked ")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            page = savedInstanceState.getInt(FLAG_PAGE);
            Log.d(TAG, "page" + page);
            mAdapter.setList(savedInstanceState.getParcelableArrayList(FLAG_ISSUES));
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        Log.d(TAG, "fetching latest issues list" + page);
        mFrontIssuesPresenter.fetchIssuesList();
    }

    @SuppressWarnings("unchecked ")
    @Override
    public void showIssues(ArrayList<Issue> issues) {
        mAdapter.setList(issues);
    }

    @Override
    public void onLoadMore() {
        page = page + 1;
        Log.d(TAG, "fetching more issues list" + page);
        mFrontIssuesPresenter.fetchMoreIssuesList(page);
    }

    @SuppressWarnings("unchecked ")
    @Override
    public void showMoreIssues(ArrayList<Issue> issues) {
        mAdapter.addMoreList(issues);
    }

    @Override
    public void onClickReadIssue(int issueId) {
        //TODO
    }

    @Override
    public void onSetProgressBarVisibility(int statusCode) {
        switch (statusCode) {
            case Constant.PROGRESS_HEADER_INVISIBILITY:
                mCanRefreshLayout.refreshComplete();
                break;
            case Constant.PROGRESS_FOOTER_INVISIBILITY:
                mCanRefreshLayout.loadMoreComplete();
                break;
        }
    }

    @Override
    public void onError(String message) {
        SnackbarUtil.show(mCanRefreshLayout, message, 0);
    }
}
