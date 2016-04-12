package com.steve.wanqureader;

import android.app.Application;
import android.content.Context;

import com.orm.SugarContext;

/**
 * Created by steve on 3/28/16.
 */
public class WanquApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }

    public static Context getContext() {
        return mContext;
    }
}
