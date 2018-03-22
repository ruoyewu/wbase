package com.wuruoye.library.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wuruoye.library.contract.WIPresenter;
import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.util.media.WPhoto;
import com.wuruoye.library.util.permission.WPermission;

/**
 * Created by wuruoye on 2017/11/20.
 * this file is to be the base of all activity
 */

public abstract class WBaseActivity<T extends WIPresenter>
        extends AppCompatActivity{
    protected abstract int getContentView();
    protected abstract void initData(Bundle bundle);
    protected abstract void initView();

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initData(getIntent().getExtras());
        initView();
    }

    public void setPresenter(T presenter) {
        if (this instanceof WIView) {
            mPresenter = presenter;
            mPresenter.attachView((WIView) this);
        }else {
            throw new IllegalStateException("activity must implement WIView");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        WPhoto.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        WPermission.onPermissionResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        WPhoto.clear();
        WPermission.clear();
        super.onDestroy();
    }
}
