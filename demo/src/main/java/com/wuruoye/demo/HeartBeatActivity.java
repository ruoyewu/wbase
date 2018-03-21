package com.wuruoye.demo;

import android.os.Bundle;

import com.wuruoye.library.ui.WBaseActivity;
import com.wuruoye.library.widget.HeartBeatView;

/**
 * Created by wuruoye on 2018/3/21.
 * this file is to
 */

public class HeartBeatActivity extends WBaseActivity {
    private HeartBeatView hbv;

    @Override
    protected int getContentView() {
        return R.layout.activity_heart_beat;
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initView() {
        hbv = findViewById(R.id.hbv_heart);
    }
}
