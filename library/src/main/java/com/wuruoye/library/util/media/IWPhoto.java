package com.wuruoye.library.util.media;

import android.content.Intent;

/**
 * @Created : wuruoye
 * @Date : 2018/3/22.
 * @Decription : 与 Activity 分离的照片获取接口
 */

public interface IWPhoto<T> {
    interface OnWPhotoListener<T> {
        void onPhotoResult(T result);
        void onPhotoError(String error);
    }

    interface OnWPhotoResult {
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }

    void choosePhoto(OnWPhotoListener<T> listener);
    void choosePhoto(String filePath, int aX, int aY, int oX, int oY,
                     OnWPhotoListener<T> listener);
    void takePhoto(String filePath, OnWPhotoListener<T> listener);
    void takePhoto(String filePath, int aX, int aY, int oX, int oY,
                   OnWPhotoListener<T> listener);
    void cropPhoto(String from, String to, int aX, int aY, int oX, int o,
                   OnWPhotoListener<T> listener);
}
