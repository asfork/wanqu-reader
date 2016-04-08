package com.steve.wanqureader.utils;

import android.app.Activity;
import android.net.Uri;

import com.steve.wanqureader.presentation.ui.activities.WebViewActivity;

/**
 * A Fallback that opens a Webview when Custom Tabs is not available
 */
public class WebviewFallback implements CustomTabActivityHelper.CustomTabFallback {
    @Override
    public void openUri(Activity activity, Uri uri) {
        WebViewActivity.actionStart(activity, uri.toString());
    }
}