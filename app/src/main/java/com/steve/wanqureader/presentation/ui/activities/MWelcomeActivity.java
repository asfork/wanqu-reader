package com.steve.wanqureader.presentation.ui.activities;

import com.stephentuso.welcome.WelcomeScreenBuilder;
import com.stephentuso.welcome.ui.WelcomeActivity;
import com.stephentuso.welcome.util.WelcomeScreenConfiguration;
import com.steve.wanqureader.R;

/**
 * Created by steve on 4/15/16.
 */
public class MWelcomeActivity extends WelcomeActivity {
    @Override
    protected WelcomeScreenConfiguration configuration() {
        return new WelcomeScreenBuilder(this)
                .theme(R.style.CustomWelcomeScreenTheme)
//                .titlePage(R.mipmap.ic_welcome_wanqu, getString(R.string.app_name), R.color.orange900)
                .basicPage(R.mipmap.ic_welcome_wanqu, getString(R.string.app_name), getString(R.string.app_description), R.color.facebook_blue)
                .parallaxPage(R.layout.parallax_welcome, getString(R.string.app_ad), getString(R.string.app_ad_info), R.color.facebook_blue, 0.2f, 2f)
                .swipeToDismiss(true)
                .exitAnimation(android.R.anim.fade_out)
                .build();
    }

    public static String welcomeKey() {
        return "WelcomeScreen";
    }
}
