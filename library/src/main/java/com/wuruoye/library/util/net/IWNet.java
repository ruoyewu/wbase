package com.wuruoye.library.util.net;


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

    String get(String url, Map<String, String> values) throws WNetException;
    String post(String url, Map<String, String> values) throws WNetException;
    String request(String url, Map<String, String> values, METHOD method) throws WNetException;
    String downloadFile(String url, String file) throws WNetException;
    String uploadFile(String url, Map<String, String> values, Map<String, String> files,
                      List<String> types) throws WNetException;
}
