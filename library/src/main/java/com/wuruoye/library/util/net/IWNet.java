package com.wuruoye.library.util.net;

import android.util.ArrayMap;

import com.wuruoye.library.model.Listener;

/**
 * Created by wuruoye on 2018/3/20.
 * this file is to
 */

public interface IWNet {
    enum PARAM_TYPE {
        FORM,
        JSON
    }
    void setParamType(PARAM_TYPE type);
    void get(String url, ArrayMap<String, String> values, Listener<String> listener);
    void post(String url, ArrayMap<String, String> values, Listener<String> listener);
    void uploadFile(String url, String key, String file, String type, Listener<String> listener);
    void downloadFile(String url, String file, Listener<String> listener);
}
