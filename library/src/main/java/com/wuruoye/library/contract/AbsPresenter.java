package com.wuruoye.library.contract;

import java.lang.ref.WeakReference;

/**
 * Created by wuruoye on 2017/11/21.
 * this file is to be the base presenter class
 */

public class AbsPresenter<T extends IBaseView> implements IBasePresenter{
    private WeakReference<T> mViewRef;

    enum Method{
        NET,
        LOCAL
    }

    @Override
    public void attachView(IBaseView view) {
        mViewRef = new WeakReference<>((T) view);
    }

    @Override
    public void detachView() {
        if (mViewRef != null){
            mViewRef.clear();
            mViewRef = null;
        }
    }

    protected T getView(){
        if (mViewRef != null){
            return mViewRef.get();
        }else {
            return null;
        }
    }

    public boolean isAvailable() {
        return getView() != null;
    }
}
