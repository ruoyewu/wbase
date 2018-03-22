package com.wuruoye.library.util.net;


import android.support.v4.util.ArrayMap;

import com.wuruoye.library.model.Listener;
import com.wuruoye.library.ui.WBaseApp;


/**1
 * Created by wuruoye on 2018/3/20.
 * this file is to
 */

public class WNet {
    private static IWNet mNet;

    public static void init(IWNet net) {
        mNet = net;
    }

    public static void setType(IWNet.PARAM_TYPE type) {
        mNet.setParamType(type);
    }

    public static void get(String url, ArrayMap<String, String> values, Listener<String> listener) {
        mNet.get(url, values, listener);
    }

    public static void post(String url, ArrayMap<String, String> values, Listener<String> listener) {
        mNet.post(url, values, listener);
    }

    public static void uploadFile(String url, String key, String file, String type,
                                  Listener<String> listener) {
        mNet.uploadFile(url, key, file, type, listener);
    }

    public static void getInBackground(final String url, final ArrayMap<String, String> values,
                                       final Listener<String> listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                get(url, values, new Listener<String>() {
                    @Override
                    public void onSuccessful(final String result) {
                        WBaseApp.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onSuccessful(result);
                            }
                        });
                    }

                    @Override
                    public void onFail(final String message) {
                        WBaseApp.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onFail(message);
                            }
                        });
                    }
                });
            }
        }).start();
    }

    public static void postInBackGround(final String url, final ArrayMap<String, String> values,
                                        final Listener<String> listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                post(url, values, new Listener<String>() {
                    @Override
                    public void onSuccessful(final String result) {
                        WBaseApp.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onSuccessful(result);
                            }
                        });
                    }

                    @Override
                    public void onFail(final String message) {
                        WBaseApp.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onFail(message);
                            }
                        });
                    }
                });
            }
        }).start();
    }

    public static void uploadFileInBackGround(final String url, final String key, final String file,
                                              final String type, final Listener<String> listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                uploadFile(url, key, file, type, new Listener<String>() {
                    @Override
                    public void onSuccessful(final String result) {
                        WBaseApp.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onSuccessful(result);
                            }
                        });
                    }

                    @Override
                    public void onFail(final String message) {
                        WBaseApp.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onFail(message);
                            }
                        });
                    }
                });
            }
        }).start();
    }
}
