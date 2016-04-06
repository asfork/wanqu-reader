package com.steve.wanqureader.presentation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.steve.wanqureader.R;
import com.steve.wanqureader.domain.executor.impl.ThreadExecutor;
import com.steve.wanqureader.network.model.Post;
import com.steve.wanqureader.presentation.CustomTabActivityHelper;
import com.steve.wanqureader.presentation.WebviewFallback;
import com.steve.wanqureader.presentation.presenters.MainPresenter;
import com.steve.wanqureader.presentation.presenters.impl.MainPresenterImpl;
import com.steve.wanqureader.presentation.ui.adapters.PostsAdapter;
import com.steve.wanqureader.storage.PostRepositoryImpl;
import com.steve.wanqureader.threading.MainThreadImpl;
import com.steve.wanqureader.utils.SnackbarUtil;

import java.util.List;

public class MainActivity extends BaseActivity
        implements MainPresenter.View, SwipeRefreshLayout.OnRefreshListener {
    private PostsAdapter mAdapter;
    private MainPresenter mMainPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private View containerView;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
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

        mAdapter = new PostsAdapter(this, this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // allows for optimizations if all items are of the same size
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(mAdapter);

        // instantiate the presenter
        mMainPresenter = new MainPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new PostRepositoryImpl(this)
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMainPresenter.resume();
    }

    @Override
    public void onRefresh() {
        mMainPresenter.fetchPostsList();
    }

    @Override
    public void showPosts(List<Post> posts) {
        mAdapter.refreshPosts(posts);
    }

    @Override
    public void onClickReadPost(Post post) {
        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
        CustomTabActivityHelper.openCustomTab(this, customTabsIntent,
                Uri.parse(post.getUrl()), new WebviewFallback());
    }

    @Override
    public void onClickLikePost(Post post) {

    }

    @Override
    public void onPostLiked(Post post) {

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
