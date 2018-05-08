package com.wuruoye.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wuruoye.library.ui.WBaseActivity;

public class MainActivity extends WBaseActivity implements View.OnClickListener {
    private Button btnSLL;
    private Button btnPresenter;
    private Button btnPhoto;
    private Button btnNet;
    private Button btnHeart;
    private Button btnSort;

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
        btnPhoto = findViewById(R.id.btn_main_photo);
        btnNet = findViewById(R.id.btn_main_net);
        btnHeart = findViewById(R.id.btn_main_heart);
        btnSort = findViewById(R.id.btn_main_sort);

        btnSLL.setOnClickListener(this);
        btnPresenter.setOnClickListener(this);
        btnPhoto.setOnClickListener(this);
        btnNet.setOnClickListener(this);
        btnHeart.setOnClickListener(this);
        btnSort.setOnClickListener(this);
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
            case R.id.btn_main_photo:
                intent = new Intent(this, PhotoActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_main_net:
                intent = new Intent(this, NetActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_main_heart:
                intent = new Intent(this, HeartBeatActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_main_sort:
                intent = new Intent(this, SortActivity.class);
                startActivity(intent);
                break;
        }
    }
}
