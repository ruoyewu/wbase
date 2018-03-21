package com.wuruoye.library.ui;

/**
 * Created by wuruoye on 2018/3/21.
 * this file is to
 */

public interface IPhotoView {
    public static final int CHOOSE_PHOTO = 1;
    public static final int TAKE_PHOTO = 2;
    public static final int CROP_PHOTO = 3;

    void choosePhoto();
    void choosePhoto(String filePath, int aX, int aY, int oX, int oY);
    void takePhoto(String filePath);
    void takePhoto(String filePath, int aX, int aY, int oX, int oY);
    void cropPhoto(String from, String to, int aX, int aY, int oX, int oY);
    void onPhotoBack(String photoPath);
    void onPhotoError(String info);
}
