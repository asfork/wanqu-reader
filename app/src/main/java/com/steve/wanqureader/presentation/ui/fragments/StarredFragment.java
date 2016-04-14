package com.steve.wanqureader.presentation.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.steve.wanqureader.R;
import com.steve.wanqureader.domain.executor.impl.ThreadExecutor;
import com.steve.wanqureader.presentation.presenters.StarredPresenter;
import com.steve.wanqureader.presentation.presenters.impl.StarredPresenterImpl;
import com.steve.wanqureader.presentation.ui.adapters.StarredPostsAdapter;
import com.steve.wanqureader.storage.PostRepositoryImpl;
import com.steve.wanqureader.storage.model.StarredPost;
import com.steve.wanqureader.threading.MainThreadImpl;
import com.steve.wanqureader.utils.CustomTabActivityHelper;
import com.steve.wanqureader.utils.SnackbarUtil;
import com.steve.wanqureader.utils.WebviewFallback;

import java.util.List;

import butterknife.Bind;

/**
 * Created by steve on 3/22/16.
 */
public class StarredFragment extends BaseFragment implements StarredPresenter.View {
    private static String TAG = "StarredFragment";
    private StarredPostsAdapter mAdapter;
    private StarredPresenter mStarredPresenter;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_starred;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mAdapter = new StarredPostsAdapter(this, mContext);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        // allows for optimizations if all items are of the same size
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(mAdapter);

        // instantiate the presenter
        mStarredPresenter = new StarredPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new PostRepositoryImpl(mContext)
        );
        mStarredPresenter.fetchStarredPostsList();
    }

    @Override
    public void onResume() {
        super.onResume();
        mStarredPresenter.resume();
    }

    @Override
    public void showPosts(List<StarredPost> posts) {
        mAdapter.refreshPosts(posts);
    }

    @Override
    public void onClickReadStarredPost(String url, String slug) {
        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
        CustomTabActivityHelper.openCustomTab(getActivity(), customTabsIntent,
                Uri.parse(url), new WebviewFallback());
    }

    @Override
    public void onClickUnstarPost(int postNo) {
        mStarredPresenter.unStarPost(postNo);
    }

    @Override
    public void onPostUnstarred(StarredPost post) {
        Log.d(TAG, "Post Unstarred");
    }

    @Override
    public void onSetProgressBarVisibility(int statusCode) {

    }

    @Override
    public void onError(String message) {
        SnackbarUtil.show(mRecyclerView, message, 0);
    }
}
