package com.wuruoye.demo.presenter;


import android.support.v4.util.ArrayMap;

import com.wuruoye.demo.App;
import com.wuruoye.demo.contract.NetContract;
import com.wuruoye.library.model.Listener;
import com.wuruoye.library.util.net.WNet;

/**
 * Created by wuruoye on 2018/3/21.
 * this file is to
 */

public class NetPresenter extends NetContract.Presenter {
    @Override
    public void request(String url) {
        if (!url.startsWith("http://")) {
            url = "http://" + url;
        }
        final String finalUrl = url;
        new Thread(new Runnable() {
            @Override
            public void run() {
                WNet.get(finalUrl, new ArrayMap<String, String>(), new Listener<String>() {
                    @Override
                    public void onSuccessful(final String result) {
                        App.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isAvailable()) {
                                    getView().onResult(result);
                                }
                            }
                        });
                    }

                    @Override
                    public void onFail(final String message) {
                        App.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isAvailable()) {
                                    getView().onResult(message);
                                }
                            }
                        });
                    }
                });
            }
        }).start();
    }
}
