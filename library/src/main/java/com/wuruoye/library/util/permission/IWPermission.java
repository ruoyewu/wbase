package com.wuruoye.library.util.permission;

import android.support.annotation.NonNull;

/**
 * @Created : wuruoye
 * @Date : 2018/3/22.
 * @Decription :
 */

public interface IWPermission {
    interface OnWPermissionResult {
        void onPermissionResult(int requestCode, @NonNull String[] permissions,
                                @NonNull int[] grantResult);
    }

    void requestPermission(String[] permissions, int requestCode, OnWPermissionResult listener);

    boolean isGranted(String[] permissions);
}
