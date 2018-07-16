package com.wuruoye.library.util.media;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import com.wuruoye.library.model.WConfig;
import com.wuruoye.library.util.FileUtil;
import com.wuruoye.library.util.permission.IWPermission;
import com.wuruoye.library.util.permission.PermissionConfig;
import com.wuruoye.library.util.permission.WPermission;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * @Created : wuruoye
 * @Date : 2018/3/22.
 * @Decription : {@link IWPhoto} 的一个实现，用于在 {@link Activity}
 * {@link android.support.v4.app.Fragment} 调用系统内部获取照片 Activity 并返回结果
 */

public class WPhoto implements IWPhoto<String> {
    private static OnWPhotoResultListener mListener;

    private static WeakReference<Activity> mActivity;
    private WPermission mPermission;
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

    public WPhoto(Activity activity) {
        mActivity = new WeakReference<>(activity);
        mPermission = new WPermission(activity);
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mListener != null) {
            mListener.onActivityResult(requestCode, resultCode, data);
        }
    }

    public static void clear() {
        if (mActivity != null) {
            mActivity.clear();
        }
        mActivity = null;
        mListener = null;
    }

    @Override
    public void choosePhoto(final OnWPhotoListener<String> listener) {
        mIsCrop = false;
        if (WPermission.isNeedPermissionRequest()) {
            mPermission.requestPermission(PermissionConfig.PERMISSION_FILE,
                    WConfig.CODE_PERMISSION_FILE, new IWPermission.OnWPermissionListener() {
                @Override
                public void onPermissionResult(int requestCode, @NonNull String[] permissions,
                                               @NonNull int[] grantResult) {
                    if (WPermission.isGranted(permissions, grantResult)) {
                        doChoosePhoto(listener);
                    }else {
                        listener.onPhotoError("无文件读写权限");
                    }
                }
            });
        }else {
            doChoosePhoto(listener);
        }
    }

    // 相册选取具体代码
    private void doChoosePhoto(final OnWPhotoListener<String> listener){
        final Activity activity = mActivity.get();
        if (activity != null) {
            mListener = new OnWPhotoResultListener() {
                @Override
                public void onActivityResult(int requestCode, int resultCode, Intent data) {
                    if (requestCode == WConfig.CODE_CHOOSE_PHOTO) {
                        if (resultCode == RESULT_OK) {
                            Uri uri = data.getData();
                            if (mIsCrop){
                                String filePath;
                                try {
                                    filePath = getPathFromUri(activity, uri);
                                } catch (Exception e) {
                                    listener.onPhotoError(e.getMessage());
                                    return;
                                }
                                assert filePath != null;
                                uri = FileProvider.getUriForFile(activity, WConfig.PROVIDER_AUTHORITY,
                                        new File(filePath));
                                cropPhoto(uri, listener);
                            }else {
                                String filePath = null;
                                try {
                                    filePath = getPathFromUri(activity, uri);
                                } catch (Exception e) {
                                    listener.onPhotoError(e.getMessage());
                                    return;
                                }
                                listener.onPhotoResult(filePath);
                            }
                        }else {
                            listener.onPhotoError("获取照片失败");
                        }
                    }
                }
            };
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            activity.startActivityForResult(intent, WConfig.CODE_CHOOSE_PHOTO);
        }
    }

    @Override
    public void choosePhoto(String filePath, int aX, int aY, int oX, int oY,
                            final OnWPhotoListener<String> listener) {
        mIsCrop = true;
        mFilePath = filePath;
        mAspectX = aX;
        mAspectY = aY;
        mOutputX = oX;
        mOutputY = oY;
        if (WPermission.isNeedPermissionRequest()) {
            mPermission.requestPermission(PermissionConfig.PERMISSION_FILE,
                    WConfig.CODE_PERMISSION_FILE, new IWPermission.OnWPermissionListener() {
                        @Override
                        public void onPermissionResult(int requestCode,
                                                       @NonNull String[] permissions,
                                                       @NonNull int[] grantResult) {
                            if (WPermission.isGranted(permissions, grantResult)) {
                                doChoosePhoto(listener);
                            }else {
                                listener.onPhotoError("无文件读写权限");
                            }
                        }
                    });
        }else {
            doChoosePhoto(listener);
        }
    }

    @Override
    public void takePhoto(String filePath, final OnWPhotoListener<String> listener) {
        mFilePath = filePath;
        mIsCrop = false;
        if (WPermission.isNeedPermissionRequest()) {
            mPermission.requestPermission(PermissionConfig.PERMISSION_FILE,
                    WConfig.CODE_PERMISSION_FILE, new IWPermission.OnWPermissionListener() {
                        @Override
                        public void onPermissionResult(int requestCode,
                                                       @NonNull String[] permissions,
                                                       @NonNull int[] grantResult) {
                            if (WPermission.isGranted(permissions, grantResult)) {
                                mPermission.requestPermission(PermissionConfig.PERMISSION_CAMARE,
                                        WConfig.CODE_PERMISSION_CAMERA, new IWPermission.OnWPermissionListener() {
                                            @Override
                                            public void onPermissionResult(int requestCode, @NonNull String[] permissions,
                                                                           @NonNull int[] grantResult) {
                                                if (WPermission.isGranted(permissions, grantResult)) {
                                                    doTakePhoto(listener);
                                                }else {
                                                    listener.onPhotoError("无相机读写权限");
                                                }
                                            }
                                        });
                            }else {
                                listener.onPhotoError("无文件读写权限");
                            }
                        }
                    });
        }else {
            doTakePhoto(listener);
        }
    }

    @Override
    public void takePhoto(String filePath, int aX, int aY, int oX, int oY,
                          final OnWPhotoListener<String> listener) {
        mFilePath = filePath;
        mIsCrop = true;
        mAspectX = aX;
        mAspectY = aY;
        mOutputX = oX;
        mOutputY = oY;
        if (WPermission.isNeedPermissionRequest()) {
            mPermission.requestPermission(PermissionConfig.PERMISSION_FILE,
                    WConfig.CODE_PERMISSION_FILE, new IWPermission.OnWPermissionListener() {
                        @Override
                        public void onPermissionResult(int requestCode,
                                                       @NonNull String[] permissions,
                                                       @NonNull int[] grantResult) {
                            if (WPermission.isGranted(permissions, grantResult)) {
                                mPermission.requestPermission(PermissionConfig.PERMISSION_CAMARE,
                                        WConfig.CODE_PERMISSION_CAMERA, new IWPermission.OnWPermissionListener() {
                                            @Override
                                            public void onPermissionResult(int requestCode, @NonNull String[] permissions,
                                                                           @NonNull int[] grantResult) {
                                                if (WPermission.isGranted(permissions, grantResult)) {
                                                    doTakePhoto(listener);
                                                }else {
                                                    listener.onPhotoError("无相机读写权限");
                                                }
                                            }
                                        });
                            }else {
                                listener.onPhotoError("无文件读写权限");
                            }
                        }
                    });
        }else {
            doTakePhoto(listener);
        }
    }

    private void doTakePhoto(final OnWPhotoListener<String> listener) {
        final Activity activity = mActivity.get();
        if (activity != null) {
            mListener = new OnWPhotoResultListener() {
                @Override
                public void onActivityResult(int requestCode, int resultCode, Intent data) {
                    if (requestCode == WConfig.CODE_TAKE_PHOTO) {
                        if (resultCode == RESULT_OK) {
                            if (mIsCrop) {
                                Uri uri = FileProvider.getUriForFile(activity, WConfig.PROVIDER_AUTHORITY,
                                        new File(mFilePath));
                                cropPhoto(uri, listener);
                            }else {
                                listener.onPhotoResult(mFilePath);
                            }
                        }else {
                            listener.onPhotoError("获取照片失败");
                        }
                    }
                }
            };
            FileUtil.checkFile(mFilePath);
            File file = new File(mFilePath);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri uri;
            if (Build.VERSION.SDK_INT >= 21) {
                uri = FileProvider.getUriForFile(activity, WConfig.PROVIDER_AUTHORITY, file);
            }else {
                uri = Uri.fromFile(file);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            activity.startActivityForResult(intent, WConfig.CODE_TAKE_PHOTO);
        }
    }

    @Override
    public void cropPhoto(String from, String to, int aX, int aY, int oX, int oY,
                          final OnWPhotoListener<String> listener) {
        final Activity activity = mActivity.get();
        if (activity != null) {
            mIsCrop = true;
            mFilePath = to;
            mAspectX = aX;
            mAspectY = aY;
            mOutputX = oX;
            mOutputY = oY;
            mCropUri = FileProvider.getUriForFile(activity, WConfig.PROVIDER_AUTHORITY,
                    new File(from));
            if (WPermission.isNeedPermissionRequest()) {
                mPermission.requestPermission(PermissionConfig.PERMISSION_FILE,
                        WConfig.CODE_PERMISSION_FILE, new IWPermission.OnWPermissionListener() {
                    @Override
                    public void onPermissionResult(int requestCode, @NonNull String[] permissions,
                                                   @NonNull int[] grantResult) {
                        if (WPermission.isGranted(permissions, grantResult)) {
                            mPermission.requestPermission(PermissionConfig.PERMISSION_CAMARE,
                                    WConfig.CODE_PERMISSION_CAMERA, new IWPermission.OnWPermissionListener() {
                                        @Override
                                        public void onPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResult) {
                                            if (WPermission.isGranted(permissions, grantResult)) {
                                                cropPhoto(mCropUri, listener);
                                            }else {
                                                listener.onPhotoError("无相机权限");
                                            }
                                        }
                                    });
                        }else {
                            listener.onPhotoError("无文件读写权限");
                        }
                    }
                });
            }else {
                cropPhoto(mCropUri, listener);
            }
        }
    }

    private void cropPhoto(Uri uri, final OnWPhotoListener<String> listener) {
        final Activity activity = mActivity.get();
        if (activity != null) {
            mListener = new OnWPhotoResultListener() {
                @Override
                public void onActivityResult(int requestCode, int resultCode, Intent data) {
                    if (requestCode == WConfig.CODE_CROP_PHOTO) {
                        if (resultCode == RESULT_OK) {
                            listener.onPhotoResult(mFilePath);
                        }else {
                            listener.onPhotoError("剪裁图片失败");
                        }
                    }
                }
            };
            FileUtil.checkFile(mFilePath);
            File file = new File(mFilePath);
            Uri outUri = FileProvider.getUriForFile(activity, WConfig.PROVIDER_AUTHORITY, file);
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", true);
            intent.putExtra("aspectX", mAspectX);
            intent.putExtra("aspectY", mAspectY);
            intent.putExtra("outputX", mOutputX);
            intent.putExtra("outputY", mOutputY);
            intent.putExtra("return-date", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            List<ResolveInfo> resolveInfoList = activity.getPackageManager().queryIntentActivities(intent,
                    PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo info : resolveInfoList) {
                String packageName = info.activityInfo.packageName;
                activity.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                activity.grantUriPermission(packageName, outUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                                | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            if (resolveInfoList.size() > 0) {
                activity.startActivityForResult(intent, WConfig.CODE_CROP_PHOTO);
            }else {
                listener.onPhotoError("没有用来剪裁图片的 Activity");
            }
        }
    }

    private String getPathFromUri(Context context, Uri uri) throws Exception {
        String path = FileUtil.getFilePathByUri(context, uri);
        if (path == null) {
            path = FileUtil.getFilePathByUri2(context, uri);
        }
        if (path == null) {
            throw new Exception("获取照片失败");
        }
        return path;
    }
}
