package com.wuruoye.demo;

import android.os.Bundle;
import android.view.View;

import com.wuruoye.library.ui.WBaseFragment;

/**
 * @Created : wuruoye
 * @Date : 2018/5/17 14:09.
 * @Description :
 */

public class EmptyFragment extends WBaseFragment {
    @Override
    protected int getContentView() {
        return R.layout.fragment_empty;
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initView(View view) {

    }
}
