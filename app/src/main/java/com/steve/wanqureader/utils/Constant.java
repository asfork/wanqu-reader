package com.steve.wanqureader.utils;

/**
 * Created by Steve Zhang
 * 2/23/16
 * <p/>
 * If it works, I created it. If not, I didn't.
 */
public class Constant {
    public static final String SITE_URL = "https://wanqu.co";
    public static final String BASE_API_URL = "http://162.243.150.140/api/v1/";

    public static final String EXTRA_SLUG = "extra.slug";
    public static final String EXTRA_URL = "extra.url";
    public static final String EXTRA_ISSUE_NUMBER = "extra.issueNum";

    public static final String ISSUES_EMAIL = "mailto:stevzhg+wanqu@gmail.com";
    public static final String ISSUES_TITLE = "Wanqu Reader Issues";

    public static final int PROGRESS_VISIBLE = 0x00000000;
    public static final int PROGRESS_HEADER_INVISIBILITY = 0x00000002;
    public static final int PROGRESS_FOOTER_INVISIBILITY = 0x00000003;
    public static final int PROGRESS_INVISIBLE = 0x00000004;

    public static final int CACHE_TIME = 5 * 60; //cache for 5 minute
    public static final int CACHE_SIZE = 10 * 1024 * 1024;

    public static final String SHARE_TYPE = "text/plain";
}
