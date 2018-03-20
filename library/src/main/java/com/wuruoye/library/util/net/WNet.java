package com.wuruoye.library.util.net;

import android.util.ArrayMap;

import com.wuruoye.library.model.Listener;


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
}
