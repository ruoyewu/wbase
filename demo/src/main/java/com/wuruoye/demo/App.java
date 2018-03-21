package com.wuruoye.demo;

import com.wuruoye.library.ui.WBaseApp;
import com.wuruoye.library.util.net.OKHttpNet;
import com.wuruoye.library.util.net.WNet;

/**
 * Created by wuruoye on 2018/3/21.
 * this file is to
 */

public class App extends WBaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        WNet.init(new OKHttpNet());
    }
}
