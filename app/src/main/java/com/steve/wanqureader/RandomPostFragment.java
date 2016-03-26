package com.steve.wanqureader;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.steve.wanqureader.model.WanquService;
import com.steve.wanqureader.model.entitiy.IssueResult;
import com.steve.wanqureader.model.entitiy.Post;
import com.steve.wanqureader.util.DateUtil;
import com.steve.wanqureader.util.SnackbarUtil;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Steve Zhang
 * 2/23/16
 * <p/>
 * If it works, I created it. If not, I didn't.
 */
public class RandomPostFragment extends BaseFragment {
    @Bind(R.id.tv_header_domain)
    TextView domainView;
    @Bind(R.id.tv_title)
    TextView titleView;
    @Bind(R.id.tv_summary)
    TextView summaryView;
    @Bind(R.id.tv_header_date)
    TextView dateView;
    @Bind(R.id.tv_header_costs)
    TextView costsView;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_random_post;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {}

    @OnClick(R.id.button)
    public void clickRandomButton(final View view) {
        fetchingRandomPost(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchingRandomPost(mRootView);
    }

    private void fetchingRandomPost(final View view) {
        final ProgressDialog dialog = ProgressDialog.show(context, "", "loading...");

        WanquService.WanquInterface service = WanquService.getClient();
        Call<IssueResult> call = service.getRandomPost();
        call.enqueue(new Callback<IssueResult>() {
            @Override
            public void onResponse(Call<IssueResult> call, Response<IssueResult> response) {
                dialog.dismiss();
                Log.d("Random", "Status Code = " + response.code());
                if (response.isSuccessful()) {
                    IssueResult issueResult = response.body();
                    Log.d("Random", "response = " + new Gson().toJson(issueResult));
                    Post post = issueResult.data.posts.get(0);

                    String readTime = context.getResources().getString(R.string.read_time);

                    domainView.setText(post.urlDomain);
                    dateView.setText(DateUtil.displayTime(post.createdAt));
                    costsView.setText(String.format(readTime, post.readTime));
                    titleView.setText(post.title);
                    summaryView.setText(post.summary);
                } else {
                    SnackbarUtil.show(mRootView, "Response Error" + response.code(), 0);
                }
            }

            @Override
            public void onFailure(Call<IssueResult> call, Throwable t) {
                dialog.dismiss();
                SnackbarUtil.show(mRootView, "Calling Failure", 0);
            }
        });
    }
}
