package com.steve.wanqureader.presentation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.steve.wanqureader.R;
import com.steve.wanqureader.utils.Constant;

import butterknife.Bind;

/**
 * Created by steve on 4/8/16.
 */
public class WebViewActivity extends BaseActivity {
    private static final String TAG = "WebViewActivity";
    private ShareActionProvider mShareActionProvider;
    private Intent mShareIntent;
    private String url;
    private String slug;

    @Bind(R.id.linear_layout)
    LinearLayout mLinearLayout;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.web_view)
    WebView mWebView;

    public static void actionStart(Context context, String slug, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(Constant.EXTRA_SLUG, slug);
        intent.putExtra(Constant.EXTRA_URL, url);
        context.startActivity(intent);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        slug = getIntent().getStringExtra(Constant.EXTRA_SLUG);
        url = getIntent().getStringExtra(Constant.EXTRA_URL);
        mShareIntent = new Intent(Intent.ACTION_SEND);
        mShareIntent.setType(Constant.SHARE_TYPE);
        mShareIntent.putExtra(Intent.EXTRA_TEXT, url);

        mToolbar.setTitle(slug);
        setSupportActionBar(mToolbar);
        // back to home
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.loadUrl(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.webview, menu);
        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.action_share);
        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
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
            mLinearLayout.removeAllViews();
            mWebView.destroy();
        }
    }
}
