package com.steve.wanqureader.presentation.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.steve.wanqureader.R;
import com.steve.wanqureader.domain.executor.impl.ThreadExecutor;
import com.steve.wanqureader.network.model.Post;
import com.steve.wanqureader.presentation.presenters.PostsPresenter;
import com.steve.wanqureader.presentation.presenters.impl.PostsPresenterImpl;
import com.steve.wanqureader.presentation.ui.CanRefreshLayout;
import com.steve.wanqureader.presentation.ui.DividerItemDecoration;
import com.steve.wanqureader.presentation.ui.adapters.CanRVAdapter;
import com.steve.wanqureader.presentation.ui.listeners.CanOnItemListener;
import com.steve.wanqureader.storage.PostRepositoryImpl;
import com.steve.wanqureader.threading.MainThreadImpl;
import com.steve.wanqureader.utils.CanHolderHelper;
import com.steve.wanqureader.utils.Constant;
import com.steve.wanqureader.utils.CustomTabActivityHelper;
import com.steve.wanqureader.utils.DateUtil;
import com.steve.wanqureader.utils.WebviewFallback;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * ; * Created by steve on 16-4-13.
 */
public class PostsFragment extends BaseFragment
        implements PostsPresenter.View,
        CanRefreshLayout.OnRefreshListener, CanRefreshLayout.OnLoadMoreListener {

    private static final String TAG = "PostsFragment";
    private static final String FLAG_POSTS = "posts";
    private static final String FLAG_PAGE = "page";
    private CanRVAdapter mAdapter;
    private PostsPresenter mPostsPresenter;
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
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CanRVAdapter<Post>(mRecyclerView, R.layout.item_post) {
            @Override
            protected void setView(CanHolderHelper viewHelper, int position, Post model) {
                String info = mContext.getResources().getString(R.string.post_info);

                viewHelper.setText(R.id.tv_header_domain, model.getUrlDomain());
                viewHelper.setText(R.id.tv_header_info, String.format(info,
                        DateUtil.displayTime(model.getCreationDate()),
                        model.getReadTimeMinutes()));
                viewHelper.setText(R.id.tv_title, model.getReadableTitle());
                viewHelper.setText(R.id.tv_article, model.getReadableArticle());
            }

            @Override
            protected void setItemListener(CanHolderHelper viewHelper) {
                viewHelper.setItemChildClickListener(R.id.linear_layout);
                viewHelper.setItemChildClickListener(R.id.ib_star);
            }
        };

        mRecyclerView.addItemDecoration(new DividerItemDecoration(
                getResources().getDrawable(R.drawable.divider)));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemListener(new CanOnItemListener() {
            public void onItemChildClick(View view, int position) {
                Post post = (Post) mAdapter.getItem(position);
                switch (view.getId()) {
                    case R.id.linear_layout:
                        onClickReadPost(post.getUrl(), post.getSlug());
                        break;
                    case R.id.ib_star:
                        onClickStarPost(post);
                        break;
                }
            }
        });

        // instantiate the presenter
        mPostsPresenter = new PostsPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new PostRepositoryImpl(mContext)
        );

        mPostsPresenter.fetchPostsList();
        onSetProgressBarVisibility(Constant.PROGRESS_VISIBLE);
    }

    @SuppressWarnings("unchecked ")
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList(FLAG_POSTS, mAdapter.getList());
        savedInstanceState.putInt(FLAG_PAGE, page);
    }

    @SuppressWarnings("unchecked ")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            page = savedInstanceState.getInt(FLAG_PAGE);
            Log.d(TAG, "page" + page);
            mAdapter.setList(savedInstanceState.getParcelableArrayList(FLAG_POSTS));
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        Log.d(TAG, "fetching latest posts list" + page);
        mPostsPresenter.fetchPostsList();
    }

    @SuppressWarnings("unchecked ")
    @Override
    public void showPosts(ArrayList<Post> posts) {
        mAdapter.setList(posts);
    }

    @Override
    public void onLoadMore() {
        page = page + 1;
        Log.d(TAG, "fetching more posts list" + page);
        mPostsPresenter.fetchMorePostsList(page);
    }

    @SuppressWarnings("unchecked ")
    @Override
    public void showMorePosts(ArrayList<Post> posts) {
        mAdapter.addMoreList(posts);
    }

    @Override
    public void onClickReadPost(String url, String slug) {
        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
        CustomTabActivityHelper.openCustomTab(getActivity(), customTabsIntent,
                Uri.parse(url), new WebviewFallback());
    }

    @Override
    public void onClickStarPost(Post post) {
        mPostsPresenter.starPost(post);
    }

    @Override
    public void onPostStarred() {
        Snackbar.make(mRecyclerView, getString(R.string.snackbar_grade), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onSetProgressBarVisibility(int statusCode) {
        switch (statusCode) {
            case Constant.PROGRESS_VISIBLE:
                mCanRefreshLayout.autoRefresh();
                break;
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
        Snackbar.make(mRecyclerView, message, Snackbar.LENGTH_SHORT).show();
    }
}
