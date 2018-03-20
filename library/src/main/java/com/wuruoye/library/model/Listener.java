package com.wuruoye.library.model;

/**
 * Created by wuruoye on 2017/11/20.
 * this file is to be a base listener of some time-consuming operator
 */

public interface Listener<T> {
    void onSuccessful(T result);
    void onFail(String message);
}
