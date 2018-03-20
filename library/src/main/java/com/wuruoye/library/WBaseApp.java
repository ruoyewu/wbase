package com.wuruoye.library;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.wuruoye.library.model.Config;


/**
 * Created by wuruoye on 2017/11/20.
 * this file is to be the global application of the app
 */

public abstract class WBaseApp extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Config.init(this);
    }

    public static Context getApp() {
        return mContext;
    }
}
