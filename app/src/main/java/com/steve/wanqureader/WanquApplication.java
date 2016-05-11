package com.steve.wanqureader;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

import com.orm.SugarContext;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.steve.wanqureader.utils.ConfigUtil;
import com.steve.wanqureader.utils.Constant;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by steve on 3/28/16.
 */
public class WanquApplication extends Application {
    private static Context mContext;
    private static int themeMode;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        mContext = getApplicationContext();
        CrashReport.initCrashReport(getApplicationContext());
        initThemeMode();
        SugarContext.init(this);
    }

    private void initThemeMode() {
        themeMode = ConfigUtil.getInt(Constant.THEME_INDEX, 1);
        switch (themeMode) {
            //自动模式
            case 0:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                break;
            case 1:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case 2:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
        }
    }


    public static void setTheme(AppCompatActivity activity, int mode) {
        if (themeMode == mode) {
            return;
        }
        switch (themeMode) {
            //自动模式
            case 0:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                activity.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                break;
            case 1:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                activity.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case 2:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                activity.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
        }
        themeMode = mode;
        ConfigUtil.putInt(Constant.THEME_INDEX, themeMode);
        activity.recreate();
    }

    /**
     * 刷新UI_MODE模式
     */
    public static void refreshResources(Activity activity) {
        themeMode = ConfigUtil.getInt(Constant.THEME_INDEX, 1);
        switch (themeMode) {
            //自动模式
            case 1:
                updateConfig(activity, Configuration.UI_MODE_NIGHT_NO);
                break;
            case 2:
                updateConfig(activity, Configuration.UI_MODE_NIGHT_YES);
                break;
        }
    }


    /**
     * google官方bug，暂时解决方案
     * 手机切屏后重新设置UI_MODE模式（因为在dayNight主题下，切换横屏后UI_MODE会出错，会导致资源获取出错，需要重新设置回来）
     */
    private static void updateConfig(Activity activity, int uiNightMode) {
        Configuration newConfig = new Configuration(activity.getResources().getConfiguration());
        newConfig.uiMode &= ~Configuration.UI_MODE_NIGHT_MASK;
        newConfig.uiMode |= uiNightMode;
        activity.getResources().updateConfiguration(newConfig, null);
    }

    public static RefWatcher getRefWatcher(Context context) {
        WanquApplication application = (WanquApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }

    public static Context getContext() {
        return mContext;
    }
}
