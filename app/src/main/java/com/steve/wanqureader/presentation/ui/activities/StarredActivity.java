package com.steve.wanqureader.presentation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.steve.wanqureader.R;
import com.steve.wanqureader.domain.executor.impl.ThreadExecutor;
import com.steve.wanqureader.presentation.presenters.StarredPresenter;
import com.steve.wanqureader.presentation.presenters.impl.StarredPresenterImpl;
import com.steve.wanqureader.presentation.ui.adapters.StarredPostsAdapter;
import com.steve.wanqureader.storage.PostRepositoryImpl;
import com.steve.wanqureader.storage.model.StarredPost;
import com.steve.wanqureader.threading.MainThreadImpl;
import com.steve.wanqureader.utils.CustomTabActivityHelper;
import com.steve.wanqureader.utils.WebviewFallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by steve on 4/8/16.
 */
public class StarredActivity extends AppCompatActivity implements StarredPresenter.View {
    private static String TAG = "StarredActivity";
    private StarredPostsAdapter mAdapter;
    private StarredPresenter mStarredPresenter;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, WebViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starred);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        setSupportActionBar(mToolbar);
        // back to home
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAdapter = new StarredPostsAdapter(this, this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // allows for optimizations if all items are of the same size
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(mAdapter);

        // instantiate the presenter
        mStarredPresenter = new StarredPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new PostRepositoryImpl(this)
        );
        mStarredPresenter.fetchStarredPostsList();
    }

    @Override
    protected void onResume() {
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
        CustomTabActivityHelper.openCustomTab(this, customTabsIntent,
                Uri.parse(url), new WebviewFallback());
    }

    @Override
    public void onClickUnstarPost(int postNo) {
        mStarredPresenter.unStarPost(postNo);
    }

    @Override
    public void onPostUnstarred(StarredPost post) {
        //TODO
    }

    @Override
    public void onSetProgressBarVisibility(boolean visibility) {

    }

    @Override
    public void onError(String message) {

    }
}
