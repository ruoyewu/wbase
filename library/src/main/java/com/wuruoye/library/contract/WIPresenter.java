package com.wuruoye.library.contract;

/**
 * Created by wuruoye on 2017/11/21.
 * this file is to be interface of presenter
 */

public interface WIPresenter {
    void attachView(WIView view);
    void detachView();
}
