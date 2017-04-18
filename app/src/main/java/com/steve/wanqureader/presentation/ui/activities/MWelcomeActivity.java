package com.steve.wanqureader.presentation.ui.activities;

import com.stephentuso.welcome.BasicPage;
import com.stephentuso.welcome.ParallaxPage;
import com.stephentuso.welcome.WelcomeActivity;
import com.stephentuso.welcome.WelcomeConfiguration;
import com.steve.wanqureader.R;

/**
 * Created by steve on 4/15/16.
 */
public class MWelcomeActivity extends WelcomeActivity {

    @Override
    protected WelcomeConfiguration configuration() {
        return new WelcomeConfiguration.Builder(this)
                .page(new BasicPage(R.mipmap.ic_welcome_wanqu,
                        getString(R.string.app_name),
                        getString(R.string.app_description))
                .background(R.color.facebook_blue))

                .page(new ParallaxPage(R.layout.parallax_welcome,
                        getString(R.string.app_ad),
                        getString(R.string.app_ad_info))
                .lastParallaxFactor(2f)
                .background(R.color.facebook_blue))

                .swipeToDismiss(true)
                .exitAnimation(android.R.anim.fade_out)
                .build();
    }

    public static String welcomeKey() {
        return "WelcomeScreen";
    }
}
