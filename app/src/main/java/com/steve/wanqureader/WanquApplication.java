package com.steve.wanqureader;

import android.app.Application;
import android.content.Context;

import com.orm.SugarContext;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by steve on 3/28/16.
 */
public class WanquApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        mContext = getApplicationContext();
        SugarContext.init(this);
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
