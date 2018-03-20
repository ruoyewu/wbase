package com.wuruoye.library;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;


import com.wuruoye.library.model.Config;
import com.wuruoye.library.util.FileUtil;

import java.io.File;
import java.util.List;

/**
 * Created by wuruoye on 2017/11/20.
 * this file is to be the base activity of all activities who need use photo
 */

public abstract class AbsPhotoActivity extends AbsBaseActivity {
    public static final int CHOOSE_PHOTO = 1;
    public static final int TAKE_PHOTO = 2;
    public static final int CROP_PHOTO = 3;

    protected abstract void onPhotoBack(String photoPath);
    protected abstract void onPhotoPermissionDeny(String info);

    // 是否剪裁
    private boolean mIsCrop = false;
    // 文件名
    private String mFilePath;
    // 剪裁比例
    private int mAspectX;
    private int mAspectY;
    // 剪裁输出
    private int mOutputX;
    private int mOutputY;
    //
    private Uri mCropUri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            Uri uri;
            switch (requestCode){
                case CHOOSE_PHOTO:
                    uri = data.getData();
                    if (mIsCrop){
                        String filePath = FileUtil.getFilePathByUri(this, uri);
                        uri = FileProvider.getUriForFile(this, Config.PROVIDER_AUTHORITY,
                                new File(filePath));
                        cropPhoto(uri);
                    }else {
                        String filePath = FileUtil.getFilePathByUri(this, uri);
                        onPhotoBack(filePath);
                    }
                    break;
                case TAKE_PHOTO:
                    if (mIsCrop) {
                        uri = FileProvider.getUriForFile(this, Config.PROVIDER_AUTHORITY,
                                new File(mFilePath));
                        cropPhoto(uri);
                    }else {
                        onPhotoBack(mFilePath);
                    }
                    break;
                case CROP_PHOTO:
                    onPhotoBack(mFilePath);
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            switch (requestCode){
                case CHOOSE_PHOTO:
                    doChoosePhoto();
                    break;
                case TAKE_PHOTO:
                    doTakePhoto();
                    break;
                case CROP_PHOTO:
                    cropPhoto(mCropUri);
                    break;
            }
        } else {
            onPhotoPermissionDeny("无权限");
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 从相册选取图片，需要剪裁
     * @param filePath 输出文件名
     * @param aX 剪裁比例 x
     * @param aY 剪裁比例 y
     * @param oX 输出大小 x
     * @param oY 输出大小 y
     */
    protected void choosePhoto(String filePath, int aX, int aY, int oX, int oY){
        mIsCrop = true;
        mFilePath = filePath;
        mAspectX = aX;
        mAspectY = aY;
        mOutputX = oX;
        mOutputY = oY;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(Config.FILE_PERMISSION, CHOOSE_PHOTO);
        }else {
            doChoosePhoto();
        }
    }

    /**
     * 相册选取图片，无需剪裁
     */
    protected void choosePhoto(){
        mIsCrop = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(Config.FILE_PERMISSION, CHOOSE_PHOTO);
        }else {
            doChoosePhoto();
        }
    }

    // 相册选取具体代码
    private void doChoosePhoto(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    /**
     * 打开相机拍照，需要剪裁
     * @param filePath 输出文件名
     * @param aX 剪裁比例 x
     * @param aY 剪裁比例 y
     * @param oX 输出大小 x
     * @param oY 输出大小 y
     */
    protected void takePhoto(String filePath, int aX, int aY, int oX, int oY){
        mIsCrop = true;
        mFilePath = filePath;
        mAspectX = aX;
        mAspectY = aY;
        mOutputX = oX;
        mOutputY = oY;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(Config.CAMERA_PERMISSION, TAKE_PHOTO);
        }else {
            doTakePhoto();
        }
    }

    /**
     * 打开相机拍照，无需剪裁
     * @param filePath 输出文件名
     */
    protected void takePhoto(String filePath){
        mIsCrop = false;
        mFilePath = filePath;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(Config.CAMERA_PERMISSION, TAKE_PHOTO);
        }else {
            doTakePhoto();
        }
    }

    private void doTakePhoto(){
        FileUtil.checkFile(mFilePath);
        File file = new File(mFilePath);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri;
        if (Build.VERSION.SDK_INT >= 21) {
            uri = FileProvider.getUriForFile(this, Config.PROVIDER_AUTHORITY, file);
        }else {
            uri = Uri.fromFile(file);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    /**
     * 剪裁照片
     * @param fileFrom 源文件
     * @param fileTo 目标文件
     * @param aX 剪裁比例 x
     * @param aY 剪裁比例 y
     * @param oX 输出大小 x
     * @param oY 输出大小 y
     */
    protected void cropPhoto(String fileFrom, String fileTo, int aX, int aY, int oX, int oY){
        mIsCrop = true;
        mFilePath = fileTo;
        mAspectX = aX;
        mAspectY = aY;
        mOutputX = oX;
        mOutputY = oY;
        mCropUri = FileProvider.getUriForFile(this, Config.PROVIDER_AUTHORITY,
                new File(fileFrom));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(Config.CAMERA_PERMISSION, CROP_PHOTO);
        }else {
            cropPhoto(mCropUri);
        }
    }
    
    private void cropPhoto(Uri uri){
        FileUtil.checkFile(mFilePath);
        File file = new File(mFilePath);
        Uri outUri = FileProvider.getUriForFile(this, Config.PROVIDER_AUTHORITY, file);
        Intent intent = new Intent("com.android.camera.action.PHOTO_CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", mAspectX);
        intent.putExtra("aspectY", mAspectY);
        intent.putExtra("outputX", mOutputX);
        intent.putExtra("outputY", mOutputY);
        intent.putExtra("return-date", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);

        List<ResolveInfo> resolveInfoList = getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo info : resolveInfoList) {
            String packageName = info.activityInfo.packageName;
            grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            grantUriPermission(packageName, outUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                            | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        startActivityForResult(intent, CROP_PHOTO);
    }
}
