package com.wuruoye.demo;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.wuruoye.demo.contract.PhotoContract;
import com.wuruoye.demo.presenter.PhotoPresenter;
import com.wuruoye.library.ui.WBaseActivity;
import com.wuruoye.library.util.media.IWPhoto;
import com.wuruoye.library.util.media.WPhoto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuruoye on 2018/3/21.
 * this file is to
 */

public class PhotoActivity extends WBaseActivity<PhotoContract.Presenter>
        implements PhotoContract.View, View.OnClickListener, IWPhoto.OnWPhotoListener<String> {
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
    private WPhoto mPhotoGet;

    @Override
    protected int getContentView() {
        return R.layout.activity_photo;
    }

    @Override
    protected void initData(Bundle bundle) {
        setPresenter(new PhotoPresenter());
        mPhotoGet = new WPhoto(this);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_photo_choose:
                mPhotoGet.choosePhoto(this);
                break;
            case R.id.btn_photo_choose_crop:
                mPhotoGet.choosePhoto(mPresenter.getPath(), AX, AY, OX, OY, this);
                isNew = true;
                break;
            case R.id.btn_photo_take:
                mPhotoGet.takePhoto(mPresenter.getPath(), this);
                isNew = true;
                break;
            case R.id.btn_photo_take_crop:
                mPhotoGet.takePhoto(mPresenter.getPath(), AX, AY, OX, OY, this);
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

    @Override
    public void onPhotoResult(String result) {
        System.gc();
        iv.setImageBitmap(BitmapFactory.decodeFile(result));
        if (isNew) {
            mNewFileList.add(result);
            isNew = false;
        }
    }

    @Override
    public void onPhotoError(String error) {
        Toast.makeText(PhotoActivity.this, error, Toast.LENGTH_SHORT).show();
    }
}
