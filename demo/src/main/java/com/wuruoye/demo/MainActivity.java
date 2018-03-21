package com.wuruoye.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wuruoye.library.ui.WBaseActivity;

public class MainActivity extends WBaseActivity implements View.OnClickListener {
    private Button btnSLL;
    private Button btnPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initView() {
        btnSLL = findViewById(R.id.btn_main_smart_linear_layout);
        btnPresenter = findViewById(R.id.btn_main_presenter);

        btnSLL.setOnClickListener(this);
        btnPresenter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_main_smart_linear_layout:
                intent = new Intent(this, SmartLinearLayoutActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_main_presenter:
                intent = new Intent(this, PresenterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
