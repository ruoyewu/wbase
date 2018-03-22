package com.wuruoye.library.ui;

import android.content.Intent;

/**
 * Created by wuruoye on 2018/3/21.
 * this file is to
 */

public interface IWPhotoView {
    int CHOOSE_PHOTO = 1;
    int TAKE_PHOTO = 2;
    int CROP_PHOTO = 3;

    void choosePhoto();
    void choosePhoto(String filePath, int aX, int aY, int oX, int oY);
    void takePhoto(String filePath);
    void takePhoto(String filePath, int aX, int aY, int oX, int oY);
    void cropPhoto(String from, String to, int aX, int aY, int oX, int oY);
    void onPhotoBack(String photoPath);
    void onPhotoError(String info);

    interface IPhotoListener {
        void onResult(int requestCode, int resultCode, Intent data);
    }
}
