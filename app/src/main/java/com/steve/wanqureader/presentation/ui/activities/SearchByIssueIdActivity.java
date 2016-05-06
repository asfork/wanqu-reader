package com.steve.wanqureader.presentation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.steve.wanqureader.R;
import com.steve.wanqureader.domain.executor.impl.ThreadExecutor;
import com.steve.wanqureader.network.model.Post;
import com.steve.wanqureader.presentation.presenters.SearchPresenter;
import com.steve.wanqureader.presentation.presenters.impl.SearchPresenterImpl;
import com.steve.wanqureader.presentation.ui.DividerItemDecoration;
import com.steve.wanqureader.presentation.ui.GoogleCircleProgressView;
import com.steve.wanqureader.presentation.ui.adapters.SearchPostsAdapter;
import com.steve.wanqureader.storage.PostRepositoryImpl;
import com.steve.wanqureader.threading.MainThreadImpl;
import com.steve.wanqureader.utils.Constant;
import com.steve.wanqureader.utils.CustomTabActivityHelper;
import com.steve.wanqureader.utils.DateUtil;
import com.steve.wanqureader.utils.StringUtil;
import com.steve.wanqureader.utils.WebviewFallback;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;

/**
 * Created by steve on 3/23/16.
 */
public class SearchByIssueIdActivity extends BaseActivity
        implements SearchPresenter.View, SearchView.OnQueryTextListener {
    private static final String TAG = "SearchByIssueIdActivity";
    private SearchPostsAdapter mAdapter;
    private SearchPresenter mSearchPresenter;
    private SearchView mSearchView;
    private int mIssueId;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.google_progress)
    GoogleCircleProgressView mProgressView;

    @BindString(R.string.snackbar_search)
    String issueNumberError;
    @BindString(R.string.snackbar_no_issue)
    String issueNumberNone;
    @BindString(R.string.snackbar_star)
    String postStarred;
    @BindString(R.string.snackbar_unstar)
    String postUnStarred;
    @BindString(R.string.issue_title)
    String issueTitle;

    @BindDrawable(R.drawable.divider)
    Drawable divider;

    @BindDimen(R.dimen.searchview_max_width)
    int maxWidth;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SearchByIssueIdActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        // back to home
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProgressView.setColorSchemeResources(
                R.color.google_blue,
                R.color.google_red,
                R.color.google_yellow,
                R.color.google_green);
        mProgressView.setStartEndTrim(0, (float) 0.75);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        // allows for optimizations if all items are of the same size
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(divider));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new SearchPostsAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        // instantiate the presenter
        mSearchPresenter = new SearchPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new PostRepositoryImpl(this)
        );

        mIssueId = getIntent().getIntExtra(Constant.EXTRA_ISSUE_NUMBER, 0);
        if (mIssueId != 0) {
            mSearchPresenter.fetchPostsByIssueId(mIssueId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        /**
         * 默认情况下, search widget是"iconified“的，只是用一个图标 来表示它(一个放大镜),
         * 当用户按下它的时候才显示search box . 你可以调用setIconifiedByDefault(false)让search
         * box默认都被显示。 你也可以调用setIconified()让它以iconified“的形式显示。
         */
//        mSearchView.setIconifiedByDefault(true);
        /**
         * 默认情况下是没提交搜索的按钮，所以用户必须在键盘上按下"enter"键来提交搜索.你可以同过setSubmitButtonEnabled(
         * true)来添加一个提交按钮（"submit" button)
         * 设置true后，右边会出现一个箭头按钮。如果用户没有输入，就不会触发提交（submit）事件
         */
//        mSearchView.setSubmitButtonEnabled(true);

        mSearchView.setMaxWidth(maxWidth);
        if (mIssueId == 0) mSearchView.onActionViewExpanded();
        mSearchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            AboutActivity.actionStart(this);
        } else if (id == R.id.action_issues) {
            Intent data = new Intent(Intent.ACTION_SENDTO);
            data.setData(Uri.parse(Constant.ISSUES_EMAIL));
            data.putExtra(Intent.EXTRA_SUBJECT, Constant.ISSUES_TITLE);
            startActivity(data);
        } else if (id == R.id.action_search) {
            Log.d(TAG, "search");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(TAG, "onQueryTextSubmit = " + query);
        if (mSearchView != null) {
            // 得到输入管理对象
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null && !query.equals("")) {

                if (StringUtil.isNumeric(query)) {
                    mSearchPresenter.fetchPostsByIssueId(Integer.parseInt(query));
                } else {
                    Log.d(TAG, issueNumberError);
                    Snackbar.make(mRecyclerView,
                            issueNumberError,
                            Snackbar.LENGTH_SHORT).show();
                }
                // 输入法如果是显示状态，那么就隐藏输入法
                imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 1);
            }
            mSearchView.clearFocus(); // 不获取焦点
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void showPosts(ArrayList<Post> posts) {
        mAdapter.refreshPosts(posts);
        if (mSearchView != null) mSearchView.onActionViewCollapsed();
        if (!posts.isEmpty()) {
            String title = issueTitle;
            setTitle(String.format(title, DateUtil.formatTitleDate(posts.get(0).getCreationDate()),
                    posts.get(0).getIssue()));
            Log.d(TAG, String.format(title, DateUtil.formatTitleDate(posts.get(0).getCreationDate()), posts.get(0).getIssue()));
        } else {
            setTitle("");
            Log.d(TAG, issueNumberNone);
            Snackbar.make(mRecyclerView,
                    issueNumberNone,
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClickReadPost(String url, String slug) {
        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
        CustomTabActivityHelper.openCustomTab(this, customTabsIntent,
                Uri.parse(url), new WebviewFallback());
    }

    @Override
    public void onClickStarPost(Post post) {
        mSearchPresenter.starPost(post);
    }

    @Override
    public void onPostStarred() {
        Snackbar.make(mRecyclerView, postStarred, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onClickUnStarPost(int id) {
        mSearchPresenter.unStarPost(id);
    }

    @Override
    public void onPostUnStarred() {
        Snackbar.make(mRecyclerView, postUnStarred, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onSetProgressBarVisibility(int statusCode) {
        switch (statusCode) {
            case Constant.PROGRESS_VISIBLE:
                mProgressView.start();
                break;
            case Constant.PROGRESS_INVISIBLE:
                mProgressView.stop();
                break;
            default:
                mProgressView.stop();
        }
    }

    @Override
    public void onError(String message) {
        Snackbar.make(mRecyclerView, message, Snackbar.LENGTH_SHORT).show();
    }
}
