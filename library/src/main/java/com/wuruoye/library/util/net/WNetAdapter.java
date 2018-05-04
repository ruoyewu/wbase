package com.wuruoye.library.util.net;

import com.wuruoye.library.model.Listener;

import java.util.List;
import java.util.Map;

/**
 * @Created : wuruoye
 * @Date : 2018/4/1.
 * @Description :
 */

public abstract class WNetAdapter implements IWNet {

    @Override
    public void setParamType(PARAM_TYPE type) {

    }

    @Override
    public void get(String url, Map<String, String> values, Listener<String> listener) {

    }

    @Override
    public void post(String url, Map<String, String> values, Listener<String> listener) {

    }

    @Override
    public void request(String url, Map<String, String> values, Listener<String> listener, METHOD method) {

    }

    @Override
    public void downloadFile(String url, String file, Listener<String> listener) {

    }

    @Override
    public void uploadFile(String url, Map<String, String> values, Map<String, String> files, List<String> types, Listener<String> listener) {

    }
}
