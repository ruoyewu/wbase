package com.wuruoye.demo;

import android.os.Bundle;
import android.view.View;

import com.wuruoye.library.ui.WBaseActivity;
import com.wuruoye.library.widget.ChangeImageView;

/**
 * @Created : wuruoye
 * @Date : 2018/5/24 20:21.
 * @Description :
 */
public class ChangeImageViewActivity extends WBaseActivity implements View.OnClickListener {
    private ChangeImageView civ;

    private boolean mIsSelect;

    @Override
    protected int getContentView() {
        return R.layout.activity_change_iv;
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initView() {
        civ = findViewById(R.id.civ_change_iv);
        civ.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (mIsSelect) {
            civ.back();
        }else {
            civ.select();
        }
        mIsSelect = !mIsSelect;
    }
}
