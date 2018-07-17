package com.wuruoye.library.util.net;


import com.wuruoye.library.model.Listener;
import com.wuruoye.library.util.thread.WThreadPool;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;


/**
 * Created by wuruoye on 2018/3/20.
 * 网络请求入口类
 */

public class WNet {
    private static IWNet mNet;

    public static void init(IWNet net) {
        mNet = net;
    }

    public static <T extends IWNet> T getNet() {
        return (T)mNet;
    }

    public static void setType(IWNet.PARAM_TYPE type) {
        mNet.setParamType(type);
    }

    public static String get(String url, Map<String, String> values) throws WNetException{
        return mNet.get(url, values);
    }

    public static String post(String url, Map<String, String> values) throws WNetException {
        return mNet.post(url, values);
    }

    public static String request(String url, Map<String, String> values,
                               IWNet.METHOD method) throws WNetException{
        return mNet.request(url, values, method);
    }

    public static String uploadFile(String url, String key, String file, String type)
            throws WNetException{
        return uploadFile(url, Collections.<String, String>emptyMap(), Collections.singletonMap(key, file),
                Collections.singletonList(type));
    }

    public static String uploadFile(String url, Map<String, String> values, Map<String,
            String> files, List<String> types) throws WNetException{
        return mNet.uploadFile(url, values, files, types);
    }

    public static String downloadFile(String url, String filePath) throws WNetException {
        return mNet.downloadFile(url, filePath);
    }

    public static void getInBackground(final String url, final Map<String, String> values,
                                       final Listener<String> listener) {
        WThreadPool.execInBackground(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return get(url, values);
            }
        }, new Listener<String>() {
            @Override
            public void onSuccessful(String result) {
                listener.onSuccessful(result);
            }

            @Override
            public void onFail(String message) {
                listener.onFail(message);
            }
        });
    }

    public static void postInBackground(final String url, final Map<String, String> values,
                                        final Listener<String> listener) {
        WThreadPool.execInBackground(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return post(url, values);
            }
        }, new Listener<String>() {
            @Override
            public void onSuccessful(String result) {
                listener.onSuccessful(result);
            }

            @Override
            public void onFail(String message) {
                listener.onFail(message);
            }
        });
    }

    public static void requestInBackground(final String url, final Map<String, String> values,
                                           final Listener<String>
                                           listener, final IWNet.METHOD method) {
        WThreadPool.execInBackground(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return request(url, values, method);
            }
        }, new Listener<String>() {
            @Override
            public void onSuccessful(String result) {
                listener.onSuccessful(result);
            }

            @Override
            public void onFail(String message) {
                listener.onFail(message);
            }
        });
    }

    public static void uploadFileInBackground(final String url, final String key, final String file,
                                              final String type, final Listener<String> listener) {
        WThreadPool.execInBackground(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return uploadFile(url, key, file, type);
            }
        }, new Listener<String>() {
            @Override
            public void onSuccessful(String result) {
                listener.onSuccessful(result);
            }

            @Override
            public void onFail(String message) {
                listener.onFail(message);
            }
        });
    }

    public static void uploadFileInBackground(final String url, final Map<String, String> values,
                                              final Map<String, String> files, final List<String> types,
                                              final Listener<String> listener) {
        WThreadPool.execInBackground(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return uploadFile(url, values, files, types);
            }
        }, new Listener<String>() {
            @Override
            public void onSuccessful(String result) {
                listener.onSuccessful(result);
            }

            @Override
            public void onFail(String message) {
                listener.onFail(message);
            }
        });
    }

    public static void downloadFileInBackground(final String url, final String filePath,
                                                final Listener<String> listener) {
        WThreadPool.execInBackground(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return downloadFile(url, filePath);
            }
        }, new Listener<String>() {
            @Override
            public void onSuccessful(String result) {
                listener.onSuccessful(result);
            }

            @Override
            public void onFail(String message) {
                listener.onFail(message);
            }
        });
    }
}
