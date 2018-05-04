package com.wuruoye.library.util.net;


import com.wuruoye.library.model.Listener;

import java.util.List;
import java.util.Map;

/**
 * Created by wuruoye on 2018/3/20.
 * this file is to
 */

public interface IWNet {
    enum PARAM_TYPE {
        FORM,
        JSON,
        TEXT
    }
    enum METHOD {
        GET,
        POST,
        PUT,
        DELETE,
        HEAD,
        OPTIONS
    }
    void setParamType(PARAM_TYPE type);
    void get(String url, Map<String, String> values, Listener<String> listener);
    void post(String url, Map<String, String> values, Listener<String> listener);
    void request(String url, Map<String, String> values, Listener<String> listener, METHOD method);
    void downloadFile(String url, String file, Listener<String> listener);
    void uploadFile(String url, Map<String, String> values, Map<String, String> files,
                    List<String> types, Listener<String> listener);
}
