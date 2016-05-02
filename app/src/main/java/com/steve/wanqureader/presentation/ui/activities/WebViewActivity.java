package com.steve.wanqureader.presentation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.steve.wanqureader.R;
import com.steve.wanqureader.utils.Constant;

import butterknife.BindView;

/**
 * Created by steve on 4/8/16.
 */
public class WebViewActivity extends BaseActivity {
    private static final String TAG = "WebViewActivity";
    private Intent mShareIntent;

    @BindView(R.id.linear_layout)
    LinearLayout mLinearLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.webview)
    WebView mWebView;
    @BindView(R.id.progressbar)
    ProgressBar mProgressBar;

    public static void actionStart(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(Constant.EXTRA_URL, url);
        context.startActivity(intent);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        String url = getIntent().getStringExtra(Constant.EXTRA_URL);
        mShareIntent = new Intent(Intent.ACTION_SEND);
        mShareIntent.setType(Constant.SHARE_TYPE);
        mShareIntent.putExtra(Intent.EXTRA_TEXT, url);

        setSupportActionBar(mToolbar);
        // back to home
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                if (mProgressBar != null) {
                    mProgressBar.setProgress(progress);
                    if (progress == 100) {
                        Log.d(TAG, "load done");
                        mProgressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.d(TAG, "TITLE=" + title);
                setTitle(title);
            }

        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        mWebView.loadUrl(url);
        mProgressBar.setProgress(0);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.webview, menu);
        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.action_share);
        // Fetch and store ShareActionProvider
        ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        // Connect the dots: give the ShareActionProvider its Share Intent
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(mShareIntent);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mLinearLayout != null && mWebView != null) {
            mWebView.destroy();
            mWebView = null;
            mLinearLayout.removeAllViews();
        }
    }
}
