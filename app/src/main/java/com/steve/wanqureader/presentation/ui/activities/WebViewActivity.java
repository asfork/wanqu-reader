package com.steve.wanqureader.presentation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.steve.wanqureader.R;
import com.steve.wanqureader.utils.Constant;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by steve on 4/8/16.
 */
public class WebViewActivity extends AppCompatActivity {
    private static final String TAG = "WebViewActivity";

    private View containerView;
    private LinearLayout mLinearLayout;
    private WebView mWebView;
    private String url;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    public static void actionStart(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constant.EXTRA_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

        url = getIntent().getStringExtra(Constant.EXTRA_URL);

        toolbar.setTitle(getString(R.string.activity_about));
        setSupportActionBar(toolbar);
        // back to home
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        containerView = View.inflate(this, R.layout.view_web, null);
        mLinearLayout = (LinearLayout) containerView.findViewById(R.id.linear_layout);
        mWebView = (WebView) containerView.findViewById(R.id.web_view);
        FrameLayout parentsView = (FrameLayout) findViewById(R.id.frame_layout);
        if (parentsView == null) return;
        parentsView.addView(containerView);

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
