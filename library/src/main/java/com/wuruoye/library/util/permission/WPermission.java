package com.wuruoye.library.util.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

/**
 * @Created : wuruoye
 * @Date : 2018/3/22.
 * @Decription : Android 动态权限请求
 */

public class WPermission implements IWPermission {
    private static OnWPermissionListener mListener;

    private static WeakReference<Activity> mActivity;

    public WPermission(Activity activity) {
        mActivity = new WeakReference<>(activity);
    }

    public static void onPermissionResult(int requestCode, @NonNull String[] permissions,
                                          @NonNull int[] grantResult) {
        if (mListener != null) {
            mListener.onPermissionResult(requestCode, permissions, grantResult);
        }
    }

    public static void check() {
        if (mActivity != null);
        if (mListener != null);
    }

    public static void clear() {
        if (mActivity != null) {
            mActivity.clear();
        }
        mActivity = null;
        mListener = null;
    }

    public static boolean isNeedPermissionRequest() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean isGranted(@NonNull String[] permissions, @NonNull int[] grantResult) {
        return grantResult.length > 0 &&
                grantResult[0] == PackageManager.PERMISSION_GRANTED;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void requestPermission(String[] permissions, int requestCode,
                                  OnWPermissionListener listener) {
        final Activity activity = mActivity.get();
        if (activity != null) {
            mListener = listener;
            activity.requestPermissions(permissions, requestCode);
        }
    }

    @Override
    public boolean isGranted(String[] permissions) {
        final Activity activity = mActivity.get();

        if (activity != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                for (String s : permissions) {
                    boolean result = activity.checkSelfPermission(s) ==
                            PackageManager.PERMISSION_GRANTED;
                    if (!result) {
                        return false;
                    }
                }
            }
            return true;
        }else {
            return false;
        }
    }
}
