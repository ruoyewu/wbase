package com.wuruoye.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wuruoye.demo.contract.PresenterContract;
import com.wuruoye.demo.presenter.PresenterPresenter;
import com.wuruoye.library.ui.WBaseActivity;

/**
 * Created by wuruoye on 2018/3/21.
 * this file is to
 */

public class PresenterActivity extends WBaseActivity<PresenterContract.Presenter>
        implements View.OnClickListener, PresenterContract.View{
    private EditText et;
    private Button btn;
    private TextView tv;

    @Override
    protected int getContentView() {
        return R.layout.activity_presenter_demo;
    }

    @Override
    protected void initData(Bundle bundle) {
        setPresenter(new PresenterPresenter());
    }

    @Override
    protected void initView() {
        et = findViewById(R.id.et_presenter);
        btn = findViewById(R.id.btn_presenter);
        tv = findViewById(R.id.tv_presenter);

        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mPresenter.request(et.getText().toString());
    }

    @Override
    public void onResult(String result) {
        tv.setText(result);
    }
}
