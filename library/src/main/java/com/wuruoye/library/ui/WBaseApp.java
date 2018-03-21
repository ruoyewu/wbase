package com.wuruoye.library.ui;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.wuruoye.library.model.WConfig;


/**
 * Created by wuruoye on 2017/11/20.
 * this file is to be the global application of the app
 */

public abstract class WBaseApp extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mHandler = new Handler(Looper.getMainLooper());
        WConfig.init(this);
    }

    public static Context getApp() {
        return mContext;
    }

    public static void runOnMainThread(Runnable runnable) {
        mHandler.post(runnable);
    }
}
