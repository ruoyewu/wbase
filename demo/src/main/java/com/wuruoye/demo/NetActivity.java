package com.wuruoye.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wuruoye.demo.contract.NetContract;
import com.wuruoye.demo.presenter.NetPresenter;
import com.wuruoye.library.ui.WBaseActivity;

/**
 * Created by wuruoye on 2018/3/21.
 * this file is to
 */

public class NetActivity extends WBaseActivity<NetContract.Presenter>
        implements View.OnClickListener, NetContract.View {
    private EditText et;
    private Button btn;
    private TextView tv;

    @Override
    protected int getContentView() {
        return R.layout.activity_net;
    }

    @Override
    protected void initData(Bundle bundle) {
        setPresenter(new NetPresenter());
    }

    @Override
    protected void initView() {
        et = findViewById(R.id.et_net);
        btn = findViewById(R.id.btn_net);
        tv = findViewById(R.id.tv_net);

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
