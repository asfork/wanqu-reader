package com.steve.wanqureader;

import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.steve.wanqureader.model.WanquService;
import com.steve.wanqureader.model.entitiy.IssueResult;
import com.steve.wanqureader.model.entitiy.Post;
import com.steve.wanqureader.util.SnackbarUtil;

import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by steve on 3/22/16.
 */
public class LatestPostsFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener, PostsAdapter.OnItemClickListener {
    List<Post> posts;
    PostsAdapter postsAdapter;

    @Bind(R.id.swipe) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.list) RecyclerView recyclerView;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_common_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        swipeRefreshLayout.setColorSchemeResources(
                R.color.blue700,
                R.color.green700,
                R.color.red700,
                R.color.orange700
        );
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        // allows for optimizations if all items are of the same size
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        fetchingRandomPost(mRootView);
        postsAdapter = new PostsAdapter(posts, context);
        postsAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(postsAdapter);

//        RecyclerView.ItemDecoration itemDecoration =
//                new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST);
//        recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void onRefresh() {
        fetchingRandomPost(mRootView);
    }

    @Override
    public void onItemClick(View view, int position) {
        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
        CustomTabActivityHelper.openCustomTab(this.getActivity(), customTabsIntent,
                Uri.parse(posts.get(position).url), new WebviewFallback());
    }

    private void fetchingRandomPost(final View view) {
        WanquService.WanquInterface service = WanquService.getClient();
        Call<IssueResult> call = service.getLatestIssue();
        call.enqueue(new Callback<IssueResult>() {
            @Override
            public void onResponse(Call<IssueResult> call, Response<IssueResult> response) {
                swipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    IssueResult issueResult = response.body();
                    Log.d("Random", "response = " + new Gson().toJson(issueResult));
                    posts = issueResult.data.posts;
                    postsAdapter.setData(posts);
                    postsAdapter.notifyDataSetChanged();
                } else {
                    SnackbarUtil.show(mRootView, "Response Error" + response.code(), 0);
                }
            }

            @Override
            public void onFailure(Call<IssueResult> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                SnackbarUtil.show(mRootView, "Calling Failure", 0);
            }
        });
    }
}
