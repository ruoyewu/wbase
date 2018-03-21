package com.wuruoye.demo;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.wuruoye.demo.contract.PhotoContract;
import com.wuruoye.demo.presenter.PhotoPresenter;
import com.wuruoye.library.ui.WPhotoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuruoye on 2018/3/21.
 * this file is to
 */

public class PhotoActivity extends WPhotoActivity<PhotoContract.Presenter>
        implements PhotoContract.View, View.OnClickListener {
    public static final int OX = 500;
    public static final int OY = 500;
    public static final int AX = 1;
    public static final int AY = 1;

    private ImageView iv;
    private Button btnC;
    private Button btnT;
    private Button btnCC;
    private Button btnTC;
    private Button btnD;

    private boolean isNew = false;
    private List<String> mNewFileList = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_photo;
    }

    @Override
    protected void initData(Bundle bundle) {
        setPresenter(new PhotoPresenter());
    }

    @Override
    protected void initView() {
        iv = findViewById(R.id.iv_photo);
        btnC = findViewById(R.id.btn_photo_choose);
        btnCC = findViewById(R.id.btn_photo_choose_crop);
        btnT = findViewById(R.id.btn_photo_take);
        btnTC = findViewById(R.id.btn_photo_take_crop);
        btnD = findViewById(R.id.btn_photo_delete);

        btnC.setOnClickListener(this);
        btnCC.setOnClickListener(this);
        btnT.setOnClickListener(this);
        btnTC.setOnClickListener(this);
        btnD.setOnClickListener(this);
    }

    @Override
    public void onPhotoBack(String photoPath) {
        iv.setImageBitmap(BitmapFactory.decodeFile(photoPath));
        if (isNew) {
            mNewFileList.add(photoPath);
            isNew = false;
        }
    }

    @Override
    public void onPhotoError(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_photo_choose:
                choosePhoto();
                break;
            case R.id.btn_photo_choose_crop:
                choosePhoto(mPresenter.getPath(), AX, AY, OX, OY);
                isNew = true;
                break;
            case R.id.btn_photo_take:
                takePhoto(mPresenter.getPath());
                isNew = true;
                break;
            case R.id.btn_photo_take_crop:
                takePhoto(mPresenter.getPath(), AX, AY, OX, OY);
                isNew = true;
                break;
            case R.id.btn_photo_delete:
                mPresenter.deleteFile(mNewFileList);
                break;
        }
    }

    @Override
    public void onDeleteError(String path) {
        Toast.makeText(this, "delete error " + path, Toast.LENGTH_SHORT).show();
    }
}
