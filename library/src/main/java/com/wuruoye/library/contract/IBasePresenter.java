package com.wuruoye.library.contract;

/**
 * Created by wuruoye on 2017/11/21.
 * this file is to be interface of presenter
 */

public interface IBasePresenter {
    void attachView(IBaseView view);
    void detachView();
}
