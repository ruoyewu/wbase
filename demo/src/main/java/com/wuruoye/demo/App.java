package com.wuruoye.demo;

import android.app.Application;

import com.wuruoye.library.model.WConfig;
import com.wuruoye.library.util.net.OKHttpNet;
import com.wuruoye.library.util.thread.DefaultThreadPool;

/**
 * Created by wuruoye on 2018/3/21.
 * this file is to
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        WConfig.init(this);
        WConfig.initNet(new OKHttpNet());
        WConfig.initThreadPool(new DefaultThreadPool());
    }
}
