package com.steve.wanqureader.presentation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.steve.wanqureader.utils.SnackbarUtil;
import com.steve.wanqureader.utils.StringUtil;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by steve on 3/23/16.
 */
public class SearchByIssueIdActivity extends BaseActivity
        implements SearchPresenter.View, SearchView.OnQueryTextListener {
    private static final String TAG = "SearchByIssueIdActivity";
    private SearchPostsAdapter mAdapter;
    private SearchPresenter mSearchPresenter;
    private SearchView mSearchView;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.google_progress)
    GoogleCircleProgressView mProgressView;

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
//        mProgressView.setStartEndTrim(0, (float) 0.75);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        // allows for optimizations if all items are of the same size
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(
                getResources().getDrawable(R.drawable.divider)));
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
//        mSearchView.setIconifiedByDefault(false);
        /**
         * 默认情况下是没提交搜索的按钮，所以用户必须在键盘上按下"enter"键来提交搜索.你可以同过setSubmitButtonEnabled(
         * true)来添加一个提交按钮（"submit" button)
         * 设置true后，右边会出现一个箭头按钮。如果用户没有输入，就不会触发提交（submit）事件
         */
//        mSearchView.setSubmitButtonEnabled(true);

        mSearchView.onActionViewExpanded();
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
                    SnackbarUtil.show(mRecyclerView, getString(R.string.snackbar_search), 0);
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
    }

    @Override
    public void onClickReadPost(String url, String slug) {

    }

    @Override
    public void onClickStarPost(Post post) {

    }

    @Override
    public void onPostStarred() {

    }

    @Override
    public void onSetProgressBarVisibility(int statusCode) {
        switch (statusCode) {
            case Constant.VISIBLE:
                mProgressView.setVisibility(statusCode);
                break;
            case Constant.INVISIBLE:
                mProgressView.setVisibility(statusCode);
                break;
            case Constant.GONE:
                mProgressView.setVisibility(statusCode);
                break;
            default:
                mProgressView.setVisibility(statusCode);
        }
    }

    @Override
    public void onError(String message) {
        SnackbarUtil.show(mRecyclerView, message, 0);
    }
}
