package com.wuruoye.library.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuruoye.library.contract.WIPresenter;
import com.wuruoye.library.contract.WIView;

/**
 * Created by wuruoye on 2017/11/20.
 * this file is to be the base of all fragment
 */

public abstract class WBaseFragment<T extends WIPresenter>
        extends Fragment {
    protected abstract int getContentView();
    protected abstract void initData(Bundle bundle);
    protected abstract void initView(View view);

    protected T mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentView(), container, false);
        initData(getArguments());
        initView(view);
        return view;
    }

    public void setPresenter(T presenter) {
        if (this instanceof WIView) {
            mPresenter = presenter;
            mPresenter.attachView((WIView) this);
        }else {
            throw new IllegalStateException("fragment must implement WIView");
        }
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }
}
