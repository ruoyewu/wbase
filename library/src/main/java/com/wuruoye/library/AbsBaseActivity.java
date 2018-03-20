package com.wuruoye.library;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by wuruoye on 2017/11/20.
 * this file is to be the base of all activity
 */

public abstract class AbsBaseActivity extends AppCompatActivity {
    protected abstract int getContentView();
    protected abstract void initData(Bundle bundle);
    protected abstract void initView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initData(getIntent().getExtras());
        initView();
    }
}
